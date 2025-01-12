package ku_rum.backend.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class WeinLoginRequest {
    @NotBlank(message = "사용자 아이디는 필수입니다.")
    private String userId;

    @NotBlank(message = "사용자 비밀번호는 필수입니다.")
    private String password;
}