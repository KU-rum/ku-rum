package ku_rum.backend.domain.mail.presentation;

import jakarta.validation.Valid;
import ku_rum.backend.domain.mail.application.MailService;
import ku_rum.backend.domain.mail.dto.request.MailSendRequest;
import ku_rum.backend.domain.mail.dto.request.MailVerificationRequest;
import ku_rum.backend.domain.mail.dto.response.MailVerificationResponse;
import ku_rum.backend.global.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static ku_rum.backend.domain.mail.domain.MailSendSetting.MAIL_SEND_INFO;

@RestController
@RequestMapping("/api/v1/mails")
@RequiredArgsConstructor
@Validated
public class MailController {
    private final MailService mailService;

    /**
     * 유저 회원가입 시, 건국대 웹 메일로 인증코드 전송
     * @param mailSendRequest
     * @return String 메일이 성공적으로 전송되었습니다.
     */
    @PostMapping
    public BaseResponse<String> sendMail(@RequestBody @Valid final MailSendRequest mailSendRequest) {
        mailService.sendCodeToEmail(mailSendRequest);
        return BaseResponse.ok(MAIL_SEND_INFO.getSUCCESS_MESSAGE());
    }

    /**
     * 유저 회원가입 시, 적절한 인증코드인지 확인
     * @param mailVerificationRequest
     * @return OK
     */
    @GetMapping("/mail_verifications")
    public BaseResponse<MailVerificationResponse> verificationEmail(@RequestBody @Valid final MailVerificationRequest mailVerificationRequest) {
        return BaseResponse.ok(mailService.verifiedCode(mailVerificationRequest));
    }

}
