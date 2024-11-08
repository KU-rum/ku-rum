package ku_rum.backend.domain.building.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class BuildingResponse {

  private Long buildingId;
  private String buildingName;
  private Long buildingNumber;
  private String bulidingAbbreviation;
  private BigDecimal latitude;
  private BigDecimal longtitude;

  @Builder
  public BuildingResponse(Long buildingId, String buildingName, Long buildingNumber, String bulidingAbbreviation, BigDecimal latitude, BigDecimal longtitude) {
    this.buildingId = buildingId;
    this.buildingName = buildingName;
    this.buildingNumber = buildingNumber;
    this.bulidingAbbreviation = bulidingAbbreviation;
    this.latitude = latitude;
    this.longtitude = longtitude;
  }
}
