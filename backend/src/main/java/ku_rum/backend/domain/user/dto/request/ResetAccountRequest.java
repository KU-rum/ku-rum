package ku_rum.backend.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ResetAccountRequest
        (@NotBlank(message = "기존 이메일 입력은 필수입니다.") @Size(min = 6) String email,
         @NotBlank(message = "아이디 입력은 필수입니다. 최소 6자 이상입니다.")  @Size(min = 6) String loginId,
         @NotNull(message = "비밀번호 입력은 필수입니다.") @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9]).{8,20}$", message = "비밀번호는 영어와 숫자를 포함해서 8자 이상 20자 이내로 입력해주세요.") String password) {

}
