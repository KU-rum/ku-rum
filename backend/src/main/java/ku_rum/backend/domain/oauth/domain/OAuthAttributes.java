package ku_rum.backend.domain.oauth.domain;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;

@Getter
@Slf4j
public enum OAuthAttributes {


    GOOGLE("google", (attributes) -> {
        UserProfile userProfile = new UserProfile();
        userProfile.setUserName((String) attributes.get("name"));
        userProfile.setEmail((String) attributes.get("email"));
        return userProfile;
    }),

    NAVER("naver", (attributes) -> {
        UserProfile userProfile = new UserProfile();

        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        userProfile.setUserName((String) response.get("name"));
        userProfile.setEmail((String) response.get("email"));
        return userProfile;
    }),

    KAKAO("kakao", (attributes) -> {
        Map<String, Object> account = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) account.get("profile");

        UserProfile userProfile = new UserProfile();
        userProfile.setUserName((String) profile.get("nickname"));
        userProfile.setEmail((String) account.get("email"));
        return userProfile;
    });

    private final String registrationId; // OAuth 플랫폼 이름
    private final Function<Map<String, Object>, UserProfile> of; // 사용자 정보를 UserProfile로 변환하는 함수

    OAuthAttributes(String registrationId, Function<Map<String, Object>, UserProfile> of) {
        this.registrationId = registrationId;
        this.of = of;
    }

    public static UserProfile extract(String registrationId, Map<String, Object> attributes) {
        return Arrays.stream(OAuthAttributes.values())
                .filter(value -> value.registrationId.equals(registrationId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unsupported OAuth Provider"))
                .of.apply(attributes);
    }

//    private final Map<String, Object> attributes;
//    private final String nameAttributeKey;
//    private final String name;
//    private final String email;
//
//    @Builder
//    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email) {
//        this.attributes = attributes;
//        this.nameAttributeKey = nameAttributeKey;
//        this.name = name;
//        this.email = email;
//    }
//
//    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
//        log.info("Creating OAuthAttributes for registrationId: {}", registrationId);
//        if ("naver".equals(registrationId)) {
//            return ofNaver(userNameAttributeName, attributes);
//        }
//        // 구글이나 다른 제공자에 대한 처리 추가 가능
//        return ofGoogle(userNameAttributeName, attributes);
//    }
//
//    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
//        log.info("Processing NAVER attributes: {}", attributes);
//
//        return OAuthAttributes.builder()
//                .name((String) attributes.get("name"))
//                .email((String) attributes.get("email"))
//                .attributes(attributes)
//                .nameAttributeKey("id")
//                .build();
//    }
//
//    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
//        return OAuthAttributes.builder()
//                .name((String) attributes.get("name"))
//                .email((String) attributes.get("email"))
//                .attributes(attributes)
//                .nameAttributeKey(userNameAttributeName)
//                .build();
//    }
//    public Map<String, Object> convertToMap() {
//        Map<String, Object> map = new HashMap<>();
//        map.put("id", attributes.getOrDefault("id", "default-id")); // Default id if not present
//        map.put("name", name != null ? name : "default-name");
//        map.put("email", email != null ? email : "default-email");
////        map.put("picture", attributes.getOrDefault("picture", "default-picture")); // Optional: Add picture field
//        return map;
//    }
}