package ku_rum.backend.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EmailValidationRequest {

    @NotBlank(message = "아이디 입력은 필수입니다. 최소 6자 이상입니다.")
    @Size(min = 6)
    private String email;

    public EmailValidationRequest(String email) {
        this.email = email;
    }
}
