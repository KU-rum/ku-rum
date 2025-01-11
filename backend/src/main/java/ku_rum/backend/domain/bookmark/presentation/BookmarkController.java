package ku_rum.backend.domain.bookmark.presentation;

import jakarta.validation.Valid;
import ku_rum.backend.domain.bookmark.application.BookmarkService;
import ku_rum.backend.domain.bookmark.dto.request.BookmarkSaveRequest;
import ku_rum.backend.domain.notice.dto.response.NoticeSimpleResponse;
import ku_rum.backend.global.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bookmarks")
@RequiredArgsConstructor
@Validated
public class BookmarkController {

    private final BookmarkService bookmarkService;

    /**
     * User가 북마크를 저장할 때 호출
     * @param bookmarkRequest
     * @return
     */
    @PostMapping("/save")
    public BaseResponse<String> addBookmark(@RequestBody @Valid final BookmarkSaveRequest bookmarkRequest) {
        bookmarkService.addBookmark(bookmarkRequest);
        return BaseResponse.ok("북마크가 저장되었습니다.");
    }

    /**
     * User가 북마크로 저장한 모든 공지사항 조회
     * @param userId
     * @return
     */
    @GetMapping("/find")
    public BaseResponse<List<NoticeSimpleResponse>> getBookmarks(@RequestParam(name = "userId") Long userId) {
        List<NoticeSimpleResponse> bookmarks = bookmarkService.getBookmarks(userId);
        return BaseResponse.ok(bookmarks);
    }
}
