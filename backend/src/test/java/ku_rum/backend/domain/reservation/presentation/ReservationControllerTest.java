package ku_rum.backend.domain.reservation.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import ku_rum.backend.domain.reservation.application.ReservationService;
import ku_rum.backend.domain.reservation.dto.request.SelectDateRequest;
import ku_rum.backend.domain.reservation.dto.request.WeinLoginRequest;
import ku_rum.backend.global.response.BaseResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(ReservationController.class)
class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservationService reservationService;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("크롤링 시작 요청 성공")
    @Test
    void crawlReservationPageSuccess() throws Exception {
        // Mock 데이터 설정
        WeinLoginRequest loginRequest = new WeinLoginRequest("mockUser", "mockPassword");

        when(reservationService.crawlReservationPage(any(WeinLoginRequest.class)))
                .thenReturn(BaseResponse.of(HttpStatus.OK, "로그인 및 크롤링 작업 성공"));

        // 요청 및 검증
        mockMvc.perform(post("/api/v1/reservations/crawl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("OK"));
    }

    @DisplayName("날짜 선택 요청 성공")
    @Test
    void selectDateSuccess() throws Exception {
        // Mock 데이터 설정
        SelectDateRequest selectDateRequest = new SelectDateRequest("2024-11-25");

        when(reservationService.selectDateAndFetchTable(anyString()))
                .thenReturn(BaseResponse.of(HttpStatus.OK, "타임 테이블 데이터"));

        // 요청 및 검증
        mockMvc.perform(post("/api/v1/reservations/select_date")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(selectDateRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("OK"));
    }


}