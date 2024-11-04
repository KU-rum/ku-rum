package ku_rum.backend.domain.notice.presentation;

import jakarta.persistence.EntityManager;
import ku_rum.backend.domain.notice.application.NoticeService;
import ku_rum.backend.domain.notice.domain.Notice;
import ku_rum.backend.domain.notice.domain.NoticeCategory;
import ku_rum.backend.global.response.BaseResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Commit;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class NoticeIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    void setUp() {

    }

    @DisplayName("공지사항 크롤링 및 저장 확인 및 저장된 공지사항을 찾는 테스트")
    @Test
    void testCrawlAndSaveNoticesAndFindByCategory() throws Exception {
        mockMvc.perform(post("/api/v1/notices/crawl")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.data").value("크롤링 및 저장에 성공하였습니다."));

        List<Notice> notices = noticeService.findNoticesByCategory(NoticeCategory.AFFAIR);
        assertThat(notices).isNotEmpty();

        mockMvc.perform(get("/api/v1/notices")
                        .param("category", "AFFAIR")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].title").isNotEmpty())
                .andExpect(jsonPath("$.data[0].url").isNotEmpty());

        Notice firstNotice = notices.get(0);
        mockMvc.perform(get("/api/v1/notices")
                        .param("category", "AFFAIR")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data[0].title").value(firstNotice.getTitle()))
                .andExpect(jsonPath("$.data[0].url").value(firstNotice.getUrl()));

    }
}
