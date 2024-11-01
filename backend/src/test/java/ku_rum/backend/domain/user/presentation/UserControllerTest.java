package ku_rum.backend.domain.user.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import ku_rum.backend.domain.user.application.UserService;
import ku_rum.backend.domain.user.dto.request.UserSaveRequest;
import ku_rum.backend.domain.user.dto.request.WeinLoginRequest;
import ku_rum.backend.domain.user.dto.response.WeinLoginResponse;
import ku_rum.backend.global.response.BaseResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @DisplayName("신규 유저를 생성한다.")
    @Test
    void createUser() throws Exception {
        //given
        UserSaveRequest request = UserSaveRequest.builder()
                .email("kmw106933")
                .password("password123")
                .department("컴퓨터공학부")
                .nickname("미미미누")
                .studentId("202112322")
                .build();

        // when then
        mockMvc.perform(post("/api/v1/users")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("OK"));
    }

    @DisplayName("위인전 로그인 성공 테스트")
    @Test
    void loginToWeinSuccess() throws Exception {
        // Given
        WeinLoginRequest weinLoginRequest = new WeinLoginRequest();
        weinLoginRequest.setUserId("testUser");
        weinLoginRequest.setPassword("testPassword");

        WeinLoginResponse weinLoginResponse = new WeinLoginResponse(true, "Wein login successful");
        Mockito.when(userService.loginToWein(any(WeinLoginRequest.class)))
                .thenReturn(BaseResponse.ok(weinLoginResponse));

        // When & Then
        mockMvc.perform(post("/api/v1/users/weinlogin")
                        .content(objectMapper.writeValueAsString(weinLoginRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.success").value(true))
                .andExpect(jsonPath("$.data.message").value("Wein login successful"));
    }

    @DisplayName("위인전 로그인 실패 테스트")
    @Test
    void loginToWeinFailure() throws Exception {
        // Given
        WeinLoginRequest weinLoginRequest = new WeinLoginRequest();
        weinLoginRequest.setUserId("testUser");
        weinLoginRequest.setPassword("wrongPassword");

        WeinLoginResponse weinLoginResponse = new WeinLoginResponse(false, "Invalid Wein credentials");
        Mockito.when(userService.loginToWein(any(WeinLoginRequest.class)))
                .thenReturn(BaseResponse.of(HttpStatus.UNAUTHORIZED, weinLoginResponse));

        // When & Then
        mockMvc.perform(post("/api/v1/users/weinlogin")
                        .content(objectMapper.writeValueAsString(weinLoginRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.data.success").value(false))
                .andExpect(jsonPath("$.data.message").value("Invalid Wein credentials"));
    }


}