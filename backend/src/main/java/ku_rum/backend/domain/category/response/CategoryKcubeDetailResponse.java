package ku_rum.backend.domain.category.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CategoryKcubeDetailResponse extends  CategoryDetailResponse{
  private String floor;

  public CategoryKcubeDetailResponse(String category, Long categoryId) {
    this.category = category;
    this.categoryId = categoryId;
  }
}
