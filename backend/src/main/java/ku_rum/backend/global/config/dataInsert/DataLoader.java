package ku_rum.backend.global.config.dataInsert;

import ku_rum.backend.domain.building.domain.Building;
import ku_rum.backend.domain.building.domain.BuildingCategory;
import ku_rum.backend.domain.building.domain.repository.BuildingCategoryRepository;
import ku_rum.backend.domain.building.domain.repository.BuildingRepository;
import ku_rum.backend.domain.category.domain.Category;
import ku_rum.backend.domain.category.domain.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class DataLoader implements ApplicationRunner {
  private final BuildingRepository buildingRepository;
  private final CategoryRepository categoryRepository;
  private final BuildingCategoryRepository buildingCategoryRepository;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    //데이터 작성
    //Building 테이블에 데이터 넣기
    ArrayList<Building> buildings = BuildingInitializer.initialize();

    for (Building building : buildings){
      buildingRepository.save(building);
    }

    //Category 테이블에 데이터 넣기
    ArrayList<Category> categories = CategoryInitializer.initialize();

    for (Category category : categories){
      categoryRepository.save(category);
    }
    
    //BuildingCategory 테이블에 데이터 넣기
    ArrayList<BuildingCategory> buildingCategories = BuildingCategoryInitializer.initialize(buildings,categories);

    for (BuildingCategory buildingCategory : buildingCategories){
      buildingCategoryRepository.save(buildingCategory);
    }
    
    //Menu 테이블에 데이터 넣기
    

    //데이터 저장

  }

  private void initializeCategories(ArrayList<Category> categories) {

  }


}
