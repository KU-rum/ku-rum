package ku_rum.backend.domain.notice.application;

import ku_rum.backend.domain.notice.domain.Notice;
import ku_rum.backend.domain.notice.domain.NoticeCategory;
import ku_rum.backend.domain.notice.domain.NoticeStatus;
import ku_rum.backend.domain.notice.domain.repository.NoticeRepository;
import ku_rum.backend.domain.notice.dto.response.NoticeSimpleResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class NoticeService {

    @Autowired
    private final NoticeRepository noticeRepository;

    public void crawlAndSaveNotices() {
        WebDriver driver = new ChromeDriver();

        for (NoticeCategory category : NoticeCategory.values()) {
            String url = category.getUrl();
            driver.get(url);

            boolean continueCrawling = true;
            while (continueCrawling) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                continueCrawling = goToNextButton(category, driver, crawling(category, driver, continueCrawling));
            }
        }

        driver.quit();
    }

    private boolean crawling(NoticeCategory category, WebDriver driver, boolean continueCrawling) {
        List<WebElement> noticeList = driver.findElements(By.cssSelector(category.getSelector()));

        for (WebElement noticeElement : noticeList) {
            try {
                String title = noticeElement.findElement(By.cssSelector("td.td-subject > a > strong")).getText();
                String link = noticeElement.findElement(By.cssSelector("td.td-subject a")).getAttribute("href");
                String date = noticeElement.findElement(By.cssSelector("td.td-date")).getText();

                Optional<Notice> existingNotice = noticeRepository.findByUrl(link);
                if (existingNotice.isPresent()) continue;

                // 중요 공지 여부 확인
                NoticeStatus status = isImportantNotice(noticeElement) ? NoticeStatus.IMPORTANT : NoticeStatus.GENERAL;

                // 크롤링 범위 지정 (작성년도 기준)
                if (date.startsWith("2024")) {
                    // Notice 객체 생성 및 저장
                    Notice notice = Notice.builder()
                            .url(link)
                            .title(title)
                            .date(date)
                            .noticeCategory(category)
                            .noticeStatus(status)
                            .build();
                    noticeRepository.save(notice);
                } else {
                    // 크롤링 종료
                    continueCrawling = false;
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return continueCrawling;
    }

    private static boolean goToNextButton(NoticeCategory category, WebDriver driver, boolean continueCrawling) {
        try {
            WebElement nextButton = driver.findElements(By.cssSelector(category.getNextButtonSelector()))
                    .stream()
                    .filter(WebElement::isDisplayed)
                    .filter(WebElement::isEnabled)
                    .findFirst()
                    .orElse(null);

            if (nextButton != null) {
                nextButton.click();
                // 페이지 로드 대기
                Thread.sleep(800);
            } else {
                continueCrawling = false;
            }
        } catch (Exception e) {
            continueCrawling = false;
            e.printStackTrace();
        }
        return continueCrawling;
    }

    private boolean isImportantNotice(WebElement noticeElement) {
        try {
            WebElement importantSpan = noticeElement.findElement(By.cssSelector("td.td-num > span"));
            log.info(importantSpan.getText());
            return importantSpan != null;
        } catch (Exception e) {
            return false; // span이 없으면 일반 공지로 간주
        }
    }

    /**
     * 카테고리별 공지사항 조회
     */
    public List<NoticeSimpleResponse> findNoticesByCategory(NoticeCategory category) {
        List<Notice> notices = noticeRepository.findByNoticeCategory(category);
        return notices.stream()
                .map(NoticeSimpleResponse::new)
                .toList();
    }

    /**
     * 제목으로 공지사항 검색
     */
    public List<NoticeSimpleResponse> searchNoticesByTitle(String searchTerm) {
        List<Notice> notices = noticeRepository.searchNoticesByTitle(searchTerm.trim());
        return notices.stream()
                .map(NoticeSimpleResponse::new)
                .toList();
    }
}
