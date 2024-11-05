package ku_rum.backend.domain.building.controller;

import ku_rum.backend.common.BaseControllerTest;
import ku_rum.backend.domain.building.dto.response.BuildingResponseDto;
import ku_rum.backend.domain.building.repository.BuildingClassRepository;
import ku_rum.backend.domain.building.service.BuildingSearchService;
import ku_rum.backend.global.exception.building.BuildingsNotFoundException;
import ku_rum.backend.global.response.status.BaseExceptionResponseStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BuildingSearchController.class)
class BuildingSearchControllerTest extends BaseControllerTest {

  @MockBean
  private BuildingSearchService buildingSearchService;
  @MockBean
  private BuildingClassRepository buildingClassRepository;

//  private final List<BuildingResponseDto> mockBuildings = Arrays.asList(
//          new BuildingResponseDto(1, 1, "공학관", 1, "공", 37.83838, 127.04567),
//          new BuildingResponseDto(2, 1, "상허기념도서관", 2, "도서관", 27.83838, 126.04567)
//  );


//  @Test
//  @DisplayName("전체 건물들의 핀포인트 정상 조회 - 건물 존재o")
//  public void 전체건물들의_핀포인트_정상조회_건물존재O() throws Exception { //통과
//    //given
//    when(buildingSearchService.findAllBuildings()).thenReturn(mockBuildings);
//
//    //when & then
//    ResultActions result = mockMvc.perform(get("/v1/building/view/all"))
//            .andDo(print())
//            .andExpect(status().isOk())
//            .andExpect(jsonPath("$.status").value("OK"))
//            .andExpect(jsonPath("$.message").value(BaseExceptionResponseStatus.SUCCESS.getMessage()))
//            .andExpect(jsonPath("$.data.length()").value(2));
//  }
//
//  @Test
//  @DisplayName("전체 건물들의 핀포인트 정상 조회 - 건물 존재 x")
//  public void 전체건물들의_핀포인트_정상_조회_건물존재X() throws Exception {
//    //given
//    when(buildingSearchService.findAllBuildings()).thenReturn(null);
//
//    ResultActions result = mockMvc.perform(get("/v1/building/view/all"))
//            .andDo(print())
//            .andExpect(status().isNoContent())
//            .andExpect(jsonPath("$.status").value("NO_CONTENT"))
//            .andExpect(jsonPath("$.message").value(BaseExceptionResponseStatus.NO_BUILDING_REGISTERED.getMessage()));
//  }
//
//  @Test
//  @DisplayName("전체 건물들의 핀포인트 조회 - 서버 내부 오류")
//  public void 전체건물들의_핀포인트_조회_서버내부오류() throws Exception {
//
//    when(buildingSearchService.findAllBuildings())
//            .thenThrow(new RuntimeException("서버 내부 오류"));
//
//    //when & then
//    mockMvc.perform(get("/v1/building/view/all"))
//            .andDo(print())
//            .andExpect(status().isInternalServerError())
//            .andExpect(jsonPath("$.message").value(
//                    BaseExceptionResponseStatus.INTERNAL_SERVER_ERROR.getMessage()));
//  }

//  @Test
//  @DisplayName("특정 건물번호로 건물 조회 - 건물 존재")
//  public void 특정건물번호로건물조회_건물존재() throws Exception {
//    // given
//    int buildingNumber = 1;
//    //BuildingResponseDto mockBuilding = new BuildingResponseDto(1, 1, "공학관A", 1, "공A", 37.83838, 127.04567);
//    when(buildingSearchService.viewBuildingByNumber(buildingNumber)).thenReturn(mockBuilding);
//
//    // when & then
//    ResultActions result = mockMvc.perform(get("/v1/building/view/search/number")
//                    .param("number", String.valueOf(buildingNumber)))
//            .andDo(print())
//            .andExpect(status().isOk())
//            .andExpect(jsonPath("$.status").value("OK"))
//            .andExpect(jsonPath("$.message").value(BaseExceptionResponseStatus.SUCCESS.getMessage()))
//            .andExpect(jsonPath("$.data.buildingId").value(mockBuilding.getBuildingId())) // 데이터의 ID 확인
//            .andExpect(jsonPath("$.data.buildingName").value(mockBuilding.getBuildingName())); // 데이터의 이름 확인
//  }
//
//  @Test
//  @DisplayName("특정 건물번호로 건물 조회 - 건물 존재하지 않음")
//  public void 특정건물번호로건물조회_건물존재하지않음() throws Exception {
//    // given
//    int buildingNumber = 2;
//    when(buildingSearchService.viewBuildingByNumber(buildingNumber)).thenThrow(new BuildingsNotFoundException(
//            BaseExceptionResponseStatus.BUILDING_DATA_NOT_FOUND_BY_NUMBER.getMessage(),
//            BaseExceptionResponseStatus.BUILDING_DATA_NOT_FOUND_BY_NUMBER
//    ));
//
//    // when & then
//    mockMvc.perform(get("/v1/building/view/search/number")
//                    .param("number", String.valueOf(buildingNumber)))
//            .andDo(print())
//            .andExpect(status().isNotFound())
//            .andExpect(jsonPath("$.status").value("NOT_FOUND"))
//            .andExpect(jsonPath("$.message").value(BaseExceptionResponseStatus.BUILDING_DATA_NOT_FOUND_BY_NUMBER.getMessage()));
//  }
//  @Test
//  @DisplayName("특정 건물번호로 건물 조회 - 서버 내부 오류")
//  public void 특정건물번호로건물조회_서버내부오류() throws Exception {
//    // given
//    int buildingNumber = 3;
//    when(buildingSearchService.viewBuildingByNumber(buildingNumber))
//            .thenThrow(new RuntimeException("Unexpected error occurred"));
//
//    // when & then
//    mockMvc.perform(get("/v1/building/view/search/number")
//                    .param("number", String.valueOf(buildingNumber)))
//            .andDo(print())
//            .andExpect(status().isInternalServerError())
//            .andExpect(jsonPath("$.message").value(BaseExceptionResponseStatus.INTERNAL_SERVER_ERROR.getMessage()));
//  }
//
//  @Test
//  @DisplayName("건물 정식명칭으로 건물 조회 - 건물 존재")
//  public void 건물정식명칭으로건물조회_건물존재() throws Exception {
//    // given
//    String buildingName = "공학관";
//    BuildingResponseDto mockBuilding = new BuildingResponseDto(1, 1, buildingName, 1, "공A", 37.83838, 127.04567);
//    when(buildingSearchService.findBuilding(buildingName)).thenReturn(mockBuilding);
//
//    // when & then
//    ResultActions result = mockMvc.perform(get("/v1/building/view/search/name")
//                    .param("name", buildingName))
//            .andDo(print())
//            .andExpect(status().isOk())
//            .andExpect(jsonPath("$.status").value("OK"))
//            .andExpect(jsonPath("$.message").value(BaseExceptionResponseStatus.SUCCESS.getMessage()))
//            .andExpect(jsonPath("$.data.buildingId").value(mockBuilding.getBuildingId()))
//            .andExpect(jsonPath("$.data.buildingName").value(mockBuilding.getBuildingName()));
//  }
//
//  @Test
//  @DisplayName("건물 정식명칭으로 건물 조회 - 건물 존재하지 않음")
//  public void 건물정식명칭으로건물조회_건물존재하지않음() throws Exception {
//    // given
//    String buildingName = "존재하지않는건물";
//    when(buildingSearchService.findBuilding(buildingName)).thenThrow(new BuildingsNotFoundException(
//            BaseExceptionResponseStatus.BUILDING_DATA_NOT_FOUND_BY_NAME.getMessage(),
//            BaseExceptionResponseStatus.BUILDING_DATA_NOT_FOUND_BY_NAME
//    ));
//
//    // when & then
//    mockMvc.perform(get("/v1/building/view/search/name")
//                    .param("name", buildingName))
//            .andDo(print())
//            .andExpect(status().isNotFound())
//            .andExpect(jsonPath("$.status").value("NOT_FOUND"))
//            .andExpect(jsonPath("$.message").value(BaseExceptionResponseStatus.BUILDING_DATA_NOT_FOUND_BY_NAME.getMessage()));
//  }
//

}