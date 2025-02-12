package ku_rum.backend.domain.auth.dto.request;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "사용자 아이디는 필수입니다.") String loginId,
        @NotBlank(message = "사용자 비밀번호는 필수입니다.") String password
) {
}
