package ku_rum.backend.domain.building.domain.repository;

import ku_rum.backend.domain.building.domain.Building;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BuildingRepository extends JpaRepository<Building, Long> {
  Optional<Building> findById(Long id);

  Long findBuildingByNumber(long l);
}
