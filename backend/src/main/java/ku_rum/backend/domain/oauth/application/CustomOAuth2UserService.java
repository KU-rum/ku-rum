package ku_rum.backend.domain.oauth.application;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import ku_rum.backend.domain.oauth.domain.OAuthAttributes;
import ku_rum.backend.domain.oauth.domain.UserProfile;
import ku_rum.backend.domain.oauth.util.TokenValidator;
import ku_rum.backend.domain.user.domain.User;
import ku_rum.backend.domain.user.domain.repository.UserRepository;
import ku_rum.backend.global.security.jwt.CustomUserDetails;
import ku_rum.backend.global.security.jwt.JwtTokenProvider;
import ku_rum.backend.global.security.jwt.TokenResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public CustomOAuth2UserService(UserRepository userRepository, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        log.info("userRequest getClientRegistration : {}", userRequest.getClientRegistration());
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId(); // 로그인을 수행한 서비스의 이름
        String userNameAttributeName = userRequest
                .getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();// PK가 되는 정보

        log.info("Registration ID: {}", registrationId);
        log.info("UserName Attribute Name: {}", userNameAttributeName);

        // Attributes 로깅
        Map<String, Object> attributes = oAuth2User.getAttributes();
        log.info("Attributes received: {}", attributes);

        UserProfile userProfile = OAuthAttributes.extract(registrationId, attributes);

        // 사용자 저장 또는 업데이트
        User user = updateOrPrepareUser(userProfile);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                CustomUserDetails.of(
                        user.getId(),
                        userProfile.getUsername(),
                        Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")),
                        user.getPassword()
                ),
                null,
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
        );

        // TokenResponse를 통해 Access Token만 가져옴
        TokenResponse tokenResponse = jwtTokenProvider.createToken(authentication);
        String accessToken = tokenResponse.accessToken(); // Access Token만 추출

        // Access Token 디코딩 및 로그 출력
        try {
            Jws<Claims> jwsClaims = jwtTokenProvider.getClaimsFromToken(accessToken);
            Claims claims = jwsClaims.getBody(); // Claims 추출
            log.info("Access Token Claims: {}", claims);
        } catch (Exception e) {
            log.error("Failed to parse Access Token: {}", e.getMessage());
        }

        // 사용자 정보와 Access Token 반환
        Map<String, Object> customAttributes = getCustomAttributes(registrationId, userNameAttributeName, attributes, userProfile);
        customAttributes.put("accessToken", accessToken);

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                customAttributes,
                userNameAttributeName
        );
    }

    private Map<String, Object> getCustomAttributes(String registrationId, String userNameAttributeName,
                                                    Map<String, Object> attributes, UserProfile userProfile) {
        Map<String, Object> customAttributes = new HashMap<>();

        // 기존 OAuth2에서 가져온 attributes를 추가
        customAttributes.putAll(attributes);

        // Custom attributes 추가
        customAttributes.put("registrationId", registrationId); // 서비스 제공자 이름 (예: google, github 등)
        customAttributes.put("userNameAttributeName", userNameAttributeName); // PK로 사용되는 attribute 이름
        customAttributes.put("name", userProfile.getUsername()); // 사용자 이름
        customAttributes.put("email", userProfile.getEmail()); // 사용자 이메일
        customAttributes.put("provider", registrationId); // OAuth 제공자 (예: google, github 등)

        return customAttributes;
    }

    private User updateOrPrepareUser(UserProfile userProfile) {
        return userRepository.findUserByEmail(userProfile.getEmail())
                .orElseGet(() -> {
                    // 신규 사용자인 경우 User 객체만 생성 (저장은 하지 않음)
                    User user = userProfile.toEntity();
                    log.info("New user prepared: {}", user);
                    return user; // 저장 없이 반환
                });
//                .orElseGet(() -> userRepository.save(userProfile.toEntity()));
    }
}


