package ku_rum.backend.domain.building.dto.response;

import ku_rum.backend.domain.building.domain.Building;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

public record BuildingResponse (
        Long buildingId,
        String buildingName,
        Long buildingNumber,
        String bulidingAbbreviation,
        BigDecimal latitude,
        BigDecimal longtitude
){

  @Builder
  public BuildingResponse(Long buildingId, String buildingName, Long buildingNumber, String bulidingAbbreviation, BigDecimal latitude, BigDecimal longtitude) {
    this.buildingId = buildingId;
    this.buildingName = buildingName;
    this.buildingNumber = buildingNumber;
    this.bulidingAbbreviation = bulidingAbbreviation;
    this.latitude = latitude;
    this.longtitude = longtitude;
  }

  public static BuildingResponse of(Building building) {
    return BuildingResponse.builder()
            .buildingId(building.getId())
            .buildingName(building.getName())
            .buildingNumber(building.getNumber())
            .bulidingAbbreviation(building.getAbbreviation())
            .latitude(building.getLatitude())
            .longtitude(building.getLongitude())
            .build();
  }
}
