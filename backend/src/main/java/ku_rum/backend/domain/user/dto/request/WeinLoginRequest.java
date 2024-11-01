package ku_rum.backend.domain.user.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeinLoginRequest {
    private String userId;
    private String password;
}
