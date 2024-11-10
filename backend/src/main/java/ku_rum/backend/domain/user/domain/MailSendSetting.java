package ku_rum.backend.domain.user.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@Getter
@RequiredArgsConstructor
public enum MailSendSetting {
    MAIL_SEND_INFO("쿠룸 이메일 인증 번호",4, "AuthCode ", 1800000);

    private final String title;
    private final int codeLength;
    private final String AUTH_CODE_PREFIX;
    private final long authCodeExpirationMillis;

}
