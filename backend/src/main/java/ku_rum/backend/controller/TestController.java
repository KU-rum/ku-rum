package ku_rum.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "user registration", description = "<b>[회원 등록]</b>회원 등록 API")
public class TestController {

  @GetMapping("/test")
  @Operation(summary = "테스트", description = "테스트 설명")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다."),
          @ApiResponse(responseCode = "201", description = "작성되었습니다."),
          @ApiResponse(responseCode = "400", description = "잘못된 접근입니다."),
          @ApiResponse(responseCode = "401", description = "권한이 없습니다."),
          @ApiResponse(responseCode = "403", description = "접근 금지"),
          @ApiResponse(responseCode = "502", description = "Bad Gateway"),
          //등
  })
  public String someAPI(){
    return "aaaa";
  }
}