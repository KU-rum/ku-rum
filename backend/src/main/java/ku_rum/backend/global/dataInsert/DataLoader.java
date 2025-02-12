package ku_rum.backend.global.dataInsert;

import jakarta.annotation.PostConstruct;
import ku_rum.backend.document.BuildingDocument;
import ku_rum.backend.domain.building.application.BuildingSearchService;
import ku_rum.backend.domain.building.domain.Building;
import ku_rum.backend.domain.building.domain.repository.BuildingElasticRepository;
import ku_rum.backend.domain.buildingCategory.domain.repository.BuildingCategoryRepository;
import ku_rum.backend.domain.building.domain.repository.BuildingRepository;
import ku_rum.backend.domain.category.domain.Category;
import ku_rum.backend.domain.category.domain.repository.CategoryRepository;
import ku_rum.backend.domain.department.domain.Department;
import ku_rum.backend.domain.department.domain.repository.DepartmentRepository;
import ku_rum.backend.domain.menu.domain.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataLoader implements ApplicationRunner {
  private final BuildingRepository buildingRepository;
  private final CategoryRepository categoryRepository;
  private final BuildingCategoryRepository buildingCategoryRepository;
  private final MenuRepository menuRepository;
  private final DepartmentRepository departmentRepository;

  private final BuildingSearchService buildingSearchService;

  @PostConstruct
  public void init() {
    System.out.println("DataLoader 실행됨!");
  }

  @Override
  @Transactional
  public void run(ApplicationArguments args) throws Exception {
    // 각 리포지토리에서 데이터 존재 여부 확인
    if (buildingRepository.count() == 0) {
      List<Building> savedBuildings = buildingRepository.saveAll(BuildingInitializer.initialize());
      buildingSearchService.addAllElasticRepo(savedBuildings);

      if (categoryRepository.count() == 0) {
        List<Category> savedCategories = categoryRepository.saveAll(CategoryInitializer.initialize());

        if (departmentRepository.count() == 0) {
          List<Department> departments = departmentRepository.saveAll(DepartmentInitializer.initializer(savedBuildings));

          if (buildingCategoryRepository.count() == 0) {
            buildingCategoryRepository.saveAll(
                    BuildingCategoryInitializer.initialize(
                            new ArrayList<>(savedBuildings),
                            new ArrayList<>(savedCategories)
                    )
            );
          }

          if (menuRepository.count() == 0) {
            menuRepository.saveAll(MenuInitializer.initializer(new ArrayList<>(savedCategories)));
          }
        }
      }
    }
  }
}