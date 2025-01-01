package ku_rum.backend.global.exception.friend;

import ku_rum.backend.global.response.status.ResponseStatus;
import lombok.Getter;

@Getter
public class NoFriendsException extends RuntimeException {
    private final ResponseStatus exceptionStatus;

    public NoFriendsException(ResponseStatus exceptionStatus) {
        super(exceptionStatus.getMessage());
        this.exceptionStatus = exceptionStatus;
    }

    public NoFriendsException(ResponseStatus exceptionStatus, String message) {
        super(message);
        this.exceptionStatus = exceptionStatus;
    }

}
