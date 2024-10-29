package ku_rum.backend.domain.category.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CategoryType {
    STUDENT_CAFETERIA("학생 식당");

    private final String text;
}
