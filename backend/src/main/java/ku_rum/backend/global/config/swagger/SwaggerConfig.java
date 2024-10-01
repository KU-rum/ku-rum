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
  public GroupedOpenApi group1() {
    List<Tag> tags = List.of(
            new Tag().name("RegisterController").description("<b>[회원 등록]</b>회원 등록 API"),
            new Tag().name("LoginController").description("<b>[회원 로그인]</b>회원 로그인 API")
    );

    return GroupedOpenApi.builder()
            .group("user")
            .pathsToMatch("/user/**")
            .addOpenApiCustomizer(openApi -> {
              openApi.setTags(tags);
              openApi.info(apiInfo());
            })
            .build();
  }

  @Bean
  public GroupedOpenApi group2() {
    List<Tag> tags = List.of(
            new Tag().name("map controller").description("<b>[지도]</b> 지도 관련 API")
    );

    return GroupedOpenApi.builder()
            .group("map")
            .pathsToMatch("/map/**")
            .addOpenApiCustomizer(openApi -> {
              openApi.setTags(tags);
              openApi.info(apiInfo());
            })
            .build();
  }

  @Bean
  public GroupedOpenApi group3() {
    List<Tag> tags = List.of(
            new Tag().name("notice controller").description("<b>[공지]</b> 공지 관련 API")
    );

    return GroupedOpenApi.builder()
            .group("notice")
            .pathsToMatch("/notice/**")
            .addOpenApiCustomizer(openApi -> {
              openApi.setTags(tags);
              openApi.info(apiInfo());
            })
            .build();
  }

  @Bean
  public GroupedOpenApi group4() {
    List<Tag> tags = List.of(
            new Tag().name("kcube controller").description("<b>[K-CUBE/K-HUB]</b> 예약 API")
    );

    return GroupedOpenApi.builder()
            .group("kcube")
            .pathsToMatch("/kcube/**")
            .addOpenApiCustomizer(openApi -> {
              openApi.setTags(tags);
              openApi.info(apiInfo());
            })
            .build();
  }

  @Bean
  public GroupedOpenApi group5() {
    List<Tag> tags = List.of(
            new Tag().name("mypage controller").description("<b>[내 정보]</b> 내 정보 관련 API")
    );

    return GroupedOpenApi.builder()
            .group("mypage")
            .pathsToMatch("/mypage/**")
            .addOpenApiCustomizer(openApi -> {
              openApi.setTags(tags);
              openApi.info(apiInfo());
            })
            .build();
  }

  @Bean
  public GroupedOpenApi group6() {
    List<Tag> tags = List.of(
            new Tag().name("chatbot controller").description("<b>[챗봇]</b> 챗봇 API")
    );

    return GroupedOpenApi.builder()
            .group("chatbot")
            .pathsToMatch("/chatbot/**")
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
