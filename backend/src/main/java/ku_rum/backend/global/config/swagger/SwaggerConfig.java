package ku_rum.backend.global.config.swagger;

import io.swagger.v3.oas.models.tags.Tag;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
@Configuration
public class SwaggerConfig {

  @Bean
  public GroupedOpenApi loginRegister() {
    List<Tag> tags = List.of(
            new Tag().name("RegisterController").description("<b>[회원 등록]</b>회원 등록 API"),
            new Tag().name("LoginController").description("<b>[회원 로그인]</b>회원 로그인 API")
    );

    return createGroupedOpenApi("loginRegister", "/api/v1/loginRegister/**", tags);
  }

  @Bean
  public GroupedOpenApi map() {
    List<Tag> tags = List.of(
            new Tag().name("mapController").description("<b>[지도]</b> 지도 관련 API")
    );

    return createGroupedOpenApi("map", "/api/v1/map/**", tags);
  }

  @Bean
  public GroupedOpenApi notice() {
    List<Tag> tags = List.of(
            new Tag().name("noticeController").description("<b>[공지]</b> 공지 관련 API")
    );

    return createGroupedOpenApi("notice", "/api/v1/notice/**", tags);

  }

  @Bean
  public GroupedOpenApi kcube() {
    List<Tag> tags = List.of(
            new Tag().name("kcubController").description("<b>[K-CUBE/K-HUB]</b> 예약 API")
    );

    return createGroupedOpenApi("kcube", "/api/v1/kcube/**", tags);
  }

  @Bean
  public GroupedOpenApi mypage() {
    List<Tag> tags = List.of(
            new Tag().name("mypageController").description("<b>[내 정보]</b> 내 정보 관련 API")
    );

    return createGroupedOpenApi("mypage", "/api/v1/mypage/**", tags);

  }

  @Bean
  public GroupedOpenApi chatbot() {
    List<Tag> tags = List.of(
            new Tag().name("chatbotController").description("<b>[챗봇]</b> 챗봇 API")
    );

    return createGroupedOpenApi("chatbot", "/api/v1/chatbot/**", tags);

  }

  private GroupedOpenApi createGroupedOpenApi(String group, String pathsToMatch, List<Tag> tags) {
    return GroupedOpenApi.builder()
            .group(group)
            .pathsToMatch(pathsToMatch)
            .addOpenApiCustomizer(openApi -> {
              openApi.setTags(tags);
              openApi.info(apiInfo());
            })
            .build();
  }

  private Info apiInfo() {
    return new Info()
            .title("kurum api")
            .description("kurum api 문서입니다.")
            .version("1.0.0");
  }
}
