package ku_rum.backend.domain.building.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import ku_rum.backend.domain.building.domain.QBuilding;
import ku_rum.backend.domain.building.domain.QBuildingCategory;
import ku_rum.backend.domain.building.dto.BuildingResponseDto;
import ku_rum.backend.domain.category.domain.QCategory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class BuildingClassRepository {
  private final JPAQueryFactory queryFactory;

  private final QBuilding building = QBuilding.building;
  private final QBuildingCategory buildingCategory = QBuildingCategory.buildingCategory;
  private final QCategory category = QCategory.category;

  public List<BuildingResponseDto> findAllBuildings() {
    return queryFactory
            .select(Projections.constructor(BuildingResponseDto.class,
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

  public BuildingResponseDto findBuilding(int number) {
    return queryFactory
            .select(Projections.constructor(BuildingResponseDto.class,
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


  public BuildingResponseDto findBuilding(String name) {
    BuildingResponseDto result =  queryFactory
            .select(Projections.constructor(BuildingResponseDto.class,
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
    return result;

  }

  public BuildingResponseDto findBuildingWithAbbrev(String abbrevWithoutNumber) {
    log.debug("찾으려하는 줄임말 건물: {}", abbrevWithoutNumber);

    BuildingResponseDto result = queryFactory
            .select(Projections.constructor(BuildingResponseDto.class,
                    building.id.intValue(),
                    category.id.intValue(),
                    building.name,
                    building.number.intValue(),
                    building.abbreviation,
                    building.latitude.doubleValue(),
                    building.longitude.doubleValue()))
            .from(building)
            .where(building.abbreviation.eq(abbrevWithoutNumber))
            .innerJoin(buildingCategory).on(buildingCategory.building.eq(building))
            .innerJoin(category).on(buildingCategory.category.eq(category))
            .fetchOne();
    log.debug("찾은거 : {}", result);
    return result;
  }
}
