package ku_rum.backend.domain.building.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import ku_rum.backend.domain.building.domain.Building;
import ku_rum.backend.domain.building.dto.response.BuildingResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class BuildingQueryRepository {
  private final JPAQueryFactory queryFactory;

  @PersistenceContext
  EntityManager entityManager;

  public List<BuildingResponse> findAllBuildings() {
    String query = "SELECT new ku_rum.backend.domain.building.dto.response.BuildingResponse(" +
            "m.id, m.name, m.number, m.abbreviation, m.latitude, m.longitude" +
            ") " +
            "FROM Building m";

    return entityManager.createQuery(query, BuildingResponse.class)
            .getResultList();
  }

  public Optional<BuildingResponse> findBuildingByNumber(int number) {
    String query = "SELECT new ku_rum.backend.domain.building.dto.response.BuildingResponse(" +
            "m.id, m.name, m.number, m.abbreviation, m.latitude, m.longitude" +
            ") " +
            "FROM Building m " +
            "where m.number =: number";
    return Optional.ofNullable(entityManager.createQuery(query, BuildingResponse.class)
            .setParameter("number", number)
            .getSingleResult());
  }

  public BuildingResponse findBuildingByName(String name) {
    String query = "SELECT new ku_rum.backend.domain.building.dto.response.BuildingResponse(" +
            "m.id, m.name, m.number, m.abbreviation, m.latitude, m.longitude" +
            ") " +
            "FROM Building m " +
            "where m.name =: name";
    return entityManager.createQuery(query, BuildingResponse.class)
            .setParameter("name", name)
            .getSingleResult();
  }

  public List<Building> findAllByIdIn(List<Long> buildingIdsFr) {
    String query = "SELECT m FROM Building m WHERE m.id IN :buildingIds";

    // buildingIdsFr에 해당하는 모든 Building 엔티티를 조회
    return entityManager.createQuery(query, Building.class)
            .setParameter("buildingIds", buildingIdsFr)
            .getResultList();
  }

  public Building findBuildingBy(Long buildingId) {
    String query = "SELECT m FROM Building m WHERE m.id =: buildingId";
    return entityManager.createQuery(query, Building.class)
            .setParameter("buildingId" , buildingId)
            .getSingleResult();
  }

  public Long findBuildingByNumber_test(long number) {
    String query = "SELECT m.id FROM Building m " +
            "where m.number =: number";
    return entityManager.createQuery(query, Long.class)
            .setParameter("number", number)
            .getSingleResult();
  }
}
