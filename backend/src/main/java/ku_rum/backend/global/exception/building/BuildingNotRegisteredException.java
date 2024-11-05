package ku_rum.backend.global.exception.building;

import ku_rum.backend.global.response.status.BaseExceptionResponseStatus;
import ku_rum.backend.global.response.status.ResponseStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.function.Supplier;

@Getter
public class BuildingNotRegisteredException extends RuntimeException{

  private BaseExceptionResponseStatus status;
  public BuildingNotRegisteredException(BaseExceptionResponseStatus status) {
    this.status = status;
  }
}