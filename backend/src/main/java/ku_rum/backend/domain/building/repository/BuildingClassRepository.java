package ku_rum.backend.domain.building.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import ku_rum.backend.domain.building.domain.Building;
import ku_rum.backend.domain.building.domain.QBuilding;
import ku_rum.backend.domain.building.domain.QBuildingCategory;
import ku_rum.backend.domain.building.dto.BuildingClassResponseDto;
import ku_rum.backend.domain.category.domain.QCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BuildingClassRepository {
  private final JPAQueryFactory queryFactory;

  private final QBuilding building = QBuilding.building;
  private final QBuildingCategory buildingCategory = QBuildingCategory.buildingCategory;
  private final QCategory category = QCategory.category;

  public List<BuildingClassResponseDto> findAllBuildings() {
    return queryFactory
            .select(Projections.constructor(BuildingClassResponseDto.class,
                    building.id.intValue(),
                    category.id.intValue(),
                    building.name,
                    building.number.intValue(),
                    building.abbreviation,
                    building.latitude.doubleValue(),
                    building.longitude.doubleValue()))
            .from(building)
            .innerJoin(buildingCategory).on(buildingCategory.building.eq(building))
            .innerJoin(category).on(buildingCategory.category.eq(category))
            .fetch();//결과 list 로 반환
  }

  public BuildingClassResponseDto findBuilding(int number) {
    return queryFactory
            .select(Projections.constructor(BuildingClassResponseDto.class,
                    building.id.intValue(),
                    category.id.intValue(),
                    building.name,
                    building.number.intValue(),
                    building.abbreviation,
                    building.latitude.doubleValue(),
                    building.longitude.doubleValue()))
            .from(building)
            .where(building.number.eq((long) number))
            .innerJoin(buildingCategory).on(buildingCategory.building.eq(building))
            .innerJoin(category).on(buildingCategory.category.eq(category))
            .fetchOne();
  }


  public BuildingClassResponseDto findBuilding(String name) {
    return queryFactory
            .select(Projections.constructor(BuildingClassResponseDto.class,
                    building.id.intValue(),
                    category.id.intValue(),
                    building.name,
                    building.number.intValue(),
                    building.abbreviation,
                    building.latitude.doubleValue(),
                    building.longitude.doubleValue()))
            .from(building)
            .where(building.name.eq(name))
            .innerJoin(buildingCategory).on(buildingCategory.building.eq(building))
            .innerJoin(category).on(buildingCategory.category.eq(category))
            .fetchOne();
  }
}
