package ku_rum.backend.domain.user.presentation;

import jakarta.validation.Valid;
import ku_rum.backend.domain.user.application.MailService;
import ku_rum.backend.domain.user.application.UserService;
import ku_rum.backend.domain.user.dto.request.MailSendRequest;
import ku_rum.backend.domain.user.dto.request.EmailValidationRequest;
import ku_rum.backend.domain.user.dto.request.MailVerificationRequest;
import ku_rum.backend.domain.user.dto.request.UserSaveRequest;
import ku_rum.backend.domain.user.dto.response.MailVerificationResponse;
import ku_rum.backend.domain.user.dto.response.UserSaveResponse;
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

    @PostMapping("/validations/email")
    public BaseResponse<Void> validateEmail(@RequestBody @Valid final EmailValidationRequest emailValidationRequest) {
        userService.validateEmail(emailValidationRequest);
        return BaseResponse.ok(null);
    }

    @PostMapping("me/mails")
    public BaseResponse<Void> sendMail(@RequestBody @Valid final MailSendRequest mailSendRequest) {
        userService.sendCodeToEmail(mailSendRequest);
        return BaseResponse.ok(null);
    }

    @GetMapping("me/mail_verifications")
    public BaseResponse<MailVerificationResponse> verificationEmail(@RequestBody @Valid final MailVerificationRequest mailVerificationRequest) {
        return BaseResponse.ok(userService.verifiedCode(mailVerificationRequest));
    }
}
