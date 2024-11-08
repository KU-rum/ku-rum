package ku_rum.backend.domain.building.service;

import ku_rum.backend.domain.building.domain.Building;
import ku_rum.backend.domain.building.domain.BuildingCategory;
import ku_rum.backend.domain.building.dto.response.BuildingResponse;
import ku_rum.backend.domain.building.repository.BuildingCategoryQueryRepository;
import ku_rum.backend.domain.building.repository.BuildingQueryRepository;
import ku_rum.backend.domain.building.repository.BuildingRepository;
import ku_rum.backend.domain.category.domain.CategoryDetail;
import ku_rum.backend.domain.category.response.CategoryCafeteriaDetailResponse;
import ku_rum.backend.domain.category.response.CategoryDetailResponse;
import ku_rum.backend.domain.category.response.CategoryKcubeDetailResponse;
import ku_rum.backend.domain.category.service.CategoryService;
import ku_rum.backend.domain.menu.repository.MenuQueryRepository;
import ku_rum.backend.global.exception.building.BuildingNotFoundException;
import ku_rum.backend.global.exception.building.BuildingNotRegisteredException;
import ku_rum.backend.global.exception.category.CategoryNotProvidingDetail;
import ku_rum.backend.global.response.status.BaseExceptionResponseStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BuildingSearchService {
  private final BuildingQueryRepository buildingQueryRepository;
  private final BuildingRepository buildingRepository;
  private final CategoryService categoryService;
  private final BuildingCategoryQueryRepository buildingCategoryQueryRepository;
  private final MenuQueryRepository menuQueryRepository;


  public List<BuildingResponse> findAllBuildings() {
    return Optional.ofNullable(buildingQueryRepository.findAllBuildings())
            .filter(buildings -> !buildings.isEmpty())
            .orElseThrow(() -> new BuildingNotRegisteredException(BaseExceptionResponseStatus.NO_BUILDING_REGISTERED_CURRENTLY));//리스트가 비어있는 경우 예외처리
  }

  public BuildingResponse viewBuildingByNumber(int number) {
    return Optional.ofNullable(buildingQueryRepository.findBuildingByNumber(number))
            .filter(c -> (c != null))
            .orElseThrow(() -> new BuildingNotFoundException(BaseExceptionResponseStatus.BUILDING_DATA_NOT_FOUND_BY_NUMBER));
  }

  public BuildingResponse viewBuildingByName(String name) {
    BuildingAbbrev result = null;
    name = removeNumbersInName(name);
    String finalName = name;

    Optional<BuildingAbbrev> foundBuildingAbbrev1 = Optional.ofNullable(checkMatchWithOriginalName(finalName));
    Optional<BuildingAbbrev> foundBuildingAbbrev2 = Optional.ofNullable(checkMatchWithAbbreviationName(finalName));

    if (foundBuildingAbbrev1.isEmpty() && foundBuildingAbbrev2.isEmpty())
      throw new BuildingNotFoundException(BaseExceptionResponseStatus.BUILDING_DATA_NOT_FOUND_BY_NAME);
    else {
      if (foundBuildingAbbrev1.isEmpty())
        result = foundBuildingAbbrev2.get();
      else
        result = foundBuildingAbbrev1.get();

    }


    return buildingQueryRepository.findBuildingByName(result.getOriginalName());
  }

  /**
   * 줄임말 이름인 경우, 숫자 제거 (ex. 공A101 -> 공A)
   *
   * @param name
   * @return
   */
  private String removeNumbersInName(String name) {
    char[] array = name.toCharArray();
    StringBuilder sb = new StringBuilder();
    for (char a : array) {
      if (!Character.isDigit(a)) {  // 숫자가 아닐 경우 추가
        sb.append(a);
      }
    }
    return sb.toString();
  }

  /**
   * 유효한 줄임말 명칭인지 체크
   *
   * @param name
   */
  private BuildingAbbrev checkMatchWithAbbreviationName(String name) {
    return BuildingAbbrev.fromAbbrevName(name);
  }

  /**
   * 유효한 정식명칭인지 체크
   *
   * @param name
   */
  private BuildingAbbrev checkMatchWithOriginalName(String name) {
    return BuildingAbbrev.fromOriginalName(name);
  }

  public List<BuildingResponse> viewBuildingByCategory(String category) {
    List<Long> buildingIds = categoryService.findByCategoryReturnBuildingIds(category);//해당하는 기본키 리스트로 가져오기
    List<Long> buildingIdsFr = buildingCategoryQueryRepository.findByBuildingIds(buildingIds);
    List<Building> buildingsFound = buildingQueryRepository.findAllByIdIn(buildingIdsFr);
    return buildingsFound.stream()
            .map(building -> BuildingResponse.of(building))
            .collect(Collectors.toList());
  }


  public CategoryDetailResponse viewBuildingDetailByCategory(String category, Long buildingId) {
    boolean check = validateDetailProvidingCategory(category); //디테일을 제공하는 카테고리인지 판별
    if (!check)
      throw new CategoryNotProvidingDetail(BaseExceptionResponseStatus.CATEGORYNAME_NOT_PROVIDING_DETAIL);
    CategoryDetailResponse response = (category.equals("학생식당")) ? new CategoryCafeteriaDetailResponse(category, buildingId) : new CategoryKcubeDetailResponse(category, buildingId);
    if (response.getClass().equals(CategoryCafeteriaDetailResponse.class)) {
      Optional<Building> building = buildingRepository.findById(buildingId);
      if (building.isPresent()) {
        Optional<BuildingCategory> buildingCategory = buildingCategoryQueryRepository.findByBuildingId(building.get().getId());
        if (buildingCategory.isPresent()) {
          ((CategoryCafeteriaDetailResponse) response).setMenus(
                  menuQueryRepository.findAllByCategoryId(buildingCategory.get().getCategory().getId()));
        }
      }
    } else {
      ((CategoryKcubeDetailResponse) response).setFloor(buildingQueryRepository.findBuildingBy(buildingId));
    }
    return response;
  }

  private boolean validateDetailProvidingCategory(String category) {
    for (CategoryDetail c : CategoryDetail.values()){
      if (category.trim().equals(c.getCategoryName()) || category.trim().equals(c.name())){
        return true;
      }
    }
    return false;
  }
}