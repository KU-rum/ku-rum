package ku_rum.backend.global.exception.building;

import ku_rum.backend.global.response.status.BaseExceptionResponseStatus;
import ku_rum.backend.global.response.status.ResponseStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class BuildingNotRegisteredException extends BuildingBaseException{

  public BuildingNotRegisteredException(String message, BaseExceptionResponseStatus status) {
    super(message, status);
  }
}