package ku_rum.backend.domain.oauth.domain;

import ku_rum.backend.domain.user.domain.User;
import lombok.Getter;

@Getter
public class UserProfile {
    private String username; // 사용자 이름
    private String provider; // 로그인한 서비스
    private String email; // 사용자의 이메일

    public void setUserName(String userName) {
        this.username = userName;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // DTO 파일을 통하여 Entity를 생성하는 메소드
    public User toEntity() {
        return User.builder()
                .email(this.email) // email 설정
                .nickname(this.username) // username을 nickname으로 설정
                .password(null) // OAuth 로그인에서는 비밀번호는 null로 처리
                .studentId(null) // studentId는 아직 입력되지 않았으므로 null
                .department(null) // department는 아직 입력되지 않았으므로 null
                .build();
    }
}
