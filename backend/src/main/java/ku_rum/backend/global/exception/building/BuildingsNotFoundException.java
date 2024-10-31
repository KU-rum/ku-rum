package ku_rum.backend.global.exception.building;

import ku_rum.backend.global.response.status.ResponseStatus;

public class BuildingsNotFoundException extends RuntimeException {

  private final ResponseStatus exceptionStatus;

  public BuildingsNotFoundException(ResponseStatus exceptionStatus) {
    super(exceptionStatus.getMessage());
    this.exceptionStatus = exceptionStatus;
  }

  public BuildingsNotFoundException(ResponseStatus exceptionStatus, String message) {
    super(message);
    this.exceptionStatus = exceptionStatus;
  }
}
