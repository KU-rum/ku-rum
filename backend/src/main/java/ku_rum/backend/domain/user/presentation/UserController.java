package ku_rum.backend.domain.user.presentation;

import jakarta.validation.Valid;
import ku_rum.backend.domain.user.dto.request.ResetAccountRequest;
import ku_rum.backend.domain.reservation.dto.request.WeinLoginRequest;
import ku_rum.backend.domain.user.application.UserService;
import ku_rum.backend.domain.mail.dto.request.LoginIdValidationRequest;
import ku_rum.backend.domain.user.dto.request.UserSaveRequest;
import ku_rum.backend.domain.user.dto.response.UserSaveResponse;
import ku_rum.backend.domain.user.dto.response.WeinLoginResponse;
import ku_rum.backend.global.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Validated
public class UserController {
    private final UserService userService;

    /**
     * 유저 회원가입 API
     * @param userSaveRequest loginId, nickname, password, studentId, department
     * @return userSaveResponse userId
     */
    @PostMapping
    public BaseResponse<UserSaveResponse> join(@RequestBody @Valid final UserSaveRequest userSaveRequest) {
        return BaseResponse.ok(userService.saveUser(userSaveRequest));
    }

    /**
     * 위인전 로그인 API
     * @Param WeinLoginRequest
     * @return WeinLoginResponse
     */
    @PostMapping("/weinlogin")
    public BaseResponse<WeinLoginResponse> loginToWein(@RequestBody @Valid WeinLoginRequest weinLoginRequest) {
        return userService.loginToWein(weinLoginRequest);

    }

    /**
     * 아이디 중복인증 확인 API
     * @param loginIdValidationRequest
     * @return userSaveResponse userId
     */
    @PostMapping("/validations/email")
    public BaseResponse<String> validateEmail(@RequestBody @Valid final LoginIdValidationRequest loginIdValidationRequest) {
        userService.validateEmail(loginIdValidationRequest);
        return BaseResponse.ok("올바른 이메일 입니다.");
    }

    /**
     * 아이디/비밀번호 재설정 API
     * @param resetAccountRequest
     * @return userSaveResponse userId
     */
    @PostMapping("/reset-account")
    public BaseResponse<String> resetAccount(@RequestBody @Valid final ResetAccountRequest resetAccountRequest) {
        userService.resetAccount(resetAccountRequest);
        return BaseResponse.ok("아이디/비밀번호가 변경되었습니다.");
    }

}
