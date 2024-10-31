package ku_rum.backend.domain.building.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import ku_rum.backend.domain.building.dto.BuildingClassResponseDto;
import ku_rum.backend.global.response.BaseResponse;
import ku_rum.backend.global.response.status.BaseExceptionResponseStatus;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
public class BuildingsResponse extends BaseResponse {

  public BuildingsResponse(BaseExceptionResponseStatus status, List<BuildingClassResponseDto> buildings, String message) {
    super(status.getStatus(), message, buildings);
  }
}