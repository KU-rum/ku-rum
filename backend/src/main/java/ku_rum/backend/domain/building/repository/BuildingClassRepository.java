package ku_rum.backend.domain.building.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import ku_rum.backend.domain.building.domain.Building;
import ku_rum.backend.domain.building.domain.QBuilding;
import ku_rum.backend.domain.building.domain.QBuildingCategory;
import ku_rum.backend.domain.building.dto.response.BuildingResponseDto;
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

  @PersistenceContext
  EntityManager entityManager;

  public List<BuildingResponseDto> findAllBuildings() {
    String query = "SELECT new ku_rum.backend.domain.building.dto.response.BuildingResponseDto(" +
            "m.id, m.name, m.number, m.abbreviation, m.latitude, m.longitude" +
            ") " +
            "FROM Building m";

    return entityManager.createQuery(query, BuildingResponseDto.class)
            .getResultList();
  }

  public BuildingResponseDto findBuildingByNumber(int number) {
    String query = "SELECT new ku_rum.backend.domain.building.dto.response.BuildingResponseDto(" +
            "m.id, m.name, m.number, m.abbreviation, m.latitude, m.longitude" +
            ") " +
            "FROM Building m " +
            "where m.number =: number";
    return entityManager.createQuery(query, BuildingResponseDto.class)
            .setParameter("number", number)
            .getSingleResult();
  }

  public BuildingResponseDto findBuildingByName(int name) {
    String query = "SELECT new ku_rum.backend.domain.building.dto.response.BuildingResponseDto(" +
            "m.id, m.name, m.number, m.abbreviation, m.latitude, m.longitude" +
            ") " +
            "FROM Building m " +
            "where m.name =: name";
    return entityManager.createQuery(query, BuildingResponseDto.class)
            .setParameter("name", name)
            .getSingleResult();
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
