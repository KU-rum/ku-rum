package ku_rum.backend.domain.category.application;

import ku_rum.backend.domain.building.domain.repository.BuildingCategoryQueryRepository;
import ku_rum.backend.domain.category.domain.repository.CategoryQueryRepository;
import ku_rum.backend.domain.category.domain.repository.CategoryRepository;
import ku_rum.backend.global.exception.category.CategoryNotExist;
import ku_rum.backend.global.response.status.BaseExceptionResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
  private final CategoryRepository categoryRepository;
  private final BuildingCategoryQueryRepository buildingCategoryQueryRepository;
  private final CategoryQueryRepository categoryQueryRepository;

  public List<Long> findByCategoryReturnBuildingIds(String category){
    List<Long> categoryIds = categoryQueryRepository.findAllByName(category)
            .orElseThrow(() -> new CategoryNotExist(BaseExceptionResponseStatus.CATEGROYNAME_NOT_EXIST));
    List<Long> buildingCategoryIds = buildingCategoryQueryRepository.findBuildingCategoryIds(categoryIds);
    return buildingCategoryQueryRepository.findBuildingIdsByCategoryIds(buildingCategoryIds);
  }
}