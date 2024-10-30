package ku_rum.backend.domain.building.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import ku_rum.backend.domain.building.dto.BuildingClassResponseDto;
import ku_rum.backend.global.type.ApiResponse;
import lombok.Getter;

import java.util.List;

@Getter
public class BuildingsResponse extends ApiResponse {
  @JsonProperty("buildings")
  private final List<BuildingClassResponseDto> data;

  public BuildingsResponse(List<BuildingClassResponseDto> buildings){
    super(200,"요청에 성공하였습니다.");
    this.data = buildings;
  }
  public BuildingsResponse(List<BuildingClassResponseDto> buildings, String message) {
    super(200, message);
    this.data = buildings;
  }
}