package ku_rum.backend.domain.department.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DepartmentType {
  컴퓨터공학부("Computer Science Engineering");
  //추후 더 추가 예정

  private final String text;
}