package ku_rum.backend.domain.building.service;

import jakarta.persistence.EntityManager;
import ku_rum.backend.domain.building.application.BuildingSearchService;
import ku_rum.backend.domain.building.domain.Building;
import ku_rum.backend.domain.building.domain.BuildingCategory;
import ku_rum.backend.domain.building.domain.repository.BuildingQueryRepository;
import ku_rum.backend.domain.building.domain.repository.BuildingRepository;
import ku_rum.backend.domain.building.dto.response.BuildingResponse;
import ku_rum.backend.domain.category.domain.Category;
import ku_rum.backend.domain.category.dto.response.CategoryDetailFloorAndMenusProviding;
import ku_rum.backend.domain.menu.domain.Menu;
import ku_rum.backend.global.exception.category.CategoryNotProvidingDetail;
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
public class BuildingCategoryInfoServiceTest {

  @Autowired
  BuildingSearchService buildingSearchService;
  @Autowired
  EntityManager em;
  @Autowired
  private BuildingRepository buildingRepository;
  @Autowired
  private BuildingQueryRepository buildingQueryRepository;

  Category c1, c2, c3, c4;
  Building b1, b2, b3, b4,b5;
  Menu m1, m2, m3;

  @BeforeEach
  public void 임시_데이터() {
    c1 = Category.of("학생식당");
    c2 = Category.of("공학관");
    c3 = Category.of("케이큐브");
    c4 = Category.of("레스티오");

    em.persist(c1);
    em.persist(c2);
    em.persist(c3);
    em.persist(c4);

    b1 = Building.of("제1학생회관",1L,"1학관",BigDecimal.valueOf(33.333332), BigDecimal.valueOf(22.222222));
    b2 = Building.of("공학관",2L,"2학관",12L,BigDecimal.valueOf(33.333332), BigDecimal.valueOf(22.222222));
    b3 = Building.of("제2학생관",10L,"2학관11",2L,BigDecimal.valueOf(33.333332), BigDecimal.valueOf(22.222222));
    b4 = Building.of("도서관케이큐브",4L,"케큡",13L,BigDecimal.valueOf(33.333332),BigDecimal.valueOf(33.333332));
    b5 = Building.of("경영관",14L,"경영",1L,BigDecimal.valueOf(33.333332),BigDecimal.valueOf(33.335532));

    em.persist(b1);
    em.persist(b2);
    em.persist(b3);
    em.persist(b4);
    em.persist(b5);

    BuildingCategory bc1 = BuildingCategory.of(b1, c1);
    BuildingCategory bc3 = BuildingCategory.of(b3, c1);
    BuildingCategory bc2 = BuildingCategory.of(b2, c2);
    BuildingCategory bc4 = BuildingCategory.of(b4, c3);
    BuildingCategory bc5 = BuildingCategory.of(b5, c4);

    em.persist(bc1);
    em.persist(bc2);
    em.persist(bc3);
    em.persist(bc4);
    em.persist(bc5);


    m1 = Menu.of("만두국", 1000L, "http://aaaaa",c1);
    m2 = Menu.of("짜장면", 3000L, "http://bbbbb",c1);
    m3 = Menu.of("아이스티", 1000L, "http://ddddc",c4);

    em.persist(m1);
    em.persist(m2);
    em.persist(m3);
    em.flush();

  }

  @Test
  public void 디테일_제공안하는_카테고리입력시_실패() throws Exception {
    Assertions.assertThrows(CategoryNotProvidingDetail.class, () -> {
      buildingSearchService.viewBuildingDetailByCategory("공학관", 2L);
    });
  }

  @Test
  public void 특정카테고리_전체조회_성공() throws Exception {
    // given
    List<BuildingResponse> responses = buildingSearchService.viewBuildingByCategory("학생식당");
    // when
    for (BuildingResponse e : responses) {
      System.out.println("Response : " + e.getBulidingAbbreviation());
    }
    // then
    Assertions.assertEquals(2, responses.size());
  }

  @Test
  public void 특정_학생식당_카테고리_디테일_조회_성공() throws Exception {
    // given
    CategoryDetailFloorAndMenusProviding response = (CategoryDetailFloorAndMenusProviding) buildingSearchService.viewBuildingDetailByCategory("학생식당", buildingQueryRepository.findBuildingByNumber_test(1L));
    // when
    // then
    Assertions.assertEquals(2, response.getMenus().size());
  }

  @Test
  public void 특정_카페_카테고리_디테일_조회_성공() throws Exception {
    // given
    CategoryDetailFloorAndMenusProviding response = (CategoryDetailFloorAndMenusProviding) buildingSearchService.viewBuildingDetailByCategory("레스티오", buildingQueryRepository.findBuildingByNumber_test(14L));
    // when
    // then
    Assertions.assertEquals(1, response.getMenus().size());
    Assertions.assertEquals(1L, response.getFloor());
  }

  @Test
  public void 특정_케이큐브_카테고리_디테일_조회_성공() throws Exception {
    // given
    CategoryDetailFloorAndMenusProviding response = (CategoryDetailFloorAndMenusProviding) buildingSearchService.viewBuildingDetailByCategory("케이큐브", buildingQueryRepository.findBuildingByNumber_test(14L));
    // when
    // then
    Assertions.assertEquals(1L, response.getFloor());
    Assertions.assertEquals(1, response.getMenus().size());
  }

}