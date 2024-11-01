package ku_rum.backend.domain.building.controller;

import ku_rum.backend.common.BaseControllerTest;
import ku_rum.backend.domain.building.dto.BuildingResponseDto;
import ku_rum.backend.domain.building.repository.BuildingClassRepository;
import ku_rum.backend.domain.building.service.BuildingClassSearchService;
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
  private BuildingClassSearchService buildingClassSearchService;
  @MockBean
  private BuildingClassRepository buildingClassRepository;
  

  @Test
  @DisplayName("전체 건물들의 핀포인트 정상 조회-건물 존재o")
  public void getAllBuildings_success() throws Exception {
    //given
    List<BuildingResponseDto> mockResponse = Arrays.asList(
            new BuildingResponseDto(
                    1,
                    1,
                    "공학관A",
                    1,
                    "공A",
                    37.83838,
                    127.04567
            ),
            new BuildingResponseDto(
                    2,
                    1,
                    "상허기념도서관",
                    2,
                    "도서관",
                    27.83838,
                    126.04567
            )
    );
    //when
    when(buildingClassSearchService.findAllBuildings()).thenReturn(mockResponse);

    ResultActions result = mockMvc.perform(get("/v1/building/class/view/all"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value("OK"))
            .andExpect(jsonPath("$.message").value(BaseExceptionResponseStatus.SUCCESS.getMessage()))
            .andExpect(jsonPath("$.data.length()").value(2));
  }

  @Test
  @DisplayName("전체 건물들의 핀포인트 정상 조회-건물 존재x")
  public void getAllBuildings_fail() throws Exception {
    //given

    //when
    when(buildingClassSearchService.findAllBuildings()).thenReturn(null);

    ResultActions result = mockMvc.perform(get("/v1/building/class/view/all"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value("OK"))
            .andExpect(jsonPath("$.message").value(BaseExceptionResponseStatus.NO_BUILDING_REGISTERED.getMessage()));
  }
  
//  @Test
//  @DisplayName("전체 건물들의 핀포인트 조회 - 건물 못 찾음")
//  public void getAllBuildings_serverError() throws Exception {
//    //given
//    List<BuildingClassResponseDto> mockResponse = Arrays.asList(
//            new BuildingClassResponseDto(
//                    1,
//                    1,
//                    "공학관A",
//                    1,
//                    "공A",
//                    37.83838,
//                    127.04567
//            ),
//            new BuildingClassResponseDto(
//                    2,
//                    1,
//                    "상허기념도서관",
//                    2,
//                    "도서관",
//                    27.83838,
//                    126.04567
//            )
//    );
//    //when
//    when(buildingClassSearchService.findAllBuildings())
//            .thenThrow(new BuildingsNotFoundException(
//                    BaseExceptionResponseStatus.Building_NOT_FOUND,
//                    BaseExceptionResponseStatus.Building_NOT_FOUND.getMessage()
//                    )
//            );
//
//    ResultActions result = mockMvc.perform(get("/v1/building/class/view/all"))
//            .andDo(print())
//            .andExpect(status().isNotFound())
//            .andExpect(jsonPath("$.status").value("NOT_FOUND"))
//            .andExpect(jsonPath("$.message").value(
//                    BaseExceptionResponseStatus.Building_NOT_FOUND.getMessage()));
//  }

}