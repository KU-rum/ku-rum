package ku_rum.backend.global.config.dataInsert;

import ku_rum.backend.domain.building.domain.Building;
import ku_rum.backend.domain.building.domain.BuildingCategory;
import ku_rum.backend.domain.category.domain.Category;
import org.springframework.stereotype.Component;
import java.util.ArrayList;

@Component
public class BuildingCategoryInitializer {
  public static ArrayList<BuildingCategory> initialize(ArrayList<Building> buildings, ArrayList<Category> categories) {
    ArrayList<BuildingCategory> buildingCategories = new ArrayList<>();

    Building building0 = buildings.get(0);
    Category category1 = categories.get(1);
    Category category2 = categories.get(2);

    buildingCategories.add(
            BuildingCategory.of(building0, category1)
    );
    buildingCategories.add(
            BuildingCategory.of(building0, category2)
    );
    buildingCategories.add(
            BuildingCategory.of(building0, category2)
    );

    //더 추가 예정

    return buildingCategories;


  }
}
