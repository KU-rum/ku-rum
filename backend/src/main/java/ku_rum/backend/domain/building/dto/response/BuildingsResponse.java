package ku_rum.backend.domain.building.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import ku_rum.backend.domain.building.dto.BuildingClassResponseDto;
import ku_rum.backend.global.response.BaseResponse;
import ku_rum.backend.global.response.status.BaseExceptionResponseStatus;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
public class BuildingsResponse extends BaseResponse {

  public BuildingsResponse(BaseExceptionResponseStatus status, Object data, String message) {
    super(status.getStatus(), message, data);
  }

  public static BuildingsResponse ofList(
          BaseExceptionResponseStatus status,
          List<BuildingClassResponseDto> data,
          String message
  ) {
    return new BuildingsResponse(status, data, message);
  }

  public static BuildingsResponse ofSingle(
          BaseExceptionResponseStatus status,
          BuildingClassResponseDto data,
          String message
  ) {
    return new BuildingsResponse(status, data, message);
  }
}