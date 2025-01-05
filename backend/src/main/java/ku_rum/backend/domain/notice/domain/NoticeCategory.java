package ku_rum.backend.domain.notice.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NoticeCategory {
    AFFAIR("학사", "https://www.konkuk.ac.kr/konkuk/2238/subview.do",
            "#menu2238_obj1168 > div._fnctWrap > form:nth-child(2) > div > table > tbody > tr", "#menu2238_obj1168 > div._fnctWrap > form:nth-child(3) > div > div > a._listNext"),

    SCHOLARSHIP("장학", "https://www.konkuk.ac.kr/konkuk/2239/subview.do",
            "#menu2239_obj1177 > div._fnctWrap > form:nth-child(2) > div > table > tbody > tr", "#menu2239_obj1177 > div._fnctWrap > form:nth-child(3) > div > div > a._listNext"),

    STARTUP("취창업", "https://www.konkuk.ac.kr/konkuk/2240/subview.do",
            "#_combBbs > table > tbody > tr", "#_combBbs > form:nth-child(3) > div > div > a._listNext"),

    INTERNATIONAL("국제", "https://www.konkuk.ac.kr/konkuk/2241/subview.do",
            "#menu2241_obj1187 > div._fnctWrap > form:nth-child(2) > div > table > tbody > tr", "#menu2241_obj1187 > div._fnctWrap > form:nth-child(3) > div > div > a._listNext"),

    STUDENT("학생", "https://www.konkuk.ac.kr/konkuk/2242/subview.do",
            "#menu2242_obj1188 > div._fnctWrap > form:nth-child(2) > div > table > tbody > tr", "#menu2242_obj1188 > div._fnctWrap > form:nth-child(3) > div > div > a._listNext"),

    GENERAL("일반", "https://www.konkuk.ac.kr/konkuk/2244/subview.do",
            "#menu2244_obj1191 > div._fnctWrap > form:nth-child(2) > div > table > tbody > tr", "#menu2244_obj1191 > div._fnctWrap > form:nth-child(3) > div > div > a._listNext"),

    INDUSTRY_ACADEMIC("산학", "https://www.konkuk.ac.kr/konkuk/19329/subview.do",
            "#_combBbs > table > tbody > tr", "#_combBbs > form:nth-child(3) > div > div > a._listNext"),

    EMPLOYMENT("채용", "https://www.konkuk.ac.kr/konkuk/19564/subview.do",
            "#menu19564_obj13644 > div._fnctWrap > form:nth-child(2) > div > table > tbody > tr", "#menu19564_obj13644 > div._fnctWrap > form:nth-child(3) > div > div > a._listNext");


    private final String text;
    private final String url; //각 카테고리별 크롤링할 url
    private final String selector; //공지사항 selector
    private final String nextButtonSelector;
}
