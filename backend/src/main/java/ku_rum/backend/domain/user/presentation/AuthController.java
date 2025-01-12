package ku_rum.backend.domain.user.presentation;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import ku_rum.backend.domain.user.application.AuthService;
import ku_rum.backend.domain.user.dto.request.AuthRequest;
import ku_rum.backend.domain.user.dto.request.ReissueRequest;
import ku_rum.backend.global.response.BaseResponse;
import ku_rum.backend.global.security.jwt.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public BaseResponse<TokenResponse> login(@Valid @RequestBody AuthRequest authRequest) {
        return BaseResponse.ok(authService.login(authRequest));
    }

    @PatchMapping("/logout")
    public BaseResponse<String> logout(HttpServletRequest request) {
        authService.logout(request);
        return BaseResponse.ok("로그아웃이 완료되었습니다.");
    }

    @PatchMapping("/reissue")
    public BaseResponse<TokenResponse> reissue(HttpServletRequest request,
                                               @Valid @RequestBody ReissueRequest reissueRequest) {
        return BaseResponse.ok(authService.reissue(request, reissueRequest));
    }
}
