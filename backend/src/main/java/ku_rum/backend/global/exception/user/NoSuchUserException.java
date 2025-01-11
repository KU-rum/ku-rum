package ku_rum.backend.global.exception.user;

import ku_rum.backend.global.response.status.ResponseStatus;

public class NoSuchUserException extends RuntimeException {
    private final ResponseStatus status;

  public NoSuchUserException(ResponseStatus exceptionStatus) {
    super(exceptionStatus.getMessage());
    this.status = exceptionStatus;
  }
}
