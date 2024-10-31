package ku_rum.backend.global.exception.user;

import ku_rum.backend.global.response.status.ResponseStatus;
import lombok.Getter;

@Getter
public class DuplicateEmailException extends RuntimeException {
    private final ResponseStatus exceptionStatus;

    public DuplicateEmailException(ResponseStatus exceptionStatus) {
        super(exceptionStatus.getMessage());
        this.exceptionStatus = exceptionStatus;
    }

    public DuplicateEmailException(ResponseStatus exceptionStatus, String message) {
        super(message);
        this.exceptionStatus = exceptionStatus;
    }

}
