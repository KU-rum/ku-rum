package ku_rum.backend.domain.notice.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import ku_rum.backend.domain.notice.application.NoticeService;
import ku_rum.backend.domain.notice.domain.Notice;
import ku_rum.backend.domain.notice.domain.NoticeCategory;
import ku_rum.backend.domain.notice.domain.NoticeStatus;
import ku_rum.backend.domain.user.domain.User;
import ku_rum.backend.global.response.BaseResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
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

    @Mock
    private User mockUser;

    @DisplayName("학사 카테고리 공지사항 크롤링 및 저장 성공")
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
        Notice notice = Notice.builder()
                .title("Sample Notice")
                .url("https://example.com")
                .noticeCategory(NoticeCategory.AFFAIR)
                .noticeStatus(NoticeStatus.GENERAL)
                .build();

        when(noticeService.findNoticesByCategory(NoticeCategory.AFFAIR))
                .thenReturn(List.of(notice));

        mockMvc.perform(get("/api/v1/notices")
                        .param("category", "AFFAIR")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data[0].title").value("Sample Notice"));
    }
}
