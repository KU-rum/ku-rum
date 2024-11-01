package ku_rum.backend.global.exception.building;

import ku_rum.backend.global.response.status.ResponseStatus;
import lombok.Getter;

@Getter
public class BuildingBaseException extends RuntimeException{
  private final ResponseStatus status;

  public BuildingBaseException(String message, ResponseStatus status) {
    super(message);
    this.status = status;
  }
}
