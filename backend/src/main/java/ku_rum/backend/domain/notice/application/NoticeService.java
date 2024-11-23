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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;

    @Qualifier("urlRedisTemplate")
    private final RedisTemplate<String, String> urlRedisTemplate;

    private static final String NOTICE_REDIS_KEY_PREFIX = "konkuk:notice:";

    @Async
    @Scheduled(fixedRate = 600000) //10분마다 실행
    @Transactional
    public void crawlAndSaveKonkukNotices() {
        WebDriver driver = null;

        try {
            driver = new ChromeDriver();
            for (NoticeCategory category : NoticeCategory.values()) {
                String url = category.getUrl();
                driver.get(url);
                log.info("크롤링 시작: {}", category.getUrl());

                boolean continueCrawling = true;
                while (continueCrawling) {
                    continueCrawling = goToNextButton(category, driver, crawlAndSave(category, driver));
                }
            }
        } finally {
            driver.quit();
        }
    }

    private boolean crawlAndSave(NoticeCategory category, WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // 페이지가 완전히 로드될 때까지 대기
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(category.getSelector())));

        List<WebElement> noticeList = driver.findElements(By.cssSelector(category.getSelector()));

        if (noticeList.isEmpty()) {
            log.warn("공지사항을 찾지 못했습니다: {}", category.getUrl());
            return false;
        }

        for (WebElement noticeElement : noticeList) {
            try {
                String title = noticeElement.findElement(By.cssSelector("td.td-subject > a > strong")).getText();
                String link = noticeElement.findElement(By.cssSelector("td.td-subject a")).getAttribute("href");
                String date = noticeElement.findElement(By.cssSelector("td.td-date")).getText();

                // 날짜가 2024년으로 시작하지 않으면 건너뜀 (일단 크롤링 너무 오래걸려서 걸어둠..후에 삭제)
                if (!date.startsWith("2024")) {
                    log.info("2024년 공지가 아님: {}", title);
                    return false;
                }

                // Redis에 이미 저장된 URL인지 확인
                String redisKey = NOTICE_REDIS_KEY_PREFIX + link;
                if (Boolean.TRUE.equals(urlRedisTemplate.hasKey(redisKey))) {
                    log.info("이미 저장된 공지사항: {}", link);
                    continue;
                }

                // 새 공지사항이면 데이터베이스에 저장
                Notice notice = Notice.of(title, link, date, category, isImportantNotice(noticeElement) ? NoticeStatus.IMPORTANT : NoticeStatus.GENERAL);

                log.info("새로운 2024년 공지사항 저장: {}", title);
                noticeRepository.save(notice);
                urlRedisTemplate.opsForValue().set(redisKey, link);

            } catch (Exception e) {
                log.error("공지사항 저장 중 오류 발생", e);
            }
        }
        return true;
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
