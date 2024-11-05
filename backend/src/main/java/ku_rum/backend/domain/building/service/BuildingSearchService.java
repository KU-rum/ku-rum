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
            .filter(building -> (building != null) )
            .orElseThrow(()-> new BuildingNotFoundException(BaseExceptionResponseStatus.BUILDING_DATA_NOT_FOUND_BY_NUMBER));
  }

  public BuildingResponseDto findBuilding(String name) {
    String abbrevWithoutNumber = getAbbrevWithoutNumber(name);

    if (abbrevWithoutNumber.isEmpty()){
      return null;
    }
    //추출된 약어가 유효한 건물 줄임말 목록에 있는지 확인
    if (isValidBuildingAbbrev(abbrevWithoutNumber)) {
      return buildingClassRepository.findBuildingWithAbbrev(abbrevWithoutNumber); //줄임말로 조회
    }else {
      return buildingClassRepository.findBuilding(abbrevWithoutNumber); //정식명칭으로 조회
    }
  }

  public String getAbbrevWithoutNumber(String name) {//optional 로 처리!
    if (name == null || name.isEmpty()) {
      return "";
    }

    StringBuilder result = new StringBuilder();

    //한글만 추출 -> 람다로 처리!
    for (char c : name.toCharArray()) {
      if (Character.UnicodeBlock.of(c) == Character.UnicodeBlock.HANGUL_SYLLABLES) {
        result.append(c);
      }
    }

    return result.toString().trim();
  }

  public boolean isValidBuildingAbbrev(String abbrev) {
    return BUILDING_ABBREV_LIST.stream()
            .anyMatch(validAbbrev -> abbrev.startsWith(validAbbrev));
  }

  public BuildingResponseDto viewBuildingByName(String name) {
    int checkOriginal = checkMatchWithOriginalName(name);
    int checkAbbrev = checkMatchWithAbbreviationName(name);
  }

  /**
   * 유효한 줄임말 명칭인지 체크
   *
   * @param name
   */
  private int checkMatchWithAbbreviationName(String name) {
  }

  /**
   * 유효한 정식명칭인지 체크
   *
   * @param name
   */
  private int checkMatchWithOriginalName(String name) {
    BuildingAbbrev.valueOf(name);

  }
}