package ku_rum.backend.domain.notice.presentation;

import ku_rum.backend.domain.notice.application.NoticeService;
import ku_rum.backend.domain.notice.domain.Notice;
import ku_rum.backend.domain.notice.domain.NoticeCategory;
import ku_rum.backend.domain.notice.dto.response.NoticeSimpleResponse;
import ku_rum.backend.global.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notices")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    /**
     * 주어진 조건(작성일 기준)을 만족하는 건국대학교 공지사항을 모두 크롤링
     * @return 성공메시지
     */
    @PostMapping("/crawl")
    public BaseResponse<String> crawlNotices() { //redis에 저장해두고 redis에 없으면 db에 저장을 한 (key에다가 공지사항 url링크 저장)
        noticeService.crawlAndSaveNotices();
        return BaseResponse.ok("크롤링 및 저장에 성공하였습니다.");
    }

    /**
     * 카테고리별 공지사항 조회
     * @param category
     * @return
     */
    @GetMapping
    public BaseResponse<List<NoticeSimpleResponse>> getNoticesByCategory(@RequestParam(name = "category") NoticeCategory category) {
        return BaseResponse.ok(noticeService.findNoticesByCategory(category));
    }

    /**
     * 공지사항 제목을 통한 검색
     * @param searchTerm
     * @return
     */
    @GetMapping("/search")
    public BaseResponse<List<NoticeSimpleResponse>> searchNotices(@RequestParam(name = "searchTerm") String searchTerm) {
        return BaseResponse.ok(noticeService.searchNoticesByTitle(searchTerm));
    }
}
