package ku_rum.backend.domain.building.service;

import jakarta.persistence.EntityManager;
import ku_rum.backend.domain.building.domain.Building;
import ku_rum.backend.domain.building.domain.BuildingCategory;
import ku_rum.backend.domain.building.dto.response.BuildingResponse;
import ku_rum.backend.domain.building.repository.BuildingRepository;
import ku_rum.backend.domain.category.domain.Category;
import ku_rum.backend.domain.category.response.CategoryCafeteriaDetailResponse;
import ku_rum.backend.domain.menu.domain.Menu;
import ku_rum.backend.global.exception.category.CategoryNotProvidingDetail;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
public class BuildingSearchServiceTest {

  @Autowired
  BuildingSearchService buildingSearchService;
  @Autowired
  EntityManager em;

  @BeforeEach
  public void 임시_데이터(){
    Category c1 = Category.builder()
            .name("학생회관")
            .build();
    Category c2 = Category.builder()
            .name("공학관")
            .build();

    em.persist(c1);em.persist(c2);

    Building b1 = Building.builder()
            .name("제1학생회관")
            .abbreviation("1학관")
            .longitude(BigDecimal.valueOf(33.333332))
            .latitude(BigDecimal.valueOf(22.222222))
            .number(11L)
            .build();
    Building b2 = Building.builder()
            .name("공학관")
            .abbreviation("2학관")
            .longitude(BigDecimal.valueOf(33.333332))
            .latitude(BigDecimal.valueOf(22.222222))
            .number(12L)
            .build();
    Building b3 = Building.builder()
            .name("제2학생회관")
            .abbreviation("2학관")
            .longitude(BigDecimal.valueOf(33.333332))
            .latitude(BigDecimal.valueOf(22.222222))
            .number(10L)
            .build();

    em.persist(b1);em.persist(b2);em.persist(b3);

    BuildingCategory bc1 = BuildingCategory.of(b1, c1);
    BuildingCategory bc3 = BuildingCategory.of(b3, c1);
    BuildingCategory bc2 = BuildingCategory.of(b2, c2);

    em.persist(bc1);em.persist(bc2);em.persist(bc3);

    Menu m1 = Menu.builder()
            .name("만두국")
            .price(1000L)
            .category(c1)
            .imageUrl("http://aaaaa")
            .build();
    Menu m2 = Menu.builder()
            .name("짜장면")
            .price(3000L)
            .category(c1)
            .imageUrl("http://bbbbb")
            .build();

    em.persist(m1);em.persist(m2);


  }

  @Test(expected = CategoryNotProvidingDetail.class)
  public void 디테일_제공안하는_카테고리입력시_실패() throws Exception {
      // given
      buildingSearchService.viewBuildingDetailByCategory("공학관", 1L);
  }

  @Test
  public void 특정카테고리_전체조회_성공() throws Exception {
      // given
    List<BuildingResponse> responses = buildingSearchService.viewBuildingByCategory("학생회관");
      // when
      // then
    Assertions.assertEquals(2, responses.size());
  }
  
  @Test
  public void 특정_학생회관_카테고리_디테일_조회_성공() throws Exception {
      // given
    CategoryCafeteriaDetailResponse response = (CategoryCafeteriaDetailResponse) buildingSearchService.viewBuildingDetailByCategory("학생회관",1L);
      // when
      // then
    Assertions.assertEquals(2,response.getMenus().size());
  }


}