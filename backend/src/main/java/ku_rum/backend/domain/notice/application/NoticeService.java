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

@Service
@RequiredArgsConstructor
public class NoticeService {

    @Autowired
    private final NoticeRepository noticeRepository;
    private final String noticeUrl = "https://www.konkuk.ac.kr/konkuk/2238/subview.do"; //공지사항(카테고리:학사) url

    public void crawlAndSaveNotices() {
        WebDriver driver = new ChromeDriver();

        driver.get(noticeUrl);

        boolean continueCrawling = true;
        while (continueCrawling) {
            List<WebElement> noticeList = driver.findElements(By.cssSelector("#menu2238_obj1168 > div._fnctWrap > form:nth-child(2) > div > table > tbody > tr"));

            for (WebElement noticeElement : noticeList) {
                String title = noticeElement.findElement(By.cssSelector("td.td-subject > a > strong")).getText();
                String link = noticeElement.findElement(By.cssSelector("td.td-subject a")).getAttribute("href");
                String date = noticeElement.findElement(By.cssSelector("td.td-date")).getText();

                // 공지사항의 날짜가 2023년이나 2024년인지 확인
                if (date.startsWith("2023") || date.startsWith("2024")) {
                    NoticeCategory category = NoticeCategory.AFFAIR;
                    NoticeStatus status = NoticeStatus.GENERAL;

                    // Notice 객체 생성 및 저장
                    Notice notice = Notice.builder()
                            .title(title)
                            .url(link)
                            .noticeCategory(category)
                            .noticeStatus(status)
                            .build();
                    noticeRepository.save(notice);
                } else {
                    // 2023년 이전 공지사항이 나오면 크롤링 종료
                    continueCrawling = false;
                    break;
                }
            }

            // 다음 페이지로 이동
            if (continueCrawling) {
                continueCrawling = driver.findElements(By.cssSelector("#menu2238_obj1168 > div._fnctWrap > form:nth-child(3) > div > div > a._listNext"))
                        .stream()
                        .findFirst()
                        .map(nextButton -> {
                            nextButton.click();
                            return true;})
                        .orElse(false);
            }
        }

        driver.quit();
    }

    public List<Notice> findNoticesByCategory(NoticeCategory category) {
        return noticeRepository.findByNoticeCategory(category);
    }
}
