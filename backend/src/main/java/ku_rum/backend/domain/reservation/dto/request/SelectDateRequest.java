package ku_rum.backend.domain.reservation.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SelectDateRequest {

    @NotBlank(message = "선택된 날짜는 필수입니다.")
    private String selectedDate;
}
