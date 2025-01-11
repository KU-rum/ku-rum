package ku_rum.backend.domain.reservation.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoomReservation {
    private String roomName;
    private List<String> reservations;
}
