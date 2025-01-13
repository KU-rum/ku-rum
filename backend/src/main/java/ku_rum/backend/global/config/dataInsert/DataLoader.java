package ku_rum.backend.global.config.dataInsert;

import ku_rum.backend.domain.building.domain.Building;
import ku_rum.backend.domain.building.domain.BuildingCategory;
import ku_rum.backend.domain.building.domain.repository.BuildingCategoryRepository;
import ku_rum.backend.domain.building.domain.repository.BuildingRepository;
import ku_rum.backend.domain.category.domain.Category;
import ku_rum.backend.domain.category.domain.repository.CategoryRepository;
import ku_rum.backend.domain.menu.domain.Menu;
import ku_rum.backend.domain.menu.domain.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataLoader implements ApplicationRunner {
  private final BuildingRepository buildingRepository;
  private final CategoryRepository categoryRepository;
  private final BuildingCategoryRepository buildingCategoryRepository;
  private final MenuRepository menuRepository;

  @Override
  public void run(ApplicationArguments args) throws Exception {

    //Building 데이터 저장
    List<Building> savedBuildings = buildingRepository.saveAll(BuildingInitializer.initialize());
    //Category 데이터 저장
    List<Category> savedCategories = categoryRepository.saveAll(CategoryInitializer.initialize());

    //BuildingCategory 데이터 저장
    buildingCategoryRepository.saveAll(
            BuildingCategoryInitializer.initialize(
                    new ArrayList<>(savedBuildings),
                    new ArrayList<>(savedCategories)
            )
    );
    // Menu 데이터 저장
    menuRepository.saveAll(MenuInitializer.initializer(new ArrayList<>(savedCategories)));

  }
}
