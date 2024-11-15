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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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

  @BeforeEach
  public void 임시_데이터() {

    Category c1 = Category.builder()
            .name("학생식당")
            .build();
    Category c2 = Category.builder()
            .name("공학관")
            .build();
    Category c3 = Category.builder()
            .name("케이큐브")
            .build();

    em.persist(c1);
    em.persist(c2);
    em.persist(c3);

    b1 = Building.builder()
            .name("제1학생회관")
            .abbreviation("1학관")
            .longitude(BigDecimal.valueOf(33.333332))
            .latitude(BigDecimal.valueOf(22.222222))
            .number(11L)
            .build();
    b2 = Building.builder()
            .name("경영관")
            .abbreviation("경영")
            .longitude(BigDecimal.valueOf(33.333332))
            .latitude(BigDecimal.valueOf(22.222222))
            .number(12L)
            .build();
    b3 = Building.builder()
            .name("제2학생회관")
            .abbreviation("2학11")
            .longitude(BigDecimal.valueOf(33.333332))
            .latitude(BigDecimal.valueOf(22.222222))
            .number(10L)
            .build();
    b4 = Building.builder()
            .name("도서관케이큐브")
            .abbreviation("케큡")
            .floor(4L)
            .longitude(BigDecimal.valueOf(33.333332))
            .latitude(BigDecimal.valueOf(22.222222))
            .number(13L)
            .build();

    em.persist(b1);
    em.persist(b2);
    em.persist(b3);
    em.persist(b4);

    BuildingCategory bc1 = BuildingCategory.of(b1, c1);
    BuildingCategory bc3 = BuildingCategory.of(b3, c1);
    BuildingCategory bc2 = BuildingCategory.of(b2, c2);
    BuildingCategory bc4 = BuildingCategory.of(b4, c3);

    em.persist(bc1);
    em.persist(bc2);
    em.persist(bc3);
    em.persist(bc4);

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

    em.persist(m1);
    em.persist(m2);
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
  public void 등록된_건물정보_전체_조회_성공() throws Exception {
    // given
    List<BuildingResponse>  buildingResponses = buildingSearchService.findAllBuildings();

    //then
    Assertions.assertEquals(4, buildingResponses.size());
  }

  @Test
  public void 등록된_건물정보_이름으로_조회_성공() throws Exception {
    // given
    BuildingResponse buildingResponse = buildingSearchService.viewBuildingByName("경영관");

    // then
    Assertions.assertEquals(12L, buildingResponse.getBuildingNumber());
  }

  @Test
  public void 등록된_건물정보_건물번호로_조회_성공() throws Exception {
    // given
    Long x = 10L;
    int number = x.intValue();
    Optional<BuildingResponse> buildingResponse = buildingSearchService.viewBuildingByNumber(number);

    // then
    Assertions.assertEquals("제2학생회관", buildingResponse.get().getBuildingName());
  }

  @Test
  public void 등록된_건물정보_건물번호로_조회_실패() throws Exception {
    // given
    Long x = 20L;
    int number = x.intValue();
    Assertions.assertThrows(BuildingNotFoundException.class, () -> {
      buildingSearchService.viewBuildingByNumber(number);
    });
  }
}