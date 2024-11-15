package ku_rum.backend.domain.category.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import ku_rum.backend.domain.menu.response.MenuSimpleResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryDetailFloorAndMenusProviding extends CategoryDetailResponse {
  private Long floor;
  private List<MenuSimpleResponse> menus;

  public void adding(Long floor, Optional<List<MenuSimpleResponse>> menusOpt) {
    // floor가 0보다 큰 경우에만 설정한다.
    if (floor != null && floor > 0) {
      this.floor = floor;
    }
    // menus가 존재하고 비어있지 않은 경우에만 설정한다.
    menusOpt.ifPresent(menuList -> {
      if (!menuList.isEmpty()) {
        this.menus = menuList;
      }
    });
  }
}