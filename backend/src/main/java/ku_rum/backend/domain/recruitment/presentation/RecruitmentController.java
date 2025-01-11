package ku_rum.backend.domain.recruitment.presentation;

import ku_rum.backend.domain.notice.presentation.CrawlingResponse;
import ku_rum.backend.domain.recruitment.application.RecruitmentService;
import ku_rum.backend.global.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static ku_rum.backend.domain.notice.presentation.CrawlingResponse.START_CRAWLING;

@RestController
@RequestMapping("/api/v1/recruitments")
@RequiredArgsConstructor
public class RecruitmentController {
    private final RecruitmentService recruitmentService;

    @PostMapping("/crawl/recruit")
    public BaseResponse<String> crawlRecruitments() {
        recruitmentService.crawlAndSaveRecruitments();
        return BaseResponse.ok(START_CRAWLING.getMessage());
    }
}
