package ku_rum.backend.domain.menu.domain;

import ku_rum.backend.domain.category.domain.Category;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static ku_rum.backend.domain.category.domain.CategoryType.STUDENT_CAFETERIA;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MenuTest {

    @DisplayName("메뉴 생성 시 카테고리 정보를 넣어준다.")
    @Test
    void registeredMenuWithCategory() {
        //given
        String categoryName = STUDENT_CAFETERIA.getText();
        Category category = Category.of(categoryName);

        //when
        Menu menu = Menu.of("짜장면", 15000L, "naver.com/jjajang.url", category);

        //then
        assertThat(menu.getName()).isEqualTo("짜장면");
        assertThat(menu.getPrice()).isEqualTo(15000);
        assertThat(menu.getImageUrl()).isEqualTo("naver.com/jjajang.url");
        assertThat(menu.getCategory()).isEqualTo(category);
    }
}