package ku_rum.backend.domain.building.service;

import jakarta.persistence.EntityManager;
import ku_rum.backend.domain.building.application.BuildingSearchService;
import ku_rum.backend.domain.building.domain.Building;
import ku_rum.backend.domain.building.domain.BuildingCategory;
import ku_rum.backend.domain.building.dto.response.BuildingResponse;
import ku_rum.backend.domain.building.domain.repository.BuildingQueryRepository;
import ku_rum.backend.domain.building.domain.repository.BuildingRepository;
import ku_rum.backend.domain.category.domain.Category;
import ku_rum.backend.domain.category.response.CategoryCafeteriaDetailResponse;
import ku_rum.backend.domain.category.response.CategoryKcubeDetailResponse;
import ku_rum.backend.domain.menu.domain.Menu;
import ku_rum.backend.global.exception.category.CategoryNotProvidingDetail;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
@Transactional
public class BuildingCategoryRelatedServiceTest {

  @Autowired
  BuildingSearchService buildingSearchService;
  @Autowired
  EntityManager em;
  @Autowired
  private BuildingRepository buildingRepository;
  @Autowired
  private BuildingQueryRepository buildingQueryRepository;

  @BeforeEach
  public void 임시_데이터(){
    Category c1 = Category.builder()
            .name("학생식당")
            .build();
    Category c2 = Category.builder()
            .name("공학관")
            .build();
    Category c3 = Category.builder()
            .name("케이큐브")
            .build();

    em.persist(c1);em.persist(c2);em.persist(c3);

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
            .abbreviation("2학11")
            .longitude(BigDecimal.valueOf(33.333332))
            .latitude(BigDecimal.valueOf(22.222222))
            .number(10L)
            .build();
    Building b4 = Building.builder()
            .name("도서관케이큐브")
            .abbreviation("케큡")
            .floor(4L)
            .longitude(BigDecimal.valueOf(33.333332))
            .latitude(BigDecimal.valueOf(22.222222))
            .number(13L)
            .build();

    em.persist(b1);em.persist(b2);em.persist(b3);em.persist(b4);

    BuildingCategory bc1 = BuildingCategory.of(b1, c1);
    BuildingCategory bc3 = BuildingCategory.of(b3, c1);
    BuildingCategory bc2 = BuildingCategory.of(b2, c2);
    BuildingCategory bc4 = BuildingCategory.of(b4, c3);


    em.persist(bc1);em.persist(bc2);em.persist(bc3);em.persist(bc4);

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
    em.flush();

  }

  @AfterEach
  public void cleanup() {
    em.createQuery("DELETE FROM Menu").executeUpdate();
    em.createQuery("DELETE FROM BuildingCategory").executeUpdate();
    em.createQuery("DELETE FROM Building").executeUpdate();
    em.createQuery("DELETE FROM Category").executeUpdate();
    em.flush();
    em.clear();
  }

  @Test
  public void 디테일_제공안하는_카테고리입력시_실패() throws Exception {
      Assertions.assertThrows(CategoryNotProvidingDetail.class, () -> {
        buildingSearchService.viewBuildingDetailByCategory("공학관", 1L);
      });
  }

  @Test
  public void 특정카테고리_전체조회_성공() throws Exception {
    // given
    List<BuildingResponse> responses = buildingSearchService.viewBuildingByCategory("학생식당");
    // when
    for (BuildingResponse e : responses){
      System.out.println("Response : " + e.getBulidingAbbreviation());
    }
    // then
    Assertions.assertEquals(2, responses.size());
  }
  
  @Test
  public void 특정_학생식당_카테고리_디테일_조회_성공() throws Exception {
    // given
    CategoryCafeteriaDetailResponse response = (CategoryCafeteriaDetailResponse) buildingSearchService.viewBuildingDetailByCategory("학생식당",buildingQueryRepository.findBuildingByNumber_test(11L));
    // when
    // then
    Assertions.assertEquals(2,response.getMenus().size());
  }

  @Test
  public void 특정_케이큐브_카테고리_디테일_조회_성공() throws Exception {
    // given
    CategoryKcubeDetailResponse response = (CategoryKcubeDetailResponse) buildingSearchService.viewBuildingDetailByCategory("케이큐브",buildingQueryRepository.findBuildingByNumber_test(13L));
    // when
    // then
    Assertions.assertEquals(4,response.getFloor());
  }


}