package ku_rum.backend.global.security.jwt;

import ku_rum.backend.domain.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
@NoArgsConstructor
@ToString
public class CustomUserDetails implements UserDetails {
    private Long userId;
    private String username;
    private String email; // 이메일 추가
    private Collection<? extends GrantedAuthority> roles;
    private String password;

    private CustomUserDetails(Long userId, String username, Collection<? extends GrantedAuthority> role, String password) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.roles = role;
        this.password = password;
    }

    public static CustomUserDetails of(Long id, String username, Collection<? extends GrantedAuthority> role, String password) {
        return new CustomUserDetails(id, username, role, password);
    }

    public static CustomUserDetails of(Long id, String username, String email, Collection<? extends GrantedAuthority> roles, String password) {
        return new CustomUserDetails(id, username, email, roles, password);
    }

    public static CustomUserDetails of(User user) {
        return new CustomUserDetails(
                user.getId(),
                user.getNickname(),            // 닉네임을 username으로 사용
                user.getEmail(),               // email 필드에 저장
                AuthorityUtils.createAuthorityList("ROLE_USER"),
                user.getPassword()
        );
    }

    private CustomUserDetails(Long userId, String username, String email, Collection<? extends GrantedAuthority> roles, String password) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.roles = roles;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }
}
