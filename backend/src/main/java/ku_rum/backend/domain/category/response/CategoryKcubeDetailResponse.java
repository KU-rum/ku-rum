package ku_rum.backend.domain.category.response;

import ku_rum.backend.domain.building.domain.Building;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CategoryKcubeDetailResponse extends  CategoryDetailResponse{
  private Long floor;

  public CategoryKcubeDetailResponse(String category, Long buildingId) {
    this.category = category;
    this.buildingId = buildingId;
  }

  public void setFloor(Building buildingBy) {
    //this.floor = buildingBy.getFloor();
  }
}
