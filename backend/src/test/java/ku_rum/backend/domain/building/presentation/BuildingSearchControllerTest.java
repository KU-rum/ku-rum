package ku_rum.backend.domain.building.presentation;

import ku_rum.backend.domain.building.application.BuildingSearchService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;

@SpringBootTest
class BuildingSearchControllerTest {

  @MockBean
  private BuildingSearchService buildingSearchService;

  @DisplayName("모든 건물들의 위치, 이름 등의 정보를 보여준다.")
  @Test
  @WithMockUser
  void viewAll() throws Exception {
    //given

  }

  @Test
  void viewBuildingByNumber() {
  }

  @Test
  void viewBuildingByName() {
  }

  @Test
  void viewBuildingByCategory() {
  }

  @Test
  void viewBuildingByCategoryInBuilding() {
  }
}