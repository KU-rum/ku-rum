package ku_rum.backend.domain.category.response;

import ku_rum.backend.domain.menu.response.MenuSimpleResponse;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class CategoryCafeteriaDetailResponse extends CategoryDetailResponse {
  private List<MenuSimpleResponse> menus;

  @Builder
  public CategoryCafeteriaDetailResponse(String category, Long categoryId) {
    this.category = category;
    this.categoryId = categoryId;
  }

  public void setMenus(List<MenuSimpleResponse> simplemenus){
    this.menus = simplemenus;
  }
}
