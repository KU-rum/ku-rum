package ku_rum.backend.domain.mail.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginIdValidationRequest(
        @NotBlank(message = "아이디 입력은 필수입니다. 최소 6자 이상입니다.")
        @Size(min = 6) String loginId
) {
    public LoginIdValidationRequest(String loginId) {
        this.loginId = loginId;
    }
}
