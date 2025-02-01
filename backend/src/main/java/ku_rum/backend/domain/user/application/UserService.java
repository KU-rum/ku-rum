package ku_rum.backend.domain.user.application;

import jakarta.validation.Valid;
import ku_rum.backend.domain.department.domain.Department;
import ku_rum.backend.domain.department.domain.repository.DepartmentRepository;
import ku_rum.backend.domain.user.domain.User;
import ku_rum.backend.domain.user.domain.repository.UserRepository;
import ku_rum.backend.domain.mail.dto.request.LoginIdValidationRequest;
import ku_rum.backend.domain.user.dto.request.ResetAccountRequest;
import ku_rum.backend.domain.user.dto.request.UserSaveRequest;
import ku_rum.backend.domain.reservation.dto.request.WeinLoginRequest;
import ku_rum.backend.domain.user.dto.response.UserSaveResponse;
import ku_rum.backend.domain.user.dto.response.WeinLoginResponse;
import ku_rum.backend.global.exception.department.NoSuchDepartmentException;
import ku_rum.backend.global.exception.user.DuplicateEmailException;
import ku_rum.backend.global.exception.user.DuplicateStudentIdException;
import ku_rum.backend.global.exception.user.NoSuchUserException;
import ku_rum.backend.global.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.cookie.BasicCookieStore;
import org.apache.hc.client5.http.impl.DefaultRedirectStrategy;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import static ku_rum.backend.global.response.status.BaseExceptionResponseStatus.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserSaveResponse saveUser(final UserSaveRequest userSaveRequest) {
        validateUser(userSaveRequest);

        String password = passwordEncoder.encode(userSaveRequest.password());
        Department department = departmentRepository.findByName(userSaveRequest.department())
               .orElseThrow(() -> new NoSuchDepartmentException(NO_SUCH_DEPARTMENT));

        User user = UserSaveRequest.newUser(userSaveRequest, department, password);
        return UserSaveResponse.from(userRepository.save(user));
    }

    @Transactional
    public void resetAccount(final ResetAccountRequest resetAccountRequest) {
        User user = findUserByEmail(resetAccountRequest.email());
        changeLoginIdAndPassword(resetAccountRequest, user);
    }

    private void changeLoginIdAndPassword(ResetAccountRequest resetAccountRequest, User user) {
        user.setPassword(passwordEncoder.encode(resetAccountRequest.password()));
        user.setLoginId(resetAccountRequest.loginId());
    }

    private void validateUser(UserSaveRequest userSaveRequest) {
        validateDuplicateLoginid(userSaveRequest.loginId());
        validateDuplicateStudentId(userSaveRequest.studentId());
        validateDepartmentName(userSaveRequest.department());
    }

    public void validateEmail(final LoginIdValidationRequest loginIdValidationRequest) {
        validateDuplicateLoginid(loginIdValidationRequest.loginId());
    }

    private void validateDuplicateLoginid(final String loginId) {
        if (userRepository.existsByLoginId(loginId)) {
            throw new DuplicateEmailException(DUPLICATE_LOGINID);
        }
    }

    private void validateDuplicateStudentId(final String studentId) {
        if (userRepository.existsByStudentId(studentId)) {
            throw new DuplicateStudentIdException(DUPLICATE_STUDENT_ID);
        }
    }

    private void validateDepartmentName(final String department) {
        if (!departmentRepository.existsByName(department)) {
            throw new NoSuchDepartmentException(NO_SUCH_DEPARTMENT);
        }
    }

    private User findUserByEmail(final String email) {
        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new NoSuchUserException(NO_SUCH_USER));
    }

    public BaseResponse<WeinLoginResponse> loginToWein(@Valid final WeinLoginRequest weinLoginRequest) {
        CloseableHttpClient httpClient = getCloseableHttpClient();
        RestTemplate restTemplate = getRestTemplate(httpClient);
        HttpHeaders headers = getHeaders();

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(createRequestBody(weinLoginRequest), headers);
        log.info("Attempting login with userId: {} and password: {}", weinLoginRequest.getUserId(), weinLoginRequest.getPassword());

        try {
            ResponseEntity<String> response = getResponseEntity(restTemplate, requestEntity);
            BaseResponse<WeinLoginResponse> loginSuccess = getWeinLoginResponseBaseResponse(weinLoginRequest, response);
            if (loginSuccess != null) return loginSuccess;

            log.error("Unexpected response for userId: {}, status code: {}, response body: {}", weinLoginRequest.getUserId(), response.getStatusCode(), response.getBody());
            return BaseResponse.of(HttpStatus.INTERNAL_SERVER_ERROR, new WeinLoginResponse(false, "Unexpected login response"));
        } catch (Exception e) {
            log.error("Exception occurred during login: ", e);
            return BaseResponse.of(HttpStatus.INTERNAL_SERVER_ERROR, new WeinLoginResponse(false, "Wein login failed due to server error"));
        }
    }

    private static ResponseEntity<String> getResponseEntity(RestTemplate restTemplate, HttpEntity<MultiValueMap<String, String>> requestEntity) {
        ResponseEntity<String> response = restTemplate.exchange(
                "https://wein.konkuk.ac.kr/common/user/loginProc.do",
                HttpMethod.POST,
                requestEntity,
                String.class
        );
        return response;
    }

    private static BaseResponse<WeinLoginResponse> getWeinLoginResponseBaseResponse(WeinLoginRequest weinLoginRequest, ResponseEntity<String> response) {
        Optional<String> responseBodyOpt = Optional.ofNullable(response.getBody());
        if (responseBodyOpt.isPresent()) {
            String responseBody = responseBodyOpt.get();
            if (responseBody.contains("index.do")) {
                log.info("Login successful for userId: {}", weinLoginRequest.getUserId());
                return BaseResponse.ok(new WeinLoginResponse(true, "Wein login successful"));
            } else if (responseBody.contains("login.do")) {
                log.warn("Login failed for userId: {}", weinLoginRequest.getUserId());
                return BaseResponse.of(HttpStatus.UNAUTHORIZED, new WeinLoginResponse(false, "Invalid Wein credentials"));
            }
        }
        return null;
    }

    /*
     헤더 설정 메서드
     */
    private static HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        return headers;
    }

    /*
     HttpClient를 사용하는 RestTemplate 설정 메서드
     */
    private static RestTemplate getRestTemplate(CloseableHttpClient httpClient) {
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        return restTemplate;
    }

    /*
     리다이렉션 전략과 쿠키 저장소를 설정하여 HttpClient 생성 메서드
     */
    private static CloseableHttpClient getCloseableHttpClient() {
        CloseableHttpClient httpClient = HttpClients.custom()
                .setRedirectStrategy(new DefaultRedirectStrategy()) // 기본 리다이렉션 전략
                .setDefaultCookieStore(new BasicCookieStore())       // 세션 유지를 위한 쿠키 관리
                .build();
        return httpClient;
    }

    private MultiValueMap<String, String> createRequestBody(WeinLoginRequest weinLoginRequest) {
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("userId", weinLoginRequest.getUserId());
        requestBody.add("pw", weinLoginRequest.getPassword());
        requestBody.add("rtnUrl", ""); // 리다이렉트 후 이동할 URL 지정, 필요시 수정
        return requestBody;
    }

}
