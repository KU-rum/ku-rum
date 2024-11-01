package ku_rum.backend.domain.building.controller;

import ku_rum.backend.domain.building.dto.BuildingResponseDto;
import ku_rum.backend.domain.building.dto.response.BuildingsResponse;
import ku_rum.backend.domain.building.service.BuildingClassSearchService;
import ku_rum.backend.global.exception.building.BuildingNotRegisteredException;
import ku_rum.backend.global.exception.building.BuildingsNotFoundException;
import ku_rum.backend.global.response.BaseErrorResponse;
import ku_rum.backend.global.response.BaseResponse;
import ku_rum.backend.global.response.status.BaseExceptionResponseStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/building/view")
public class BuildingSearchController {

  private final BuildingClassSearchService buildingClassSearchService;

  /*
    전체 강의실의 핀포인트 조회
   */
  @GetMapping("/all")
  public ResponseEntity<BaseResponse> viewAll() {
    try{
      List<BuildingResponseDto> buildings = buildingClassSearchService.findAllBuildings();
      if (buildings == null){//등록된 건물들 없을 때
        throw new BuildingNotRegisteredException(
                BaseExceptionResponseStatus.NO_BUILDING_REGISTERED.getMessage(),
                BaseExceptionResponseStatus.NO_BUILDING_REGISTERED
        );
      }
      return ResponseEntity.ok(//등록된 건물들 정상 출력
              BuildingsResponse.ofList(
                      BaseExceptionResponseStatus.SUCCESS,
                      buildings,
                      BaseExceptionResponseStatus.SUCCESS.getMessage()
        )
      );
    }catch(BuildingsNotFoundException e){//등록된 건물들 조회하지 못했을 때
      throw new BuildingsNotFoundException(
              BaseExceptionResponseStatus.NO_BUILDING_REGISTERED.getMessage(),
              BaseExceptionResponseStatus.NO_BUILDING_REGISTERED

              );
    }

  }

  /*
    특정 강의실의 핀포인트 조회 (건물번호로)
  */
  @GetMapping("/search/number")
  public ResponseEntity<BaseResponse> viewBuildingByNumber(@RequestParam("number") int number){
    try{
      BuildingResponseDto result = buildingClassSearchService.viewBuildingByNumber(number);
      if (result == null){
        throw new BuildingsNotFoundException(
                BaseExceptionResponseStatus.BUILDING_DATA_NOT_FOUND_BY_NUMBER.getMessage(),
                BaseExceptionResponseStatus.BUILDING_DATA_NOT_FOUND_BY_NUMBER
        );
      }
      return ResponseEntity.ok(
              BuildingsResponse.ofSingle(
                      BaseExceptionResponseStatus.SUCCESS,
                      result,
                      BaseExceptionResponseStatus.SUCCESS.getMessage()
        )
      );
    }catch(BuildingsNotFoundException e){//등록된 건물을 조회하지 못했을 때
      throw new BuildingsNotFoundException(
              BaseExceptionResponseStatus.BUILDING_DATA_NOT_FOUND_BY_NUMBER.getMessage(),
              BaseExceptionResponseStatus.BUILDING_DATA_NOT_FOUND_BY_NUMBER

      );
    }
  }

  /*
    특정 강의실의 핀포인트 조회 (건물정식명칭으로, 건물 줄임말로)
  */
  @GetMapping("/search/name")
  public ResponseEntity<BaseResponse> viewBuildingByName(@RequestParam("name") String name){
    try{
      BuildingResponseDto result = buildingClassSearchService.findBuilding(name);
      if (result == null){
        throw new BuildingsNotFoundException(
                BaseExceptionResponseStatus.BUILDING_DATA_NOT_FOUND_BY_NAME.getMessage(),
                BaseExceptionResponseStatus.BUILDING_DATA_NOT_FOUND_BY_NAME
        );
      }
      return ResponseEntity.ok(
              BuildingsResponse.ofSingle(
                      BaseExceptionResponseStatus.SUCCESS,
                      result,
                      BaseExceptionResponseStatus.SUCCESS.getMessage()
              )
      );
    }catch(BuildingsNotFoundException e){//등록된 건물을 조회하지 못했을 때
      throw new BuildingsNotFoundException(
              BaseExceptionResponseStatus.BUILDING_DATA_NOT_FOUND_BY_NAME.getMessage(),
              BaseExceptionResponseStatus.BUILDING_DATA_NOT_FOUND_BY_NAME

      );
    }
  }

}
