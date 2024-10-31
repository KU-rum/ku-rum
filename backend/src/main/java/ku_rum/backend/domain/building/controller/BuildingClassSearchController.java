package ku_rum.backend.domain.building.controller;

import ku_rum.backend.domain.building.dto.BuildingClassResponseDto;
import ku_rum.backend.domain.building.dto.response.BuildingsResponse;
import ku_rum.backend.domain.building.repository.BuildingClassRepository;
import ku_rum.backend.domain.building.service.BuildingClassSearchService;
import ku_rum.backend.global.exception.building.BuildingsNotFoundException;
import ku_rum.backend.global.response.BaseResponse;
import ku_rum.backend.global.response.status.BaseExceptionResponseStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/building/class/view")
public class BuildingClassSearchController {

  private final BuildingClassSearchService buildingClassSearchService;
  private final BuildingClassRepository buildingClassRepository;

  /*
    전체 강의실의 핀포인트 조회
   */
  @GetMapping("/all")
  public ResponseEntity<BaseResponse> viewAll() {
    try{
      List<BuildingClassResponseDto> buildings = buildingClassSearchService.findAllBuildings();
      if (buildings == null){//등록된 건물들 없을 때
        return ResponseEntity.ok(
                BuildingsResponse.ofList(
                        BaseExceptionResponseStatus.NO_BUILDING_REGISTERED,
                        buildings,
                        BaseExceptionResponseStatus.NO_BUILDING_REGISTERED.getMessage()
          )
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
              BaseExceptionResponseStatus.Building_NOT_FOUND,
              BaseExceptionResponseStatus.Building_NOT_FOUND.getMessage()
      );
    }

  }

  /*
    특정 강의실의 핀포인트 조회 (건물번호로)
  */
  @GetMapping("/search")
  public ResponseEntity<BaseResponse> viewBuildingByNumber(@RequestParam("number") int number){
    try{
      BuildingClassResponseDto result = buildingClassSearchService.findBuilding(number);
      if (result == null){
        BuildingsResponse.ofSingle(
                BaseExceptionResponseStatus.NO_BUILDING_REGISTERED,
                result,
                BaseExceptionResponseStatus.NO_BUILDING_REGISTERED.getMessage()
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
              BaseExceptionResponseStatus.Building_NOT_FOUND,
              BaseExceptionResponseStatus.Building_NOT_FOUND.getMessage()
      );
    }
  }

  /*
    특정 강의실의 핀포인트 조회 (건물정식명칭으로)
  */
  @GetMapping("/search")
  public ResponseEntity<BaseResponse> viewBuildingByName(@RequestParam("name") String name){
    try{
      BuildingClassResponseDto result = buildingClassSearchService.findBuilding(name);
      if (result == null){
        BuildingsResponse.ofSingle(
                BaseExceptionResponseStatus.NO_BUILDING_REGISTERED,
                result,
                BaseExceptionResponseStatus.NO_BUILDING_REGISTERED.getMessage()
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
              BaseExceptionResponseStatus.Building_NOT_FOUND,
              BaseExceptionResponseStatus.Building_NOT_FOUND.getMessage()
      );
    }
  }

   /*
    특정 강의실의 핀포인트 조회 (건물줄임말로)
  */
//  @GetMapping("/v1/building/class/view/search?abbreviation={abbreviation}")
//  public

}
