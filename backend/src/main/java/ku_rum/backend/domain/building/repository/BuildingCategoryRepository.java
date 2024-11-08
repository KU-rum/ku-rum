package ku_rum.backend.domain.building.repository;

import ku_rum.backend.domain.building.domain.BuildingCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BuildingCategoryRepository extends JpaRepository<BuildingCategory, Long> {
}
