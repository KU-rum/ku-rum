package ku_rum.backend.domain.recruitment.application;

import ku_rum.backend.domain.recruitment.domain.RecruitCategory;
import ku_rum.backend.domain.recruitment.domain.Recruitment;
import ku_rum.backend.domain.recruitment.domain.repository.RecruitmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static ku_rum.backend.domain.recruitment.domain.RecruitCategory.SARAMIN;

@Service
@Slf4j
@RequiredArgsConstructor
public class RecruitmentService {

    private final RecruitmentRepository recruitmentRepository;

    @Transactional
    public void crawlAndSaveRecruitments() {
        WebDriver driver = null;

        try{
            driver = new ChromeDriver();
            for(RecruitCategory category : RecruitCategory.values()){
                String url = category.getUrl();
                driver.get(url);
                log.info("크롤링 시작: {}", category.getUrl());
                crawlAndSave(category, driver);
            }
        } finally {
            driver.quit();
        }
    }

    private void crawlAndSave(RecruitCategory category, WebDriver driver) {
        List<WebElement> recruitList = driver.findElements(By.cssSelector(category.getSelector()));

        if(recruitList.isEmpty()){
            log.warn("채용 공고를 찾지 못했습니다: {}", category.getUrl());
            return;
        }

        //사람인의 경우 첫번째요소가 실시간으로 바뀌기 때문에 건너뜁니다.
        int startIndex = category.isEqual(SARAMIN) ? 1 : 0;

        for (int i = startIndex; i < recruitList.size(); i++) {
            WebElement recruitElement = recruitList.get(i);
            try {
                String title = recruitElement.findElement(By.cssSelector(category.getTitleSelector())).getText();
                String url = recruitElement.findElement(By.cssSelector(category.getUrlSelector())).getAttribute("href");
                String company = recruitElement.findElement(By.cssSelector(category.getCompanySelector())).getText();
                String location, career;

                if (category.isEqual(SARAMIN)) {
                    location = recruitElement.findElement(By.cssSelector(category.getLocationSelector())).getText();
                    career = recruitElement.findElement(By.cssSelector(category.getCareerSelector())).getText();
                } else {
                    String[] text = recruitElement.findElement(By.cssSelector(category.getLocationSelector())).getText().split(" · ");
                    location = text[0];
                    career = text[1];
                }

                Recruitment recruitment = Recruitment.of(url, title, location, career, category, company);
                log.info("새로운 채용공고 저장: {}", title);
                recruitmentRepository.save(recruitment);
            } catch (Exception e) {
                log.error("채용공고 저장 중 오류 발생", e);
            }
        }
    }

}
