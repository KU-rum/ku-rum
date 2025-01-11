package ku_rum.backend.domain.notice.presentation;

import lombok.Getter;

@Getter
public enum CrawlingResponse {
    START_CRAWLING("크롤링 작업이 시작되었습니다."),
    CRAWLING_FAIL("크롤링에 실패하였습니다.");

    private final String message;

    CrawlingResponse(String message) {
        this.message = message;
    }
}
