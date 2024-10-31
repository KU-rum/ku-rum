package ku_rum.backend.global.exception.department;

import ku_rum.backend.global.response.status.ResponseStatus;
import lombok.Getter;

@Getter
public class NoSuchDepartmentException extends RuntimeException {
    private final ResponseStatus exceptionStatus;

    public NoSuchDepartmentException(ResponseStatus exceptionStatus) {
        super(exceptionStatus.getMessage());
        this.exceptionStatus = exceptionStatus;
    }

    public NoSuchDepartmentException(ResponseStatus exceptionStatus, String message) {
        super(message);
        this.exceptionStatus = exceptionStatus;
    }

}
