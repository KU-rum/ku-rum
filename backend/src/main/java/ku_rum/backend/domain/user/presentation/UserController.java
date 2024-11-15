package ku_rum.backend.domain.user.presentation;

import jakarta.validation.Valid;
import ku_rum.backend.domain.user.application.MailService;
import ku_rum.backend.domain.user.application.UserService;
import ku_rum.backend.domain.user.dto.request.MailSendRequest;
import ku_rum.backend.domain.user.dto.request.EmailValidationRequest;
import ku_rum.backend.domain.user.dto.request.MailVerificationRequest;
import ku_rum.backend.domain.user.dto.request.UserSaveRequest;
import ku_rum.backend.domain.user.dto.request.WeinLoginRequest;
import ku_rum.backend.domain.user.dto.response.MailVerificationResponse;
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

    @PostMapping
    public BaseResponse<UserSaveResponse> join(@RequestBody @Valid final UserSaveRequest userSaveRequest) {
        return BaseResponse.ok(userService.saveUser(userSaveRequest));
    }


    @PostMapping("/weinlogin")
    public BaseResponse<WeinLoginResponse> loginToWein(@RequestBody @Valid WeinLoginRequest weinLoginRequest) {
//        BaseResponse<WeinLoginResponse> weinLoginResponseBaseResponse = userService.loginToWein(weinLoginRequest);
        return userService.loginToWein(weinLoginRequest);
    }

    @PostMapping("/validations/email")
    public BaseResponse<Void> validateEmail(@RequestBody @Valid final EmailValidationRequest emailValidationRequest) {
        userService.validateEmail(emailValidationRequest);
        return BaseResponse.ok(null);

    }

}
