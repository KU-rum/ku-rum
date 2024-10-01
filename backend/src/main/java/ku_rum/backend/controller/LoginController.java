package ku_rum.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "LoginController", description = "회원 로그인") // 클래스 레벨 태그
public class LoginController {

  @GetMapping(value = "/test")
  @Operation(summary = "테스트", description = "테스트 설명")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다."),
          @ApiResponse(responseCode = "201", description = "작성되었습니다."),
          @ApiResponse(responseCode = "400", description = "잘못된 접근입니다."),
          @ApiResponse(responseCode = "401", description = "권한이 없습니다."),
          @ApiResponse(responseCode = "403", description = "접근 금지"),
          @ApiResponse(responseCode = "502", description = "Bad Gateway"),
  })
  public String someAPI() {
    return "aaaa";
  }
}
