package ku_rum.backend.domain.building.controller;

import ku_rum.backend.domain.building.dto.response.BuildingResponseDto;
import ku_rum.backend.domain.building.dto.response.BuildingsResponse;
import ku_rum.backend.domain.building.service.BuildingSearchService;
import ku_rum.backend.global.exception.building.BuildingsNotFoundException;
import ku_rum.backend.global.response.BaseResponse;
import ku_rum.backend.global.response.status.BaseExceptionResponseStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/buildings/view")
public class BuildingSearchController {

  private final BuildingSearchService buildingSearchService;

  /**
   * 전체 강의실의 핀포인트 조회
   *
   * @return
   */
  @GetMapping
  public BaseResponse<List<BuildingResponseDto>> viewAll() {
    List<BuildingResponseDto> results = buildingSearchService.findAllBuildings();
    return BaseResponse.of(BaseExceptionResponseStatus.SUCCESS.getStatus(),results);
  }

  /**
   * 특정 강의실의 핀포인트 조회 (건물번호로)
   *
   * @param number
   * @return
   */
  @GetMapping("/search/{number}")
  public BaseResponse<BuildingResponseDto> viewBuildingByNumber(@RequestParam("number") int number) {
    BuildingResponseDto result = buildingSearchService.viewBuildingByNumber(number);
    return BaseResponse.of(BaseExceptionResponseStatus.SUCCESS.getStatus(), result);
  }

  /**
   * 특정 강의실의 핀포인트 조회 (건물정식명칭으로, 건물 줄임말로)
   *
   * @param name
   * @return
   */
  @GetMapping("/search/{name}")
  public BaseResponse<BuildingResponseDto> viewBuildingByName(@RequestParam("name") String name){
    BuildingResponseDto result = buildingSearchService.viewBuildingByName(name);
    return BaseResponse.of(BaseExceptionResponseStatus.SUCCESS.getStatus(), result);
  }
}