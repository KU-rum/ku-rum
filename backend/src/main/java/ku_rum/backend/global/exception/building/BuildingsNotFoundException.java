package ku_rum.backend.global.exception.building;

import ku_rum.backend.global.response.status.ResponseStatus;

public class BuildingsNotFoundException extends BuildingBaseException {

  public BuildingsNotFoundException(String message, ResponseStatus status) {
    super(message, status);
  }
}
