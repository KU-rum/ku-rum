package ku_rum.backend.global.exception.user;

import ku_rum.backend.global.response.status.ResponseStatus;
import lombok.Getter;

@Getter
public class DuplicateStudentIdException extends RuntimeException {
    private final ResponseStatus exceptionStatus;

    public DuplicateStudentIdException(ResponseStatus exceptionStatus) {
        super(exceptionStatus.getMessage());
        this.exceptionStatus = exceptionStatus;
    }

    public DuplicateStudentIdException(ResponseStatus exceptionStatus, String message) {
        super(message);
        this.exceptionStatus = exceptionStatus;
    }

}
