package ku_rum.backend.global.config.security;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum AuthorizationList {
    LIST(Collections.unmodifiableList(Arrays.asList(
            "/api/v1/auth/login",
            "/api/v1/users",
            "/docs/**",
            "/api/v1/users/validations/loginId",
            "/api/v1/users/weinlogin",
            "/api/v1/users/reset-account"
    )));

    private List<String> authorities;

    AuthorizationList(List<String> authorities) {
        this.authorities = authorities;
    }

    public List<String> getAuthorities() {
        return authorities;
    }

    @Override
    public String toString() {
        return String.join(", ", authorities);
    }
}
