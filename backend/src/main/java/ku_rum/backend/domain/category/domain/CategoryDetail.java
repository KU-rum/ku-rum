package ku_rum.backend.domain.category.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CategoryDetail {

  STUDENT_CAFETERIA("학생식당"),
  KCUBE("케이큐브"),
  CU("씨유편의점"),
  RESTIO("레스티오"),
  CAFE_1894("1894카페");

  private final String categoryName;

  public boolean isEqual(CategoryDetail categoryDetail) {
    return this == categoryDetail;
  }

  public boolean isDetailProviding() {
    return this == STUDENT_CAFETERIA || this == KCUBE || this == CU || this == RESTIO || this == CAFE_1894;
  }
}
