package ku_rum.backend.domain.reservation.application;

import ku_rum.backend.domain.reservation.domain.ReservationCategory;
import ku_rum.backend.domain.reservation.dto.RoomReservation;
import ku_rum.backend.domain.reservation.dto.TimeTable;
import ku_rum.backend.domain.reservation.dto.request.WeinLoginRequest;
import ku_rum.backend.global.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import org.jsoup.nodes.Document;

import java.time.Duration;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationService {

    private WebDriver driver; // WebDriver를 전역 변수로 선언
    private boolean isLoggedIn = false; // 로그인 상태를 추적

    public BaseResponse<String> crawlReservationPage(WeinLoginRequest weinLoginRequest) {

        if (driver == null) {
            driver = setupWebDriver();
        }

        try {
            // 로그인 페이지 접근
            driver.get(ReservationCategory.RESERVATION_LOGIN.getValue());
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));

            // 로그인 수행
            if (!performLogin(driver, weinLoginRequest)) {
                return BaseResponse.of(HttpStatus.UNAUTHORIZED, "로그인 실패! 아이디 또는 비밀번호를 확인해주세요.", null);
            }

            isLoggedIn = true;

            // 예약 페이지 접근 및 크롤링
            return crawlReservationData(driver);

        } catch (Exception e) {
            log.error("크롤링 중 오류 발생: {}", e.getMessage(), e);
            return BaseResponse.of(HttpStatus.INTERNAL_SERVER_ERROR, "예약 데이터 크롤링 중 오류가 발생했습니다.", null);
        }
    }
    private boolean performLogin(WebDriver driver, WeinLoginRequest weinLoginRequest) {
        try {
            // 아이디와 비밀번호 입력
            WebElement userIdField = driver.findElement(By.name("userId"));
            WebElement passwordField = driver.findElement(By.name("pw"));
            WebElement loginButton = driver.findElement(By.id("loginBtn"));

            userIdField.sendKeys(weinLoginRequest.getUserId());
            passwordField.sendKeys(weinLoginRequest.getPassword());
            loginButton.click();

            // 로그인 성공 여부 확인
            String currentUrl = driver.getCurrentUrl();

            return currentUrl.contains("index.do");
        } catch (Exception e) {
            log.error("로그인 중 오류 발생: {}", e.getMessage(), e);
            return false;
        }
    }

    private BaseResponse<String> crawlReservationData(WebDriver driver) {
        try {
            String targetUrl = ReservationCategory.RESERVATION_PAGE.getValue();
            driver.get(targetUrl);

            // URL 접근 확인 로그
            String currentUrl = driver.getCurrentUrl();
            if (!currentUrl.equals(targetUrl)) {
                log.warn("URL 접근 실패: 예상 URL: {}, 현재 URL: {}", targetUrl, currentUrl);
                return BaseResponse.of(HttpStatus.INTERNAL_SERVER_ERROR, "예약 페이지 접근 중 오류가 발생했습니다.", null);
            }
            log.info("URL 접근 성공: 현재 URL: {}", currentUrl);

            // "전체" 체크박스 확인 및 선택
            try {
                WebElement allCheckbox = driver.findElement(By.cssSelector(ReservationCategory.CHECKBOX_SELECTOR.getValue()));
                if (!allCheckbox.isSelected()) {
                    allCheckbox.click();
                    log.info("{}를 선택했습니다.", ReservationCategory.CHECKBOX_SELECTOR.getDescription());
                } else {
                    log.info("{}가 이미 선택되어 있습니다.", ReservationCategory.CHECKBOX_SELECTOR.getDescription());
                }
            } catch (NoSuchElementException e) {
                log.warn("{} 요소를 찾을 수 없습니다. 페이지 구조를 확인하세요.", ReservationCategory.CHECKBOX_SELECTOR.getDescription());
                return BaseResponse.of(HttpStatus.INTERNAL_SERVER_ERROR, "전체 체크박스 요소를 찾을 수 없습니다.", null);
            }

            // "선택 건물 예약 현황 조회" 버튼 클릭
            try {
                WebElement searchButton = driver.findElement(By.cssSelector(ReservationCategory.SEARCH_BUTTON_SELECTOR.getValue()));
                searchButton.click();
                log.info("{}를 클릭했습니다.", ReservationCategory.SEARCH_BUTTON_SELECTOR.getDescription());
            } catch (NoSuchElementException e) {
                log.warn("{} 요소를 찾을 수 없습니다. 페이지 구조를 확인하세요.", ReservationCategory.SEARCH_BUTTON_SELECTOR.getDescription());
                return BaseResponse.of(HttpStatus.INTERNAL_SERVER_ERROR, "조회 버튼 요소를 찾을 수 없습니다.", null);
            }

            // 5초 대기 후 타임 테이블 로드 확인
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            boolean isTimeTableLoaded = wait.until(driver1 -> {
                List<WebElement> timeTables = driver1.findElements(By.cssSelector(ReservationCategory.TIME_TABLE_SELECTOR.getValue()));
                return !timeTables.isEmpty();
            });

            if (isTimeTableLoaded) {
                log.info("타임 테이블이 성공적으로 로드되었습니다.");
                String pageSource = driver.getPageSource();

                // 예약 상태 파싱 (옵션)
                List<TimeTable> timeTables = parseReservationStatus(pageSource);


                return BaseResponse.of(HttpStatus.OK, "타임 테이블이 성공적으로 로드되었습니다.", null);
            } else {
                log.warn("타임 테이블 로드 실패");
                return BaseResponse.of(HttpStatus.INTERNAL_SERVER_ERROR, "타임 테이블 로드 중 오류가 발생했습니다.", null);
            }
        } catch (TimeoutException e) {
            log.error("타임 테이블 로드 시간 초과: {}", e.getMessage());
            return BaseResponse.of(HttpStatus.REQUEST_TIMEOUT, "타임 테이블 로드 중 시간 초과가 발생했습니다.", null);
        } catch (Exception e) {
            log.error("예약 데이터 크롤링 중 오류 발생: {}", e.getMessage(), e);
            return BaseResponse.of(HttpStatus.INTERNAL_SERVER_ERROR, "예약 데이터 크롤링 중 오류가 발생했습니다.", null);
        }
    }

    public BaseResponse<String> selectDateAndFetchTable(String selectedDate) {
        if (driver == null || !isLoggedIn) {
            return BaseResponse.of(HttpStatus.FORBIDDEN, "로그인이 필요합니다. 먼저 /crawl을 호출하세요.", null);
        }

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

            // 날짜 선택
            WebElement dateSelect = driver.findElement(By.id("ymdSelect"));
            List<WebElement> dateOptions = dateSelect.findElements(By.tagName("option"));
            boolean dateFound = false;

            for (WebElement option : dateOptions) {
                if (option.getAttribute("value").equals(selectedDate)) {
                    option.click();
                    log.info("날짜 {} 선택 완료", selectedDate);
                    dateFound = true;
                    break;
                }
            }

            if (!dateFound) {
                return BaseResponse.of(HttpStatus.BAD_REQUEST, "해당 날짜를 찾을 수 없습니다.", null);
            }

            // 타임 테이블 로드 확인
            boolean isTableLoaded = wait.until(driver1 -> {
                List<WebElement> timeTables = driver1.findElements(By.cssSelector(".time-table"));
                return !timeTables.isEmpty();
            });

            if (isTableLoaded) {
                log.info("날짜 {}: 타임 테이블 로드 성공", selectedDate);
                String pageSource = driver.getPageSource();
                List<TimeTable> timeTables = parseReservationStatus(pageSource);
                return BaseResponse.of(HttpStatus.OK, "타임 테이블 데이터", null);
            } else {
                log.warn("날짜 {}: 타임 테이블 로드 실패", selectedDate);
                return BaseResponse.of(HttpStatus.INTERNAL_SERVER_ERROR, "타임 테이블 로드 실패", null);
            }

        } catch (Exception e) {
            log.error("날짜 선택 및 타임 테이블 로드 중 오류 발생: {}", e.getMessage(), e);
            return BaseResponse.of(HttpStatus.INTERNAL_SERVER_ERROR, "오류 발생", null);
        }
    }

    private WebDriver setupWebDriver() {

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized"); // 브라우저 창 최대화
        options.addArguments("--disable-notifications"); // 알림 비활성화
        options.addArguments("--headless"); // (필요 시) 헤드리스 모드

        // ChromeDriver 생성
        WebDriver driver = new ChromeDriver(options);
        return driver;
    }

    public List<TimeTable> parseReservationStatus(String html) {
        List<TimeTable> timeTables = new ArrayList<>();

        try {
            Document document = Jsoup.parse(html);

            // 타임 테이블 데이터 추출
            Elements timeTableElements = document.select(ReservationCategory.TIME_TABLE_SELECTOR.getValue());

            for (Element table : timeTableElements) {
                TimeTable timeTable = new TimeTable();

                // 타임 테이블 제목 추출
                Element titleElement = table.previousElementSibling(); // 바로 위의 sibling에서 title 가져오기
                String title = (titleElement != null && titleElement.hasClass("time-table__title"))
                        ? titleElement.text()
                        : "제목 없음";
                timeTable.setTitle(title);

                // 호실 정보 추출
                Elements roomElements = table.select("div.class ol li");
                List<String> rooms = roomElements.stream()
                        .map(Element::text)
                        .filter(room -> !room.equals("시간"))
                        .toList();

                List<RoomReservation> roomReservations = new ArrayList<>();
                for (String room : rooms) {
                    roomReservations.add(new RoomReservation(room, new ArrayList<>()));
                }

                // 시간별 예약 상태 추출
                Elements hourElements = table.select("div.time ol.hour > li");
                for (Element hour : hourElements) {
                    String time = Optional.ofNullable(hour.selectFirst("strong"))
                            .map(Element::text)
                            .orElse("시간 없음");

                    Elements roomStatusElements = hour.select("ul > li");
                    for (int i = 0; i < rooms.size(); i++) {
                        String status = "정보 없음";

                        if (i < roomStatusElements.size()) {
                            Element statusElement = roomStatusElements.get(i);
                            if (statusElement.hasClass("nochoice")) {
                                status = "예약 불가";
                            } else if (statusElement.hasClass("enable")) {
                                status = "예약 가능";
                            } else {
                                status = "예약됨";
                            }
                        }

                        RoomReservation reservation = roomReservations.get(i);
                        reservation.getReservations().add(time + " - " + status);
                    }
                }

                timeTable.setRoomReservations(roomReservations);
                timeTables.add(timeTable);
            }

        } catch (Exception e) {
            log.error("예약 상태 파싱 중 오류 발생: {}", e.getMessage(), e);
        }

        // 최종 데이터 로그 출력
        log.info("Parsed Time Tables:");
        for (TimeTable timeTable : timeTables) {
            log.info("Title: {}", timeTable.getTitle());
            for (RoomReservation room : timeTable.getRoomReservations()) {
                log.info("  Room: {}", room.getRoomName());
                for (String reservation : room.getReservations()) {
                    log.info("    Reservation: {}", reservation);
                }
            }
        }

        return timeTables;
    }
}
