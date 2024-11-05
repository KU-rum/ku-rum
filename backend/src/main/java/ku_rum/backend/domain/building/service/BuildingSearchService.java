package ku_rum.backend.domain.building.service;

import ku_rum.backend.domain.building.dto.response.BuildingResponse;
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


  public List<BuildingResponse> findAllBuildings(){
    return Optional.ofNullable(buildingClassRepository.findAllBuildings())
            .filter(buildings -> !buildings.isEmpty())
            .orElseThrow(() -> new BuildingNotRegisteredException(BaseExceptionResponseStatus.NO_BUILDING_REGISTERED_CURRENTLY));//리스트가 비어있는 경우 예외처리
  }

  public BuildingResponse viewBuildingByNumber(int number) {
    return Optional.ofNullable(buildingClassRepository.findBuildingByNumber(number))
            .filter(c -> (c != null))
            .orElseThrow(() -> new BuildingNotFoundException(BaseExceptionResponseStatus.BUILDING_DATA_NOT_FOUND_BY_NUMBER));
  }

  public BuildingResponse viewBuildingByName(String name) {
    BuildingAbbrev result = null;
    name = removeNumbersInName(name);
    String finalName = name;

    Optional<BuildingAbbrev> foundBuildingAbbrev1 = Optional.ofNullable(checkMatchWithOriginalName(finalName));
    Optional<BuildingAbbrev> foundBuildingAbbrev2 = Optional.ofNullable(checkMatchWithAbbreviationName(finalName));

    if (foundBuildingAbbrev1.isEmpty() && foundBuildingAbbrev2.isEmpty())
            throw new BuildingNotFoundException(BaseExceptionResponseStatus.BUILDING_DATA_NOT_FOUND_BY_NAME);
    else {
      if (foundBuildingAbbrev1.isEmpty())
        result = foundBuildingAbbrev2.get();
      else
        result = foundBuildingAbbrev1.get();

    }


    return buildingClassRepository.findBuildingByName(result.getOriginalName());
  }

  /**
   * 줄임말 이름인 경우, 숫자 제거 (ex. 공A101 -> 공A)
   *
   * @param name
   * @return
   */
  private String removeNumbersInName(String name) {
    char[] array = name.toCharArray();
    StringBuilder sb = new StringBuilder();
    for (char a : array) {
      if (!Character.isDigit(a)) {  // 숫자가 아닐 경우 추가
        sb.append(a);
      }
    }
    return sb.toString();
  }

  /**
   * 유효한 줄임말 명칭인지 체크
   *
   * @param name
   */
  private BuildingAbbrev checkMatchWithAbbreviationName(String name) {
    return BuildingAbbrev.fromAbbrevName(name);
  }

  /**
   * 유효한 정식명칭인지 체크
   *
   * @param name
   */
  private BuildingAbbrev checkMatchWithOriginalName(String name) {
    return BuildingAbbrev.fromOriginalName(name);
  }
}