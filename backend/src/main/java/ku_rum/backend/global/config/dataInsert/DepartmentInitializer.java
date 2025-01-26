package ku_rum.backend.global.config.dataInsert;

import ku_rum.backend.domain.building.domain.Building;
import ku_rum.backend.domain.category.domain.Category;
import ku_rum.backend.domain.category.domain.CategoryType;
import ku_rum.backend.domain.department.domain.Department;
import ku_rum.backend.domain.department.domain.DepartmentType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DepartmentInitializer {
  public static ArrayList<Department> initializer(List<Building> buildings) {
    ArrayList<Department> departments = new ArrayList<>();

    departments.add(
            Department.of(DepartmentType.컴퓨터공학부.name(), buildings.get(15))
    );

    return departments;
  }
}
