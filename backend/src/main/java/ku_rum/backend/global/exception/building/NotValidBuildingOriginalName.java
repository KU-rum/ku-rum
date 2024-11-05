package ku_rum.backend.global.exception.building;

import ku_rum.backend.global.response.status.BaseExceptionResponseStatus;

public class NotValidBuildingOriginalName extends RuntimeException{
  private BaseExceptionResponseStatus status;
  public NotValidBuildingOriginalName(BaseExceptionResponseStatus status) {
    this.status = status;
  }
}
