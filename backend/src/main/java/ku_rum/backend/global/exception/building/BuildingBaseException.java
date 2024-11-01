package ku_rum.backend.global.exception.building;

import ku_rum.backend.global.response.status.BaseExceptionResponseStatus;

public class BuildingBaseException extends RuntimeException {
  private final BaseExceptionResponseStatus status;

  public BuildingBaseException(String message, BaseExceptionResponseStatus status) {
    super(message);
    this.status = status;
  }
}
