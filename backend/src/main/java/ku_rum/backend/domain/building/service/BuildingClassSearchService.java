package ku_rum.backend.domain.building.service;

import ku_rum.backend.domain.building.dto.BuildingResponseDto;
import ku_rum.backend.domain.building.repository.BuildingClassRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BuildingClassSearchService {
  private final BuildingClassRepository buildingClassRepository;

  public List<BuildingResponseDto> findAllBuildings() {
    return buildingClassRepository.findAllBuildings();
  }

  public BuildingResponseDto findBuilding(int number) {
    return buildingClassRepository.findBuilding(number);
  }

  public BuildingResponseDto findBuilding(String name) {
    return buildingClassRepository.findBuilding(name);

  }

  public BuildingResponseDto findBuildingAbbrev(String abbrev) {//공A101 -> 공A
    String abbrevWithoutNumber = getAbbrevWithoutNumber(abbrev);
    return buildingClassRepository.findBuildingWithAbbrev(abbrevWithoutNumber);
    
  }

  private String getAbbrevWithoutNumber(String abbrev) {
    String[] abbrevList = {"경영","상허관","사","예","언어원","종강","의","생","동","산학","수","새","건","부","문","공","신공","이","창" };
    //abbrev에 abbrevList 의 원소가 있는지 판별 -> 단순 while문보다 다른 방법
    char[] abbrevArray = abbrev.toCharArray();
    int index = 0;
    while(index < abbrevArray.length){
      if (Character.isDigit(abbrevArray[index])){
        abbrevArray[index] = ' ';
      }
    }
    String abbrevWithoutNumber = abbrevArray.toString();
    return abbrevWithoutNumber;
  }


}
