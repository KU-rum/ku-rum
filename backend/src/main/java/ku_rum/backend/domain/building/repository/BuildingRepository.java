package ku_rum.backend.domain.building.repository;

import ku_rum.backend.domain.building.domain.Building;
import ku_rum.backend.domain.building.domain.BuildingCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BuildingRepository extends JpaRepository<Building, Long> {
}
