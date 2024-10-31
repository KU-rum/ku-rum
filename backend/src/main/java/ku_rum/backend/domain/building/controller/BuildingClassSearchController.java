package ku_rum.backend.domain.building.controller;

import ku_rum.backend.domain.building.dto.BuildingClassResponseDto;
import ku_rum.backend.domain.building.dto.response.BuildingsResponse;
import ku_rum.backend.global.exception.building.BuildingsNotFoundException;
import ku_rum.backend.domain.building.service.BuildingClassSearchService;
import ku_rum.backend.global.response.BaseResponse;
import ku_rum.backend.global.response.status.BaseExceptionResponseStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
  public ResponseEntity<BaseResponse> viewAll() {
    try{
      List<BuildingClassResponseDto> buildings = buildingClassSearchService.findAllBuildingClasses();
      if (buildings.isEmpty()){//등록된 건물이 없을 때
        return ResponseEntity.ok(
                new BuildingsResponse(
                        BaseExceptionResponseStatus.NO_BUILDING_REGISTERED,
                        buildings,
                        BaseExceptionResponseStatus.NO_BUILDING_REGISTERED.getMessage()
                )
        );
      }
      return ResponseEntity.ok(//등록된 건물이 정상 출력
              new BuildingsResponse(
                      BaseExceptionResponseStatus.SUCCESS,
                      buildings,
                      BaseExceptionResponseStatus.SUCCESS.getMessage()
              )
      );
    }catch(BuildingsNotFoundException e){//서버 오류로 등록된 건물을 조회하지 못했을 때
      throw new BuildingsNotFoundException(
              BaseExceptionResponseStatus.Buildings_NOT_FOUND,
              BaseExceptionResponseStatus.Buildings_NOT_FOUND.getMessage()
      );
    }

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
