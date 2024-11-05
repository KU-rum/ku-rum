package ku_rum.backend.domain.building.service;

import ku_rum.backend.domain.building.dto.response.BuildingResponseDto;
import ku_rum.backend.domain.building.repository.BuildingClassRepository;
import ku_rum.backend.global.exception.building.BuildingNotFoundException;
import ku_rum.backend.global.exception.building.BuildingNotRegisteredException;
import ku_rum.backend.global.response.status.BaseExceptionResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BuildingSearchService {
  private final BuildingClassRepository buildingClassRepository;


  public List<BuildingResponseDto> findAllBuildings(){
    return Optional.ofNullable(buildingClassRepository.findAllBuildings())
            .filter(buildings -> !buildings.isEmpty())
            .orElseThrow(() -> new BuildingNotRegisteredException(BaseExceptionResponseStatus.NO_BUILDING_REGISTERED_CURRENTLY));//리스트가 비어있는 경우 예외처리
  }

  public BuildingResponseDto viewBuildingByNumber(int number) {
    return Optional.ofNullable(buildingClassRepository.findBuildingByNumber(number))
            .filter(c -> (c != null))
            .orElseThrow(() -> new BuildingNotFoundException(BaseExceptionResponseStatus.BUILDING_DATA_NOT_FOUND_BY_NUMBER));
  }

  public BuildingResponseDto viewBuildingByName(String name) {
    BuildingAbbrev foundBuildingAbbrev = Optional.ofNullable(checkMatchWithOriginalName(name))
            .orElseGet(() -> checkMatchWithAbbreviationName(name))
            .filter(c -> (c!= null))
            .orElseThrow(()-> new BuildingNotFoundException(BaseExceptionResponseStatus.BUILDING_DATA_NOT_FOUND_BY_NAME));

    return buildingClassRepository.findBuildingByName(foundBuildingAbbrev.getOriginalName());
  }

  /**
   * 유효한 줄임말 명칭인지 체크
   *
   * @param name
   */
  private Optional<BuildingAbbrev> checkMatchWithAbbreviationName(String name) {
    return BuildingAbbrev.fromAbbrevName(name);
  }

  /**
   * 유효한 정식명칭인지 체크
   *
   * @param name
   */
  private Optional<BuildingAbbrev> checkMatchWithOriginalName(String name) {
    return BuildingAbbrev.fromOriginalName(name);
  }
}