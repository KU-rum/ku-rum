package ku_rum.backend.global.exception.building;

import ku_rum.backend.global.response.status.BaseExceptionResponseStatus;
import lombok.Getter;

@Getter
public class BuildingNotRegisteredException extends RuntimeException{

  private BaseExceptionResponseStatus status;
  public BuildingNotRegisteredException(BaseExceptionResponseStatus status) {
    this.status = status;
  }
}