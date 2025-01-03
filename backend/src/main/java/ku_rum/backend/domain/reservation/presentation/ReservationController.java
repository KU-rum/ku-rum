package ku_rum.backend.domain.reservation.presentation;

import jakarta.validation.Valid;
import ku_rum.backend.domain.reservation.application.ReservationService;
import ku_rum.backend.domain.reservation.dto.request.SelectDateRequest;
import ku_rum.backend.domain.reservation.dto.request.WeinLoginRequest;
import ku_rum.backend.global.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reservations")
@RequiredArgsConstructor
@Validated
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping("/login")
    public BaseResponse<String> getLoginUrl() {
        return reservationService.getLoginUrl();
    }

    @PostMapping("/crawl")
    public BaseResponse<String> crawlReservationPage(@RequestBody @Valid WeinLoginRequest weinLoginRequest) {
        return reservationService.crawlReservationPage(weinLoginRequest);
    }

    @PostMapping("/select_date")
    public BaseResponse<String> selectDate(@RequestBody @Valid SelectDateRequest request) {
        return reservationService.selectDateAndFetchTable(request.getSelectedDate());
    }



}
