package ku_rum.backend.domain.category.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static ku_rum.backend.domain.category.domain.CategoryType.*;

class CategoryTest {

    @DisplayName("카테고리 이름을 받으면 카테고리를 생성한다.")
    @Test
    void saveCategory() {
        //given
        String categoryName = STUDENT_CAFETERIA.getText();
        //when
        Category category = Category.of(categoryName);
        //then
        Assertions.assertThat(category.getName()).isEqualTo(categoryName);
    }

}