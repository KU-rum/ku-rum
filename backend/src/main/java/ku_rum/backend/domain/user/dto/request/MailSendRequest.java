package ku_rum.backend.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MailSendRequest {

    @NotBlank(message = "이메일 입력은 필수입니다.")
    @Pattern(regexp = "[a-zA-Z0-9_.+-]+@konkuk\\.ac\\.kr$", message = "이메일의 끝자리는 @konkuk.ac.kr로 끝나야 합니다.")
    private String email;

    public MailSendRequest(String email) {
        this.email = email;
    }
}
