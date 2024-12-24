package ku_rum.backend.domain.building.application;

import jakarta.persistence.EntityNotFoundException;
import ku_rum.backend.domain.building.domain.Building;
import ku_rum.backend.domain.building.dto.response.BuildingResponse;
import ku_rum.backend.domain.building.domain.repository.BuildingCategoryQueryRepository;
import ku_rum.backend.domain.building.domain.repository.BuildingQueryRepository;
import ku_rum.backend.domain.building.domain.repository.BuildingRepository;
import ku_rum.backend.domain.building.domain.BuildingAbbrev;
import ku_rum.backend.domain.category.dto.response.CategoryDetailFloorAndMenusProviding;
import ku_rum.backend.domain.category.domain.CategoryDetail;
import ku_rum.backend.domain.category.dto.response.CategoryDetailResponse;
import ku_rum.backend.domain.category.application.CategoryService;
import ku_rum.backend.domain.menu.repository.MenuQueryRepository;
import ku_rum.backend.domain.menu.response.MenuSimpleResponse;
import ku_rum.backend.global.exception.building.BuildingNotFoundException;
import ku_rum.backend.global.exception.building.BuildingNotRegisteredException;
import ku_rum.backend.global.exception.category.CategoryNotProvidingDetail;
import ku_rum.backend.global.response.status.BaseExceptionResponseStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
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

  public Optional<BuildingResponse> viewBuildingByNumber(int number) {
    try {
      return Optional.ofNullable(buildingQueryRepository.findBuildingByNumber(number))
              .orElseThrow(() -> new BuildingNotFoundException(BaseExceptionResponseStatus.BUILDING_DATA_NOT_FOUND_BY_NUMBER));
    } catch (EmptyResultDataAccessException e) {
      throw new BuildingNotFoundException(BaseExceptionResponseStatus.BUILDING_DATA_NOT_FOUND_BY_NUMBER);
    }
  }

  public BuildingResponse viewBuildingByName(String name) {
    String inputName = name;
    name = removeNumbersInName(name);
    String finalName = name;

    List<BuildingAbbrev> potentialMatches = Arrays.asList(BuildingAbbrev.values());

    BuildingAbbrev matchedBuilding = potentialMatches.stream()
            .filter(b -> b.getOriginalName().toLowerCase().equals(finalName) ||
                    b.name().toLowerCase().equals(finalName))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Building name not found: " + inputName));

    return buildingQueryRepository.findBuildingByName(matchedBuilding.getOriginalName());
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
    List<Building> buildingsFound = buildingQueryRepository.findAllByIdIn(buildingIds);
    return buildingsFound.stream()
            .map(building -> BuildingResponse.of(building))
            .collect(Collectors.toList());
  }


  public CategoryDetailResponse viewBuildingDetailByCategory(String category, Long buildingId) {
    if (validateDetailProvidingCategory(category))
    {
      CategoryDetail categoryDetail = getCategoryDetail(category);
      return getCategoryDetailFloorAndMenus(categoryDetail,buildingId);
    }else{
      throw new CategoryNotProvidingDetail(BaseExceptionResponseStatus.CATEGORYNAME_NOT_PROVIDING_DETAIL);
    }
  }

  private CategoryDetailResponse getCategoryDetailFloorAndMenus(CategoryDetail categoryDetail, Long buildingId) {
    CategoryDetailFloorAndMenusProviding response = new CategoryDetailFloorAndMenusProviding();
    log.info("buildingId : {}", buildingId);

    Building building = buildingRepository.findById(buildingId)
            .orElseThrow(() -> new BuildingNotFoundException(BaseExceptionResponseStatus.BUILDING_DATA_NOT_FOUND_BY_NAME));

    buildingCategoryQueryRepository.findByBuildingId(buildingId)
            .ifPresent(buildingCategory -> {
              Long floor = building.getFloor();
              Optional<List<MenuSimpleResponse>> menus = menuQueryRepository
                      .findAllByCategoryId(buildingCategory.getCategory().getId());
              response.adding(floor, menus);
            });

    return response;
  }

  private CategoryDetail getCategoryDetail(String category) {
    for (CategoryDetail detail : CategoryDetail.values()) {
      if (detail.getCategoryName().equals(category)) {
        return detail;
      }
    }
    throw new CategoryNotProvidingDetail(BaseExceptionResponseStatus.CATEGORYNAME_NOT_PROVIDING_DETAIL);
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