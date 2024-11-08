package ku_rum.backend.domain.building.controller;

import ku_rum.backend.domain.building.dto.response.BuildingResponse;
import ku_rum.backend.domain.building.service.BuildingSearchService;
import ku_rum.backend.domain.category.response.CategoryDetailResponse;
import ku_rum.backend.global.response.BaseResponse;
import ku_rum.backend.global.response.status.BaseExceptionResponseStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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
  public BaseResponse<List<BuildingResponse>> viewAll() {
    List<BuildingResponse> results = buildingSearchService.findAllBuildings();
    return BaseResponse.of(BaseExceptionResponseStatus.SUCCESS.getStatus(),results);
  }

  /**
   * 특정 강의실의 핀포인트 조회 (건물번호로)
   *
   * @param number
   * @return
   */
  @GetMapping("/searchNumber")
  public BaseResponse<BuildingResponse> viewBuildingByNumber(@RequestParam("number") int number) {
    BuildingResponse result = buildingSearchService.viewBuildingByNumber(number);
    return BaseResponse.of(BaseExceptionResponseStatus.SUCCESS.getStatus(), result);
  }

  /**
   * 특정 강의실의 핀포인트 조회 (건물정식명칭으로, 건물 줄임말로)
   *
   * @param name
   * @return
   */
  @GetMapping("/searchName")
  public BaseResponse<BuildingResponse> viewBuildingByName(@RequestParam("name") String name){
    BuildingResponse result = buildingSearchService.viewBuildingByName(name.trim());
    return BaseResponse.of(BaseExceptionResponseStatus.SUCCESS.getStatus(), result);
  }

  /**
   * 특정 카테고리의 핀포인트들 조회 (카테고리명으로)
   *
   * @param category
   * @return
   */
  @GetMapping("/{category}")
  public BaseResponse<Object> viewBuildingByCategory(@PathVariable("category") String category){
    List<BuildingResponse> categoryList = buildingSearchService.viewBuildingByCategory(category.trim());
    return BaseResponse.of(BaseExceptionResponseStatus.SUCCESS.getStatus(), categoryList);
  }

  /**
   * 카테고리에 해당하는 특정 핀포인트 디테일 정보 확인 (학생식당, K-CUBE/K-HUB)
   * 
   * @param category
   * @param buildingId
   * @return
   */
  @GetMapping("/{buildingId}/{category}")
  public BaseResponse<Object> viewBuildingByCategory(
          @PathVariable("category") String category,
          @PathVariable("buildingId") Long buildingId
          ){
    CategoryDetailResponse categoryDetailResponse = buildingSearchService.viewBuildingDetailByCategory(category,buildingId);
    return BaseResponse.of(BaseExceptionResponseStatus.SUCCESS.getStatus(), categoryDetailResponse);
  }
}