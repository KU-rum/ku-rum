package ku_rum.backend.domain.user.application;

import ku_rum.backend.domain.department.domain.Department;
import ku_rum.backend.domain.department.domain.repository.DepartmentRepository;
import ku_rum.backend.domain.user.domain.MailSendSetting;
import ku_rum.backend.domain.user.domain.User;
import ku_rum.backend.domain.user.domain.repository.UserRepository;
import ku_rum.backend.domain.user.dto.request.EmailValidationRequest;
import ku_rum.backend.domain.user.dto.request.MailSendRequest;
import ku_rum.backend.domain.user.dto.request.MailVerificationRequest;
import ku_rum.backend.domain.user.dto.request.UserSaveRequest;
import ku_rum.backend.domain.user.dto.response.MailVerificationResponse;
import ku_rum.backend.domain.user.dto.response.UserSaveResponse;
import ku_rum.backend.global.config.MailConfig;
import ku_rum.backend.global.exception.department.NoSuchDepartmentException;
import ku_rum.backend.global.exception.user.DuplicateEmailException;
import ku_rum.backend.global.exception.user.DuplicateStudentIdException;
import ku_rum.backend.global.exception.user.MailSendException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;
import java.util.Random;

import static ku_rum.backend.domain.user.domain.MailSendSetting.*;
import static ku_rum.backend.global.config.MailConfig.*;
import static ku_rum.backend.global.response.status.BaseExceptionResponseStatus.*;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final RedisTemplate<String, String> redisTemplate;
    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;
    private final MailService mailService;

    @Transactional
    public UserSaveResponse saveUser(final UserSaveRequest userSaveRequest) {
        validateDuplicateEmail(userSaveRequest.getEmail());
        validateDuplicateStudentId(userSaveRequest.getStudentId());
        validateDepartmentName(userSaveRequest.getDepartment());

        // TODO Password Encoder 적용
        String password = userSaveRequest.getPassword();
        Department department = departmentRepository.findByName(userSaveRequest.getDepartment())
                .orElseThrow(() -> new NoSuchDepartmentException(NO_SUCH_DEPARTMENT));

        User user = User.builder()
                .nickname(userSaveRequest.getNickname())
                .email(userSaveRequest.getEmail())
                .password(password)
                .studentId(userSaveRequest.getStudentId())
                .department(department)
                .build();

        userRepository.save(user);
        return UserSaveResponse.of(user);
    }

    public void validateEmail(final EmailValidationRequest emailValidationRequest) {
        validateDuplicateEmail(emailValidationRequest.getEmail());
    }

    public void sendCodeToEmail(final MailSendRequest mailSendRequest) {
        validateDuplicateEmail(mailSendRequest.getEmail());

        String title = MAIL_SEND_INFO.getTitle();
        String authCode = this.createCode();

        mailService.sendEmail(mailSendRequest.getEmail(), title, authCode);
        storeAuthCode(mailSendRequest.getEmail(), authCode);
    }

    public MailVerificationResponse verifiedCode(final MailVerificationRequest mailVerificationRequest) {
        String email = mailVerificationRequest.getEmail();
        String authCode = mailVerificationRequest.getCode();

        validateDuplicateEmail(email);
        return MailVerificationResponse.of(isValidAuthCode(generateKeyByEmail(email), authCode));
    }

    private void storeAuthCode(String email, String authCode) {
        redisTemplate.opsForValue().set(generateKeyByEmail(email),
                authCode, getAuthCodeExpirationMills());
    }

    private Duration getAuthCodeExpirationMills() {
        return Duration.ofMillis(MAIL_SEND_INFO.getAuthCodeExpirationMillis());
    }

    private String generateKeyByEmail(String email) {
        return MAIL_SEND_INFO.getAUTH_CODE_PREFIX() + email;
    }

    private String getRedisAuthCode(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    private boolean checkExistsValue(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    private boolean isValidAuthCode(String key, String authCode) {
        return checkExistsValue(key) && getRedisAuthCode(key).equals(authCode);
    }

    private String createCode() {
        try {
            Random random = SecureRandom.getInstanceStrong();
            StringBuilder builder = new StringBuilder();

            for (int i = 0; i < MAIL_SEND_INFO.getCodeLength(); i++) {
                builder.append(random.nextInt(10));
            }
            return builder.toString();
        } catch (NoSuchAlgorithmException e) {
            log.debug("네자리 난수 생성 시 예외가 발생했습니다.");
            throw new MailSendException(INVALID_AUTH_CODE_GENERATION);
        }
    }

    private void validateDuplicateEmail(final String email) {
        if (userRepository.existsByEmail(email)) {
            throw new DuplicateEmailException(DUPLICATE_EMAIL);
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
}
