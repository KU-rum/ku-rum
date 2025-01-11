package ku_rum.backend.domain.building.service;

import jakarta.persistence.EntityManager;
import ku_rum.backend.domain.building.application.BuildingSearchService;
import ku_rum.backend.domain.building.domain.Building;
import ku_rum.backend.domain.building.domain.BuildingCategory;
import ku_rum.backend.domain.building.dto.response.BuildingResponse;
import ku_rum.backend.domain.building.domain.repository.BuildingQueryRepository;
import ku_rum.backend.domain.building.domain.repository.BuildingRepository;
import ku_rum.backend.domain.category.domain.Category;
import ku_rum.backend.domain.menu.domain.Menu;
import ku_rum.backend.global.exception.building.BuildingNotFoundException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@Transactional
public class BuildingInfoServiceTest {

  @Autowired
  BuildingSearchService buildingSearchService;
  @Autowired
  EntityManager em;
  @Autowired
  private BuildingRepository buildingRepository;
  @Autowired
  private BuildingQueryRepository buildingQueryRepository;

  private Building b1, b2, b3, b4;
  private BuildingCategory bc1, bc2, bc3, bc4;
  private Menu m1, m2;
  private Category c1,c2,c3;

  @BeforeEach
  public void 임시_데이터() {

    c1 = Category.of("학생식당");
    c2 = Category.of("공학관");
    c3 = Category.of("케이큐브");

    em.persist(c1);
    em.persist(c2);
    em.persist(c3);

    b1 = Building.of("제1학생회관", 1L,"1학관", 1L, BigDecimal.valueOf(33.333332), BigDecimal.valueOf(22.222222));
    b2 = Building.of("경영관", 2L,"경영", 12L, BigDecimal.valueOf(44.733332), BigDecimal.valueOf(22.222222));
    b3 = Building.of("제2학생회관",3L, "2학11", 10L, BigDecimal.valueOf(33.333332), BigDecimal.valueOf(22.222222));
    b4 = Building.of("도서관케이큐브", 4L,"케큡", 13L, BigDecimal.valueOf(33.333332), BigDecimal.valueOf(22.222222));

    em.persist(b1);
    em.persist(b2);
    em.persist(b3);
    em.persist(b4);

    bc1 = BuildingCategory.of(b1, c1);
    bc3 = BuildingCategory.of(b3, c1);
    bc2 = BuildingCategory.of(b2, c2);
    bc4 = BuildingCategory.of(b4, c3);

    em.persist(bc1);
    em.persist(bc2);
    em.persist(bc3);
    em.persist(bc4);

    m1 = Menu.of("만두국", 1000L, "http://aaaaa",c1);
    m2 = Menu.of("짜장면", 3000L, "http://bbbbb",c1);

    em.persist(m1);
    em.persist(m2);
    em.flush();
    em.clear();
  }

  @Test
  public void 등록된_건물정보_전체_조회_성공() throws Exception {
    // given
    List<BuildingResponse>  buildingResponses = buildingSearchService.findAllBuildings();

    //then
    Assertions.assertEquals(4, buildingResponses.size());
  }

  @Test
  public void 등록된_건물정보_이름으로_조회_성공() throws Exception {
    System.out.println(b2.getNumber());
    // given
    BuildingResponse buildingResponse = buildingSearchService.viewBuildingByName("경영102");

    // then
    Assertions.assertEquals(2L, buildingResponse.getBuildingNumber());
  }

  @Test
  public void 등록된_건물정보_건물번호로_조회_성공() throws Exception {
    // given
    Long number = 3L;
    BuildingResponse buildingResponse = buildingSearchService.viewBuildingByNumber(number);

    // then
    Assertions.assertEquals("제2학생회관", buildingResponse.getBuildingName());
  }

  @Test
  public void 등록된_건물정보_건물번호로_조회_실패() throws Exception {
    // given
    Long number = 202L;
    Assertions.assertThrows(BuildingNotFoundException.class, () -> {
      buildingSearchService.viewBuildingByNumber(number);
    });
  }
}