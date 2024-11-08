package ku_rum.backend.domain.notice.application;

import ku_rum.backend.domain.notice.domain.Notice;
import ku_rum.backend.domain.notice.domain.NoticeCategory;
import ku_rum.backend.domain.notice.domain.NoticeStatus;
import ku_rum.backend.domain.notice.domain.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
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

                List<WebElement> noticeList = driver.findElements(By.cssSelector(category.getSelector()));;

                for (WebElement noticeElement : noticeList) {
                    try {
                        String title = noticeElement.findElement(By.cssSelector("td.td-subject > a > strong")).getText();
                        String link = noticeElement.findElement(By.cssSelector("td.td-subject a")).getAttribute("href");
                        String date = noticeElement.findElement(By.cssSelector("td.td-date")).getText();

                        // 중복 체크: 이미 동일한 URL이 저장되어 있으면 건너뜀
                        Optional<Notice> existingNotice = noticeRepository.findByUrl(link);
                        if (existingNotice.isPresent()) continue;

                        // 날짜 확인 (2023년 또는 2024년만 저장)
                        if (date.startsWith("2023") || date.startsWith("2024") || date.startsWith("2022")) {
                            NoticeStatus status = NoticeStatus.GENERAL;

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
                            // 2023년 이전 공지사항이 나오면 해당 카테고리의 크롤링 종료
                            continueCrawling = false;
                            break;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                // 다음 페이지로 이동
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
            }
        }

        driver.quit();
    }

    public List<Notice> findNoticesByCategory(NoticeCategory category) {
        return noticeRepository.findByNoticeCategory(category);
    }
}
