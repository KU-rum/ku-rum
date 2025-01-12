package ku_rum.backend.global.security.jwt;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
@NoArgsConstructor
@ToString
public class CustomUserDetails implements UserDetails {
    private Long userId;
    private String username;
    private Collection<? extends GrantedAuthority> roles;
    private String password;

    private CustomUserDetails(Long userId, String username, Collection<? extends GrantedAuthority> role, String password) {
        this.userId = userId;
        this.username = username;
        this.roles = role;
        this.password = password;
    }

    public static CustomUserDetails of(Long id, String username, Collection<? extends GrantedAuthority> role, String password) {
        return new CustomUserDetails(id, username, role, password);
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
