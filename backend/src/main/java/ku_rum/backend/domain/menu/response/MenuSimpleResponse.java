package ku_rum.backend.domain.menu.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MenuSimpleResponse {
  private String  name;
  private Long price;
  private String imageUrl;

  @Builder
  public MenuSimpleResponse(String name, Long price, String imageUrl) {
    this.name = name;
    this.price = price;
    this.imageUrl = imageUrl;
  }
}
