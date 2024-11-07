package ku_rum.backend.domain.user.presentation;

import jakarta.validation.Valid;
import ku_rum.backend.domain.user.application.MailService;
import ku_rum.backend.domain.user.application.UserService;
import ku_rum.backend.domain.user.dto.request.MailSendRequest;
import ku_rum.backend.domain.user.dto.request.MailVerificationRequest;
import ku_rum.backend.domain.user.dto.response.MailVerificationResponse;
import ku_rum.backend.global.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/mails")
@RequiredArgsConstructor
@Validated
public class MailController {
    private final MailService mailService;

    @PostMapping
    public BaseResponse<Void> sendMail(@RequestBody @Valid final MailSendRequest mailSendRequest) {
        mailService.sendCodeToEmail(mailSendRequest);
        return BaseResponse.ok(null);
    }

    @GetMapping("/mail_verifications")
    public BaseResponse<MailVerificationResponse> verificationEmail(@RequestBody @Valid final MailVerificationRequest mailVerificationRequest) {
        return BaseResponse.ok(mailService.verifiedCode(mailVerificationRequest));
    }
}
