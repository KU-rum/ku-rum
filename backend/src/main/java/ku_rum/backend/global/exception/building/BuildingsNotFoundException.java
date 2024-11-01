package ku_rum.backend.global.exception.building;

import ku_rum.backend.global.response.status.BaseExceptionResponseStatus;
import ku_rum.backend.global.response.status.ResponseStatus;
import lombok.Getter;

@Getter
public class BuildingsNotFoundException extends BuildingBaseException {

  public BuildingsNotFoundException(String message, BaseExceptionResponseStatus status) {
    super(message, status);
  }
}
