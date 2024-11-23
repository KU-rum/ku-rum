package ku_rum.backend.domain.reservation.application;

import static org.junit.jupiter.api.Assertions.*;

import ku_rum.backend.domain.reservation.dto.RoomReservation;
import ku_rum.backend.domain.reservation.dto.TimeTable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;


class ReservationServiceTest {

    private ReservationService reservationService;

    @BeforeEach
    void setUp() {
        reservationService = new ReservationService();
    }

    @DisplayName("parseReservationStatus 메서드 테스트 - 타임 테이블 데이터 파싱")
    @Test
    void parseReservationStatusSuccess() {
        // 테스트용 HTML 데이터
        String html = """
            <p class="time-table__title">공학관 K-Cube</p>
            <div class="time-table">
                <div class="class">
                    <ol>
                        <li class="first">시간</li>
                        <li>1호실(6인실)</li>
                        <li>2호실(6인실)</li>
                    </ol>
                </div>
                <div class="time">
                    <ol class="hour">
                        <li class="first">
                            <strong>09:00</strong>
                            <ul>
                                <li class="enable"></li>
                                <li class="nochoice"></li>
                            </ul>
                            <ul>
                                <li class="enable"></li>
                                <li class="enable"></li>
                            </ul>
                        </li>
                        <li>
                            <strong>10:00</strong>
                            <ul>
                                <li class="nochoice"></li>
                                <li class="enable"></li>
                            </ul>
                            <ul>
                                <li class="nochoice"></li>
                                <li class="nochoice"></li>
                            </ul>
                        </li>
                    </ol>
                </div>
            </div>
        """;

        // 메소드 호출
        List<TimeTable> timeTables = reservationService.parseReservationStatus(html);

        // 검증
        assertNotNull(timeTables, "TimeTables should not be null");
        assertEquals(1, timeTables.size(), "There should be exactly 1 TimeTable");

        TimeTable timeTable = timeTables.get(0);
        assertEquals("공학관 K-Cube", timeTable.getTitle(), "The title should match");

        List<RoomReservation> roomReservations = timeTable.getRoomReservations();
        assertNotNull(roomReservations, "RoomReservations should not be null");
        assertEquals(2, roomReservations.size(), "There should be exactly 2 RoomReservations");

        RoomReservation room1 = roomReservations.get(0);
        assertEquals("1호실(6인실)", room1.getRoomName(), "The first room name should match");
        assertEquals(2, room1.getReservations().size(), "The first room should have 2 reservations");
        assertEquals("09:00 - 예약 가능", room1.getReservations().get(0), "The first reservation of the first room should match");
        assertEquals("10:00 - 예약 불가", room1.getReservations().get(1), "The second reservation of the first room should match");

        RoomReservation room2 = roomReservations.get(1);
        assertEquals("2호실(6인실)", room2.getRoomName(), "The second room name should match");
        assertEquals(2, room2.getReservations().size(), "The second room should have 2 reservations");
        assertEquals("09:00 - 예약 불가", room2.getReservations().get(0), "The first reservation of the second room should match");
        assertEquals("10:00 - 예약 가능", room2.getReservations().get(1), "The second reservation of the second room should match");
    }

}