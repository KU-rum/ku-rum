package ku_rum.backend.domain.building.service;

import ku_rum.backend.domain.building.dto.BuildingClassResponseDto;
import ku_rum.backend.domain.building.repository.BuildingClassRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BuildingClassSearchService {
  private final BuildingClassRepository buildingClassRepository;

  public List<BuildingClassResponseDto> findAllBuildingClasses() {
    return buildingClassRepository.findAllBuildingClasses();
  }

//  public List<BuildingClassResponseDto> findBuildingClassesByNumber(String number) {
//    return buildingClassRepository.findBuildingClassesByNumber(number);
//  }
//
//  public List<BuildingClassResponseDto> findBuildingClassesByName(String name) {
//    return buildingClassRepository.findBuildingClassesByName(name);
//  }
//
//  public List<BuildingClassResponseDto> findBuildingClassesByAbbreviation(String abbreviation) {
//    return buildingClassRepository.findBuildingClassesByAbbreviation(abbreviation);
//  }
}
