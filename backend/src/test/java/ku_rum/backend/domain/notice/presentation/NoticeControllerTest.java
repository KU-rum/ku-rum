package ku_rum.backend.domain.notice.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import ku_rum.backend.domain.notice.application.NoticeService;
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

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(NoticeController.class)
class NoticeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NoticeService noticeService;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("공지사항 크롤링 성공")
    @Test
    void crawlNoticesSuccess() throws Exception {
        doNothing().when(noticeService).crawlAndSaveNotices();

        mockMvc.perform(post("/api/v1/notices/crawl")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.data").value("크롤링 및 저장에 성공하였습니다."));
    }

    @DisplayName("카테고리별 공지사항 조회 성공")
    @Test
    void getNoticesByCategorySuccess() throws Exception {
        NoticeSimpleResponse response = new NoticeSimpleResponse(
                Notice.builder()
                        .title("Notice Category Test")
                        .url("https://konkuk.ac.kr")
                        .date("2024-11-07")
                        .noticeStatus(NoticeStatus.GENERAL)
                        .noticeCategory(NoticeCategory.AFFAIR)
                        .build()
        );

        when(noticeService.findNoticesByCategory(NoticeCategory.AFFAIR))
                .thenReturn(List.of(response));

        mockMvc.perform(get("/api/v1/notices")
                        .param("category", "AFFAIR")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data[0].title").value("Notice Category Test"));
    }

    @DisplayName("검색어를 통한 공지사항 조회 성공")
    @Test
    void searchNoticesByTitleSuccess() throws Exception {
        NoticeSimpleResponse response = new NoticeSimpleResponse(
                Notice.builder()
                        .title("Notice Search Test")
                        .url("https://konkuk.ac.kr")
                        .date("2024-11-07")
                        .noticeStatus(NoticeStatus.GENERAL)
                        .noticeCategory(NoticeCategory.AFFAIR)
                        .build()
        );

        when(noticeService.searchNoticesByTitle("Search"))
                .thenReturn(List.of(response));

        mockMvc.perform(get("/api/v1/notices/search")
                        .param("searchTerm", "Search")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data[0].title").value("Notice Search Test"));
    }
}
