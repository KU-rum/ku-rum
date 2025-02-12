package ku_rum.backend.domain.wein.dto.response;

import lombok.Getter;

@Getter
public class WeinLoginResponse {
    private boolean success;
    private String message;

    public WeinLoginResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}