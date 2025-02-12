package ku_rum.backend.domain.auth.application;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import ku_rum.backend.domain.auth.dto.request.LoginRequest;
import ku_rum.backend.domain.auth.dto.request.ReissueRequest;
import ku_rum.backend.global.config.redis.RedisUtil;
import ku_rum.backend.global.security.jwt.*;
import ku_rum.backend.global.security.jwt.dto.TokenResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Date;

import static ku_rum.backend.global.response.status.BaseExceptionResponseStatus.MALFORMED_TOKEN;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtTokenAuthenticationFilter jwtTokenAuthenticationFilter;
    private final RedisUtil redisUtil;

    public TokenResponse login(LoginRequest authRequest) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.loginId(),
                authRequest.password()));

        return jwtTokenProvider.createToken(authenticate);
    }

    public void logout(HttpServletRequest request) {
        String token = validateAccessToken(request);

        long runtime = new Date().getTime();
        long expiredAccessTokenTime = jwtTokenProvider.getExpiredTime(token) - runtime;

        Long userId = jwtTokenProvider.getUserId(token);
        setBlackListInRedis(token, expiredAccessTokenTime, userId);
    }

    public TokenResponse reissue(ReissueRequest reissueRequest) {
        jwtTokenProvider.validateToken(reissueRequest.refreshToken());

        Authentication authenticate = jwtTokenProvider.getAuthentication(reissueRequest.refreshToken());
        CustomUserDetails principal = (CustomUserDetails) authenticate.getPrincipal();

        if (TokenCheckInRedis(reissueRequest, principal)) return jwtTokenProvider.createToken(authenticate);

        log.error(MALFORMED_TOKEN.getMessage());
        throw new JwtException(MALFORMED_TOKEN.getMessage());
    }

    private boolean TokenCheckInRedis(ReissueRequest reissueRequest, CustomUserDetails principal) {
        Long userId = principal.getUserId();
        String redisRefreshToken = redisUtil.getRedisData(String.valueOf(userId));

        if (redisRefreshToken != null && redisRefreshToken.equals(reissueRequest.refreshToken())) {
            redisUtil.deleteRedisData(String.valueOf(userId));
            return true;
        }
        return false;
    }

    private void setBlackListInRedis(String token, long expiredAccessTokenTime, Long userId) {
        redisUtil.setBlackList(token, "logout", Duration.ofMillis(expiredAccessTokenTime));
        redisUtil.deleteRedisData(String.valueOf(userId));
    }

    private String validateAccessToken(HttpServletRequest request) {
        String token = jwtTokenAuthenticationFilter.resolveToken(request);
        jwtTokenProvider.validateToken(token);
        return token;
    }
}
