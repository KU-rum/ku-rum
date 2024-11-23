package ku_rum.backend.domain.reservation.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReservationCategory {
    RESERVATION_LOGIN("로그인 페이지", "https://wein.konkuk.ac.kr/common/user/login.do"),
    RESERVATION_PAGE("예약 페이지", "https://wein.konkuk.ac.kr/ptfol/cmnt/cube/findCubeResveStep1.do"),
    CHECKBOX_SELECTOR("전체 체크박스", "#buildAll"),
    SEARCH_BUTTON_SELECTOR("예약 조회 버튼", "#searchBtn"),
    TIME_TABLE_SELECTOR("타임 테이블", "div.time-table"),
    DATE_SELECT_SELECTOR("날짜 선택 드롭다운", "#ymdSelect"),
    ROOM_SELECTOR("호실 목록", "div.class ol li"),
    TIME_SLOT_SELECTOR("시간대 목록", "div.time ol.hour > li");

    private final String description; // 셀렉터나 URL에 대한 설명
    private final String value;      // URL 또는 CSS 셀렉터 값
}
