package ku_rum.backend.global.exception.notice;

import ku_rum.backend.global.response.status.ResponseStatus;
import lombok.Getter;

@Getter
public class NoSuchNoticeException extends RuntimeException {
    private final ResponseStatus exceptionStatus;

    public NoSuchNoticeException(ResponseStatus exceptionStatus) {
        super(exceptionStatus.getMessage());
        this.exceptionStatus = exceptionStatus;
    }

    public NoSuchNoticeException(ResponseStatus exceptionStatus, String message) {
        super(message);
        this.exceptionStatus = exceptionStatus;
    }
}
