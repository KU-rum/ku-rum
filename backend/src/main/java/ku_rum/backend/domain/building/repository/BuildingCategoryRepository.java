package ku_rum.backend.domain.building.repository;

import ku_rum.backend.domain.building.domain.BuildingCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BuildingCategoryRepository extends JpaRepository<BuildingCategory, Long> {
}
