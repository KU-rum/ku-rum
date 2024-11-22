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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.List;

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
                switch(category){
                    case SARAMIN:
                        crawlAndSaveSaramIn(category, driver);
                        break;
                    case WANTED:
                        crawlAndSaveWanted();
                        break;
                }
            }

        } finally {
            driver.quit();
        }
    }

    private void crawlAndSaveSaramIn(RecruitCategory category, WebDriver driver) {
        List<WebElement> recruitList = driver.findElements(By.cssSelector(category.getSelector()));

        if(recruitList.isEmpty()){
            log.warn("채용 공고를 찾지 못했습니다: {}", category.getUrl());
            return;
        }

        for(WebElement recruitElement : recruitList){
            try{
                String title = recruitElement.findElement(By.cssSelector("div.box_item > div.col.notification_info > div.job_tit > a.str_tit > span")).getText();
                String url = recruitElement.findElement(By.cssSelector("div.box_item > div.col.notification_info > div.job_tit > a.str_tit")).getAttribute("href");
                String location = recruitElement.findElement(By.cssSelector("div.box_item > div.col.recruit_info > ul > li:nth-child(1) > p")).getText();
                String career = recruitElement.findElement(By.cssSelector("div.box_item > div.col.recruit_info > ul > li:nth-child(2) > p")).getText();

                Recruitment recruitment = Recruitment.of(url, title, location, career, category);
                log.info("새로운 채용공고 저장: {}", title);
                recruitmentRepository.save(recruitment);
            } catch (Exception e){
                log.error("채용공고 저장 중 오류 발생", e);
            }
        }
    }

    private void crawlAndSaveWanted() {
    }


}
