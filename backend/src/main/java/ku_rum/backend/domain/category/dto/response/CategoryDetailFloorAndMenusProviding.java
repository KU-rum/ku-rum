package ku_rum.backend.domain.category.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import ku_rum.backend.domain.menu.response.MenuSimpleResponse;
import java.util.List;
import java.util.Optional;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CategoryDetailFloorAndMenusProviding(
        Long floor,
        List<MenuSimpleResponse> menus
) implements CategoryDetailResponse {

  public CategoryDetailFloorAndMenusProviding {
    if (floor != null && floor <= 0) {
      throw new IllegalArgumentException("Floor must be greater than 0");
    }
  }

  public static CategoryDetailFloorAndMenusProviding of(
          Long floor,
          Optional<List<MenuSimpleResponse>> menusOpt
  ) {
    List<MenuSimpleResponse> finalMenus = menusOpt
            .filter(menuList -> !menuList.isEmpty())
            .orElse(null);

    return new CategoryDetailFloorAndMenusProviding(floor, finalMenus);
  }

  @Override
  public String getCategory() {
    return ;
  }

  @Override
  public Long getBuildingId() {
    return 0;
  }
}