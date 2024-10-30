package ku_rum.backend.domain.building.service;

import ku_rum.backend.domain.building.dto.BuildingClassResponseDto;
import ku_rum.backend.domain.building.repository.BuildingClassRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BuildingClassSearchService {
  private final BuildingClassRepository buildingClassRepository;

  public List<BuildingClassResponseDto> findAllBuildingClasses() {
    return null;
  }
}
