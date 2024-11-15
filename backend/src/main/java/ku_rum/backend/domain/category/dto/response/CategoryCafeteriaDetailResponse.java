package ku_rum.backend.domain.category.dto.response;

import ku_rum.backend.domain.menu.response.MenuSimpleResponse;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class CategoryCafeteriaDetailResponse extends CategoryDetailResponse {
  private List<MenuSimpleResponse> menus;

  @Builder
  public CategoryCafeteriaDetailResponse(String category, Long buildingId) {
    this.category = category;
    this.buildingId = buildingId;
  }

  public void setMenus(List<MenuSimpleResponse> simplemenus){
    this.menus = simplemenus;
  }
}
