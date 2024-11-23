package ku_rum.backend.domain.building.repository;

import ku_rum.backend.domain.building.domain.Building;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuildingRepository extends JpaRepository<Building, Long> {
}
