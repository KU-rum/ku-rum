package ku_rum.backend.global.config;

import ku_rum.backend.domain.oauth.application.CustomOAuth2UserService;
import ku_rum.backend.domain.user.domain.repository.UserRepository;
import ku_rum.backend.global.security.jwt.CustomUserDetails;
import ku_rum.backend.global.security.jwt.JwtTokenAuthenticationFilter;
import ku_rum.backend.global.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Collection;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisUtil redisUtil;
    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.formLogin(AbstractHttpConfigurer::disable);
        http.httpBasic(AbstractHttpConfigurer::disable);

        // 요청에 대한 권한 설정
        http.authorizeHttpRequests((auth) -> auth
                .requestMatchers("/api/auth/login", "/api/v1/users").permitAll()
                .anyRequest().authenticated());
        // JWT 토큰 인증 필터 추가
        http.addFilterBefore(new JwtTokenAuthenticationFilter(jwtTokenProvider, redisUtil), UsernamePasswordAuthenticationFilter.class);

//        http.logout(logout -> logout.logoutSuccessUrl("/"));
        http.oauth2Login(oauth2 -> oauth2
                .defaultSuccessUrl("/oauth/loginInfo", true) // 로그인 성공 후 이동할 URL
                .userInfoEndpoint(userInfo -> userInfo
                        .userService(customOAuth2UserService) // 로그인 성공 후 사용할 서비스 로직 설정
                )
        );

        http.sessionManagement((session) -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return email -> userRepository.findUserByEmail(email)
                .map(u -> CustomUserDetails.of(u.getId(),
                        u.getEmail(),
                        AuthorityUtils.createAuthorityList(u.getRoles().toArray(new String[0])),
                        u.getPassword()))
                .orElseThrow(() -> new UsernameNotFoundException("해당 유저가 존재하지 않습니다!"));
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService,
                                                       PasswordEncoder passwordEncoder)  {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(provider);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
