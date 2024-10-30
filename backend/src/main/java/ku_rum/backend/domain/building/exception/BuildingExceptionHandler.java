package ku_rum.backend.domain.building.exception;

import ku_rum.backend.domain.building.dto.response.BuildingsResponse;
import ku_rum.backend.global.exception.ErrorCode;
import ku_rum.backend.global.type.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BuildingExceptionHandler {

  @ExceptionHandler(BuildingsNotFoundException.class)
  public ResponseEntity<ApiResponse> handleBuildingNotFoundException(BuildingsNotFoundException e) {
    ErrorCode errorCode = ErrorCode.Building_NotFound_ERROR;
    ApiResponse response = new BuildingsResponse(
            null,
            errorCode.getMessage()
    );
    return ResponseEntity
            .status(errorCode.getStatus())
            .body(response);
  }
}