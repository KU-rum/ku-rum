package ku_rum.backend.domain.building.exception;

public class BuildingsNotFoundException extends RuntimeException {
  public BuildingsNotFoundException() {
  }

  public BuildingsNotFoundException(String message) {
    super(message);
  }
}
