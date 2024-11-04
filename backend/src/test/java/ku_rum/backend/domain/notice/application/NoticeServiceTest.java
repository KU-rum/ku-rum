package ku_rum.backend.domain.notice.application;

import ku_rum.backend.domain.notice.domain.Notice;
import ku_rum.backend.domain.notice.domain.NoticeCategory;
import ku_rum.backend.domain.notice.domain.NoticeStatus;
import ku_rum.backend.domain.notice.domain.repository.NoticeRepository;
import ku_rum.backend.domain.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.WebDriver;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class NoticeServiceTest {

    @InjectMocks
    private NoticeService noticeService;

    @Mock
    private NoticeRepository noticeRepository;

    @Mock
    private WebDriver driver;

    @Mock
    private User mockUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("카테고리별 공지사항 조회 성공")
    @Test
    void findNoticesByCategorySuccess() {
        Notice notice = Notice.builder()
                .title("Sample Notice")
                .url("https://example.com")
                .noticeCategory(NoticeCategory.AFFAIR)
                .noticeStatus(NoticeStatus.GENERAL)
                .build();

        when(noticeRepository.findByNoticeCategory(NoticeCategory.AFFAIR)).thenReturn(List.of(notice));

        List<Notice> result = noticeService.findNoticesByCategory(NoticeCategory.AFFAIR);

        assertEquals(1, result.size());
        assertEquals("Sample Notice", result.get(0).getTitle());
    }
}
