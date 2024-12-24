package ku_rum.backend.domain.building.domain;

import ku_rum.backend.domain.category.domain.Category;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static ku_rum.backend.domain.category.domain.CategoryType.STUDENT_CAFETERIA;
import static org.assertj.core.api.Assertions.assertThat;

class BuildingCategoryTest {

    @DisplayName("빌딩 카테고리 생성시, 건물과 카테고리를 저장한다.")
    @Test
    void registeredBulidingCategory() {
        //given
        Building building = createBuilding();
        Category category = createCategory();

        //when
        BuildingCategory buildingCategory = BuildingCategory.of(building, category);

        //then
        assertThat(buildingCategory.getBuilding()).isEqualTo(building);
        assertThat(buildingCategory.getCategory()).isEqualTo(category);
    }

    private Category createCategory() {
        String categoryName = STUDENT_CAFETERIA.getText();
        return Category.of(categoryName);
    }

    private Building createBuilding() {
        BigDecimal latitude = BigDecimal.valueOf(64.3423423);
        BigDecimal longitude = BigDecimal.valueOf(342.2343434);
        return Building.of("신공학관", "신공", latitude, longitude);
    }

}