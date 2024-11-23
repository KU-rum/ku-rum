package ku_rum.backend.domain.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReservationStatus {
    private String time;         // 시간대 (예: "09:00")
    private String roomName;     // 방 이름 (예: "1호실(6인실)")
    private String status;       // 예약 상태 ("예약 가능", "이미 예약됨", "예약 불가")
}