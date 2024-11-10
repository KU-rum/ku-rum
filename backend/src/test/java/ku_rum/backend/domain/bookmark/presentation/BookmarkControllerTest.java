package ku_rum.backend.domain.bookmark.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import ku_rum.backend.domain.bookmark.application.BookmarkService;
import ku_rum.backend.domain.bookmark.dto.request.BookmarkSaveRequest;
import ku_rum.backend.domain.notice.domain.Notice;
import ku_rum.backend.domain.notice.domain.NoticeCategory;
import ku_rum.backend.domain.notice.domain.NoticeStatus;
import ku_rum.backend.domain.notice.dto.response.NoticeSimpleResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookmarkController.class)
class BookmarkControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookmarkService bookmarkService;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("북마크 추가 성공")
    @Test
    void addBookmarkSuccess() throws Exception {
        // given
        BookmarkSaveRequest request = new BookmarkSaveRequest(1L, "https://konkuk.ac.kr");

        doNothing().when(bookmarkService).addBookmark(request);

        // when & then
        mockMvc.perform(post("/api/v1/bookmarks/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.data").value("북마크가 저장되었습니다."));
    }

    @DisplayName("사용자 북마크 조회 성공")
    @Test
    void getBookmarksSuccess() throws Exception {
        // Notice 객체 생성 시 NoticeCategory 포함
        Notice notice = Notice.builder()
                .title("Notice")
                .url("https://konkuk.ac.kr")
                .date("2024-11-08")
                .noticeStatus(NoticeStatus.GENERAL)
                .noticeCategory(NoticeCategory.AFFAIR)
                .build();

        NoticeSimpleResponse response = new NoticeSimpleResponse(notice);

        when(bookmarkService.getBookmarks(1L)).thenReturn(List.of(response));

        // when & then
        mockMvc.perform(get("/api/v1/bookmarks/find")
                        .param("userId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.data[0].title").value("Notice"))
                .andExpect(jsonPath("$.data[0].url").value("https://konkuk.ac.kr"))
                .andExpect(jsonPath("$.data[0].date").value("2024-11-08"))
                .andExpect(jsonPath("$.data[0].category").value("학사"));
    }
}
