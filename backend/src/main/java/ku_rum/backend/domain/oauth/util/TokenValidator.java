package ku_rum.backend.domain.oauth.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
@Slf4j
@Component
public class TokenValidator {
    private static final String NAVER_TOKEN_INFO_URL = "https://openapi.naver.com/v1/nid/me";

    public boolean validateAccessToken(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();

        // 요청 헤더 구성
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        log.info("Validating Access Token with headers: {}", headers);

        try {
            // 요청 전송
            ResponseEntity<Map> response = restTemplate.getForEntity(
                    NAVER_TOKEN_INFO_URL,
                    Map.class,
                    headers
            );

            log.info("Token validation response: {}", response);
            // 응답 상태 확인
            if (response.getStatusCode() == HttpStatus.OK) {
                Map<String, Object> responseBody = response.getBody();
                if (responseBody != null && responseBody.get("response") != null) {
                    log.info("Access Token이 유효");
                    // Access Token이 유효함
                    return true;
                }
            }
        } catch (Exception e) {
            // Access Token이 유효하지 않거나 네트워크 오류 발생
            System.out.println("Access Token 검증 실패: " + e.getMessage());
        }

        return false;
    }
}
