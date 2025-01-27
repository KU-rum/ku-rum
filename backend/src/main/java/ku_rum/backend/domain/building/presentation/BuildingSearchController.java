package ku_rum.backend.domain.building.presentation;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import ku_rum.backend.domain.building.dto.response.BuildingResponse;
import ku_rum.backend.domain.building.application.BuildingSearchService;
import ku_rum.backend.domain.category.dto.response.CategoryDetailResponse;
import ku_rum.backend.domain.user.application.UserService;
import ku_rum.backend.domain.user.domain.repository.UserRepository;
import ku_rum.backend.global.response.BaseResponse;
import ku_rum.backend.global.response.status.BaseExceptionResponseStatus;
import ku_rum.backend.global.security.jwt.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.Token;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/v1/buildings/view")
public class BuildingSearchController {

  private final BuildingSearchService buildingSearchService;
  private final UserService userService;

  /**
   * 전체 강의실의 핀포인트 조회
   *
   * @return
   */
  @GetMapping
  public BaseResponse viewAll(@AuthenticationPrincipal CustomUserDetails userDetails) {
    userService.validateUserDetails(userDetails);
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
  public BaseResponse<BuildingResponse> viewBuildingByNumber(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestParam("number")@NotNull @Min(1)  Long number) {
    userService.validateUserDetails(userDetails);
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
  public BaseResponse<BuildingResponse> viewBuildingByName(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestParam("name")@NotNull String name){
    userService.validateUserDetails(userDetails);
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
  public BaseResponse<List> viewBuildingByCategory(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable("category") String category){
    userService.validateUserDetails(userDetails);
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
  public BaseResponse<CategoryDetailResponse> viewBuildingByCategoryInBuilding(
          @AuthenticationPrincipal CustomUserDetails userDetails,
          @PathVariable("category") String category,
          @PathVariable("buildingId") Long buildingId
  ){
    userService.validateUserDetails(userDetails);
    CategoryDetailResponse categoryDetailResponse = buildingSearchService.viewBuildingDetailByCategory(category,buildingId);
    return BaseResponse.of(BaseExceptionResponseStatus.SUCCESS.getStatus(), categoryDetailResponse);
  }
}