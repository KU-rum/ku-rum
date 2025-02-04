package ku_rum.backend.domain.category.dto.response;

import ku_rum.backend.domain.building.domain.Building;

public record CategoryKcubeDetailResponse(
        String category,
        Long buildingId,
        Long floor
) implements CategoryDetailResponse {

  public CategoryKcubeDetailResponse(String category, Long buildingId) {
    this(category, buildingId, null);
  }

  public static CategoryKcubeDetailResponse withFloor(
          CategoryKcubeDetailResponse response,
          Building building
  ) {
    return new CategoryKcubeDetailResponse(
            response.category(),
            response.buildingId(),
            building.getFloor()
    );
  }

  @Override
  public String getCategory() {
    return this.category;
  }

  @Override
  public Long getBuildingId() {
    return this.buildingId;
  }
}