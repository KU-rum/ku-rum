package ku_rum.backend.domain.building.controller;

import ku_rum.backend.domain.building.dto.BuildingClassResponseDto;
import ku_rum.backend.domain.building.service.BuildingClassSearchService;
import ku_rum.backend.global.type.ApiResponse;
import ku_rum.backend.global.type.SuccessResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/building/class/view")
public class BuildingClassSearchController {

  private final BuildingClassSearchService buildingClassSearchService;
  /*
    전체 강의실의 핀포인트 조회
   */
  @GetMapping("/all")
  public ResponseEntity<SuccessResponse<List<BuildingClassResponseDto>>> viewAll(){
    List<BuildingClassResponseDto> response = buildingClassSearchService.findAllBuildingClasses();
    return ResponseEntity.ok(new SuccessResponse<>(response));
  }

  /*
    특정 강의실의 핀포인트 조회 (건물번호로)
  */
//  @GetMapping("/v1/building/class/view/search?number={number}")
//  public

  /*
    특정 강의실의 핀포인트 조회 (건물정식명칭으로)
  */
//  @GetMapping("/v1/building/class/search?name={name}")
//  public

   /*
    특정 강의실의 핀포인트 조회 (건물줄임말로)
  */
//  @GetMapping("/v1/building/class/view/search?abbreviation={abbreviation}")
//  public

}
