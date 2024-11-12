package ku_rum.backend.domain.category.domain;

import lombok.Getter;

@Getter
public enum CategoryDetail {

  STUDENT_CAFETERIA("학생식당"),
  KCUBE("케이큐브");

  private String categoryName;

  CategoryDetail(String categoryName) {
    this.categoryName = categoryName;
  }

  public boolean isEqual(CategoryDetail categoryDetail) {
    return this == categoryDetail;
  }

  public boolean isDetailProviding() {
    return this == STUDENT_CAFETERIA || this == KCUBE;
  }
}
