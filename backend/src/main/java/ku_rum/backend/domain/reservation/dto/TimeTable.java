package ku_rum.backend.domain.reservation.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TimeTable {
    private String title;
    private List<RoomReservation> roomReservations;
}
