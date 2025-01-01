package ku_rum.backend.domain.building.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import ku_rum.backend.domain.building.dto.response.BuildingResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class BuildingClassRepository {
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

  public BuildingResponse findBuildingByNumber(int number) {
    String query = "SELECT new ku_rum.backend.domain.building.dto.response.BuildingResponse(" +
            "m.id, m.name, m.number, m.abbreviation, m.latitude, m.longitude" +
            ") " +
            "FROM Building m " +
            "where m.number =: number";
    return entityManager.createQuery(query, BuildingResponse.class)
            .setParameter("number", number)
            .getSingleResult();
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

}
