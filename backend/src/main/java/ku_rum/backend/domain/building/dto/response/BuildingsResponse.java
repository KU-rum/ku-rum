package ku_rum.backend.domain.building.dto.response;

import ku_rum.backend.domain.building.dto.BuildingResponseDto;
import ku_rum.backend.global.response.BaseResponse;
import ku_rum.backend.global.response.status.BaseExceptionResponseStatus;
import lombok.Getter;

import java.util.List;

@Getter
public class BuildingsResponse extends BaseResponse {

  public BuildingsResponse(BaseExceptionResponseStatus status, Object data, String message) {
    super(status.getStatus(), message, data);
  }

  public static BuildingsResponse ofList(
          BaseExceptionResponseStatus status,
          List<BuildingResponseDto> data,
          String message
  ) {
    return new BuildingsResponse(status, data, message);
  }

  public static BuildingsResponse ofSingle(
          BaseExceptionResponseStatus status,
          BuildingResponseDto data,
          String message
  ) {
    return new BuildingsResponse(status, data, message);
  }
}