package ku_rum.backend.domain.building.service;

import ku_rum.backend.domain.building.dto.BuildingResponseDto;
import ku_rum.backend.domain.building.repository.BuildingClassRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BuildingSearchService {
  private final BuildingClassRepository buildingClassRepository;

  private static final List<String> BUILDING_ABBREV_LIST = Arrays.asList(
          "경영", "상허관", "사", "예", "언어원", "종강", "의",
          "생", "동", "산학", "수", "새", "건", "부", "문",
          "공", "신공", "이", "창"
  );

  public List<BuildingResponseDto> findAllBuildings() {
    return buildingClassRepository.findAllBuildings();
  }

  public BuildingResponseDto viewBuildingByNumber(int number) {
    return buildingClassRepository.findBuilding(number);
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

  public String getAbbrevWithoutNumber(String name) {
    if (name == null || name.isEmpty()) {
      return "";
    }

    StringBuilder result = new StringBuilder();

    //한글만 추출
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
}