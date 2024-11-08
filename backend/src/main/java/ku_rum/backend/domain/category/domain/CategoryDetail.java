package ku_rum.backend.domain.category.domain;

import lombok.Getter;

@Getter
public enum CategoryDetail {

  학생식당("학생식당"),
  KCUBE("KCUBE"),
  케이큐브("케이큐브"),
  khub("khub"),
  kcube("kcube");

  private String categoryName;

  CategoryDetail(String categoryName) {
    this.categoryName = categoryName;
  }
}
