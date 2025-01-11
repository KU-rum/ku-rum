package ku_rum.backend.domain.user.application;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import ku_rum.backend.domain.user.domain.repository.UserRepository;
import ku_rum.backend.domain.user.dto.request.AuthRequest;
import ku_rum.backend.domain.user.dto.request.ReissueRequest;
import ku_rum.backend.global.config.RedisUtil;
import ku_rum.backend.global.security.jwt.*;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.Token;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Date;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtTokenAuthenticationFilter jwtTokenAuthenticationFilter;
    private final RedisUtil redisUtil;

    public TokenResponse login(AuthRequest authRequest) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.email(),
                authRequest.password()));

        return jwtTokenProvider.createToken(authenticate);
    }

    public void logout(HttpServletRequest request) {
        String token = jwtTokenAuthenticationFilter.resolveToken(request);
        jwtTokenProvider.validateToken(token);

        long expiredAccessTokenTime = jwtTokenProvider.getExpiredTime(token) - new Date().getTime();
        Long userId = jwtTokenProvider.getUserId(token);

        redisUtil.setBlackList(token, "logout", Duration.ofMillis(expiredAccessTokenTime));
        redisUtil.deleteRedisData(String.valueOf(userId));
    }

    public TokenResponse reissue(HttpServletRequest request, ReissueRequest reissueRequest) {
        jwtTokenProvider.validateToken(reissueRequest.refreshToken());

        Authentication authenticate = jwtTokenProvider.getAuthentication(reissueRequest.refreshToken());
        CustomUserDetails principal = (CustomUserDetails) authenticate.getPrincipal();

        Long userId = principal.getUserId();
        String redisRefreshToken = redisUtil.getRedisData(String.valueOf(userId));

        if (redisRefreshToken != null && redisRefreshToken.equals(reissueRequest.refreshToken())) {
            redisUtil.deleteRedisData(String.valueOf(userId));
            return jwtTokenProvider.createToken(authenticate);
        }
        throw new JwtException("Invalid refresh token");
    }
}
