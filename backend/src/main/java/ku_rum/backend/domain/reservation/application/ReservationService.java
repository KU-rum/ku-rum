package ku_rum.backend.domain.reservation.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.bonigarcia.wdm.WebDriverManager;
import jakarta.validation.Valid;
import ku_rum.backend.domain.reservation.dto.ReservationStatus;
import ku_rum.backend.domain.user.application.UserService;
import ku_rum.backend.domain.user.dto.request.WeinLoginRequest;
import ku_rum.backend.domain.user.dto.response.WeinLoginResponse;
import ku_rum.backend.global.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.cookie.BasicCookieStore;
import org.apache.hc.client5.http.cookie.Cookie;
import org.apache.hc.client5.http.impl.DefaultRedirectStrategy;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.jsoup.Jsoup;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;

import org.jsoup.nodes.Document;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationService {

    private static final String RESERVATION_URL = "https://wein.konkuk.ac.kr/ptfol/cmnt/cube/findCubeResveStep1.do";
    private static final String WEIN_LOGIN_URL = "https://wein.konkuk.ac.kr/common/user/loginProc.do";

    private final ObjectMapper objectMapper = new ObjectMapper(); // JSON 직렬화를 위한 ObjectMapper
    private final BasicCookieStore cookieStore = new BasicCookieStore();

    public BaseResponse<String> crawlReservationPage(WeinLoginRequest weinLoginRequest) {
        BaseResponse<WeinLoginResponse> loginResponse = loginToWein(weinLoginRequest);

        if (!loginResponse.getData().isSuccess()) {
            return BaseResponse.of(HttpStatus.UNAUTHORIZED, "Login failed, cannot proceed to reservation page.");
        }

        WebDriver driver = setupWebDriver();
        driver.get("https://wein.konkuk.ac.kr");
//        driver.get("https://wein.konkuk.ac.kr/common/user/login.do"); // 로그인 페이지 접근
        // 쿠키 추가
        for (Cookie cookie : cookieStore.getCookies()) {
            log.info("Cookie being added to WebDriver - Name: {}, Value: {}, Domain: {}, Path: {}",
                    cookie.getName(), cookie.getValue(), cookie.getDomain(), cookie.getPath());
            org.openqa.selenium.Cookie seleniumCookie = new org.openqa.selenium.Cookie(
                    cookie.getName(), cookie.getValue(), cookie.getDomain(),
                    cookie.getPath(), cookie.getExpiryDate(), cookie.isSecure());
            driver.manage().addCookie(seleniumCookie);
        }
        driver.get("https://wein.konkuk.ac.kr/ptfol/cmnt/cube/findCubeResveStep1.do"); // 바로 예약 페이지로 이동


        String pageSource = driver.getPageSource();
        if (pageSource.contains("로그인") || pageSource.contains("login")) {
            log.warn("User is still on login page, session might not have been transferred correctly.");
        }

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.attributeToBe(By.tagName("body"), "class", "vsc-initialized"));
            WebElement allCheckbox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buildAll")));
            if (!allCheckbox.isSelected()) {
                allCheckbox.click();
            }

            // "선택 건물 예약 현황 조회" 버튼 클릭
            WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("searchBtn")));
            searchButton.click();

            // 검색 결과가 로드될 때까지 기다림
            new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".time-table__title")));

            // HTML 소스 가져오기 및 Jsoup 파싱
            String html = driver.getPageSource();
            parseReservationStatus(html);

            return BaseResponse.ok("Crawling and outputting reservation status completed successfully.");
        } catch (TimeoutException e) {
            log.error("Timeout occurred while waiting for an element: {}", e.getMessage());
            return BaseResponse.of(HttpStatus.REQUEST_TIMEOUT, "Timeout while waiting for page elements to load.");
        } catch (Exception e) {
            log.error("An unexpected error occurred: {}", e.getMessage());
            return BaseResponse.of(HttpStatus.INTERNAL_SERVER_ERROR, "Error processing reservation data.");
        } finally {
            driver.quit();
        }
    }

    private BaseResponse<WeinLoginResponse> loginToWein(@Valid final WeinLoginRequest weinLoginRequest) {
        CloseableHttpClient httpClient = HttpClients.custom()
                .setRedirectStrategy(new DefaultRedirectStrategy())
                .setDefaultCookieStore(cookieStore) // 쿠키 저장소 설정
                .build();

        // RestTemplate 설정
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
        RestTemplate restTemplate = new RestTemplate(requestFactory);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(createRequestBody(weinLoginRequest), headers);
        log.info("Attempting login with userId: {} and password: {}", weinLoginRequest.getUserId(), weinLoginRequest.getPassword());

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    WEIN_LOGIN_URL,
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );

            String responseBody = response.getBody();
            if (responseBody != null) {
                if (responseBody.contains("index.do")) {
                    log.info("Login successful for userId: {}", weinLoginRequest.getUserId());
                    return BaseResponse.ok(new WeinLoginResponse(true, "Wein login successful"));
                } else if (responseBody.contains("login.do")) {
                    log.warn("Login failed for userId: {}", weinLoginRequest.getUserId());
                    return BaseResponse.of(HttpStatus.UNAUTHORIZED, new WeinLoginResponse(false, "Invalid Wein credentials"));
                }
            }

            log.error("Unexpected response for userId: {}, status code: {}, response body: {}", weinLoginRequest.getUserId(), response.getStatusCode(), response.getBody());
            return BaseResponse.of(HttpStatus.INTERNAL_SERVER_ERROR, new WeinLoginResponse(false, "Unexpected login response"));

        } catch (Exception e) {
            log.error("Exception occurred during login: ", e);
            return BaseResponse.of(HttpStatus.INTERNAL_SERVER_ERROR, new WeinLoginResponse(false, "Wein login failed due to server error"));
        }
    }

    private MultiValueMap<String, String> createRequestBody(WeinLoginRequest weinLoginRequest) {
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("userId", weinLoginRequest.getUserId());
        requestBody.add("pw", weinLoginRequest.getPassword());
        requestBody.add("rtnUrl", ""); // 리다이렉트 후 이동할 URL 지정, 필요시 수정
        return requestBody;
    }

    private WebDriver setupWebDriver() {
        // WebDriver 설정 (예: ChromeDriver)
//        WebDriverManager.chromedriver().setup();
        WebDriverManager.chromedriver().browserVersion("130.0.6723.117").setup();

        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--headless"); // 화면 없이 백그라운드 실행

//        WebDriver driver = new ChromeDriver(options);
//
//        String driverVersion = (String) ((ChromeDriver) driver).getCapabilities().getCapability("chrome").get("chromedriverVersion");
//        String browserVersion = (String) driver.getCapabilities().getBrowserVersion();
//        log.info("ChromeDriver Version: {}", driverVersion);
//        log.info("Chrome Browser Version: {}", browserVersion);


        return new ChromeDriver(options);
    }

    private boolean isReservationPageLoaded(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        // 특정 예약 화면 요소 확인
        WebElement reservationElement = driver.findElement(By.cssSelector(".time-table__title"));
        return reservationElement.getText().contains("K-Cube");
    }

    private void parseReservationStatus(String html) {
        Document document = Jsoup.parse(html);
        org.jsoup.select.Elements rooms = document.select(".time-table .class li");
        org.jsoup.select.Elements times = document.select(".time-table .hour li");

        for (org.jsoup.nodes.Element roomElement : rooms) {
            String roomName = roomElement.text();
            org.jsoup.select.Elements timeSlots = times.select("li");

            for (org.jsoup.nodes.Element timeSlot : timeSlots) {
                String time = timeSlot.select(".time").text();
                String status = timeSlot.select(".status").text();

                // 예약 상태를 출력
                log.info("Room: {}, Time: {}, Status: {}", roomName, time, status);            }
        }
    }
}
