package ku_rum.backend.domain.notice.presentation;

import ku_rum.backend.domain.notice.application.NoticeService;
import ku_rum.backend.domain.notice.domain.Notice;
import ku_rum.backend.domain.notice.domain.NoticeCategory;
import ku_rum.backend.global.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notices")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    @PostMapping("/crawl")
    public BaseResponse<String> crawlNotices() {
        noticeService.crawlAndSaveNotices();
        return BaseResponse.ok("크롤링 및 저장에 성공하였습니다.");
    }

    @GetMapping
    public BaseResponse<List<Notice>> getNoticesByCategory(@RequestParam(name = "category") NoticeCategory category) {
        List<Notice> notices = noticeService.findNoticesByCategory(category);
        return BaseResponse.ok(notices);
    }
}
