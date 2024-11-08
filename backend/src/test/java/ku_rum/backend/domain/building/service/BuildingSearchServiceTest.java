package ku_rum.backend.domain.building.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class BuildingSearchServiceTest {

  BuildingSearchService buildingSearchService = new BuildingSearchService(null);

  @Test
  void 줄임말에서_정식명칭추출_성공() {
    // given
    String input = "경영101";

    // when
    String result = buildingSearchService.getAbbrevWithoutNumber(input);

    // then
    assertEquals("경영", result); // 한글만 추출되어야 함
  }

  @Test
  void 존재하는_줄임말_판별_성공() {
    // given
    String abbrev = "경영";

    // when
    boolean result = buildingSearchService.isValidBuildingAbbrev(abbrev);

    // then
    assertTrue(result);
  }

  @Test
  void 존재하지않는_줄임말_판별_성공() {
    // given
    String abbrev = "아이스티";

    // when
    boolean result = buildingSearchService.isValidBuildingAbbrev(abbrev);

    // then
    assertFalse(result);
  }


}