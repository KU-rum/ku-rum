package ku_rum.backend.domain.building.dto;

import ku_rum.backend.global.type.ApiResponse;
import lombok.Getter;

@Getter
public class BuildingClassResponseDto {

  private int buildingId;
  private int categoryId;
  private String buildingName;
  private int buildingNumber;
  private String bulidingAbbreviation;
  private double latitude;
  private double longtitude;

  public BuildingClassResponseDto(int buildingId, int categoryId, String buildingName, int buildingNumber, String bulidingAbbreviation, double latitude, double longtitude) {
    this.buildingId = buildingId;
    this.categoryId = categoryId;
    this.buildingName = buildingName;
    this.buildingNumber = buildingNumber;
    this.bulidingAbbreviation = bulidingAbbreviation;
    this.latitude = latitude;
    this.longtitude = longtitude;
  }
}
