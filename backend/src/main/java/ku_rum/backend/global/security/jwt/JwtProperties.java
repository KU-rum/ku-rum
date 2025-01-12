package ku_rum.backend.global.security.jwt;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class JwtProperties {

    @Value("${secret.jwt-secret-key}")
    private String secret;

    @Value("${secret.accessToken-valid-time}")
    private long accessTokenValiditySeconds;

    @Value("${secret.refreshToken-valid-time}")
    private long refreshTokenValiditySeconds;
}
