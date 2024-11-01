package ku_rum.backend.global.exception.building;

import ku_rum.backend.global.response.status.ResponseStatus;

public class BuildingNotRegisteredException extends BuildingBaseException{
  public BuildingNotRegisteredException(String message, ResponseStatus status) {
    super(message, status);
  }
}