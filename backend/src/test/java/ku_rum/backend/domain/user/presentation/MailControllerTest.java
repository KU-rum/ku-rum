package ku_rum.backend.domain.user.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import ku_rum.backend.config.RestDocsTestSupport;
import ku_rum.backend.domain.user.application.mail.MailService;
import ku_rum.backend.domain.user.dto.request.mail.MailSendRequest;
import ku_rum.backend.domain.user.dto.request.mail.MailVerificationRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static javax.management.openmbean.SimpleType.STRING;
import static javax.swing.text.html.parser.DTDConstants.NUMBER;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class MailControllerTest extends RestDocsTestSupport {

    @MockBean
    private MailService mailService;

    @DisplayName("이메일 인증 요청을 보낸다.")
    @Test
    @WithMockUser
    void sendMail() throws Exception {
        //given
        MailSendRequest mailSendRequest = new MailSendRequest("kmw10693@konkuk.ac.kr");

        //when then
        mockMvc.perform(post("/api/v1/mails")
                        .content(objectMapper.writeValueAsString(mailSendRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.data").value("메일이 성공적으로 전송되었습니다."))
                .andDo(restDocs.document(
                        requestFields(
                                fieldWithPath("email")
                                        .type(STRING)
                                        .description("건국대학교 웹메일")
                                        .attributes(constraints("이메일의 끝자리는 @konkuk.ac.kr로 끝나야 합니다."))
                        ),
                        responseFields(
                                fieldWithPath("code")
                                        .type(NUMBER)
                                        .description("성공시 반환 코드 (200)"),
                                fieldWithPath("status")
                                        .type(STRING)
                                        .description("성공시 상태 값 (OK)"),
                                fieldWithPath("message")
                                        .type(STRING)
                                        .description("성공 시 메시지 (OK)"),
                                fieldWithPath("data")
                                        .type(STRING)
                                        .description("성공 시 '메일이 성공적으로 전송되었습니다.' 반환합니다.")
                        )));
    }

    @DisplayName("이메일 인증 코드를 검증한다.")
    @Test
    @WithMockUser
    void verificationEmail() throws Exception {
        //given
        MailVerificationRequest mailVerificationRequest = new MailVerificationRequest("kmw10693@konkuk.ac.kr", "1234");

        //when then
        mockMvc.perform(get("/api/v1/mails/mail_verifications")
                        .content(objectMapper.writeValueAsString(mailVerificationRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("OK"))
                .andDo(restDocs.document(
                        requestFields(
                                fieldWithPath("email")
                                        .type(STRING)
                                        .description("건국대학교 웹메일")
                                        .attributes(constraints("이메일의 끝자리는 @konkuk.ac.kr로 끝나야 합니다.")),
                                fieldWithPath("code")
                                        .type(STRING)
                                        .description("건국대학교 웹메일")
                                        .attributes(constraints("사용자가 받은 인증코드를 입력받습니다."))
                        ),
                        responseFields(
                                fieldWithPath("code")
                                        .type(NUMBER)
                                        .description("성공시 반환 코드 (200)"),
                                fieldWithPath("status")
                                        .type(STRING)
                                        .description("올바른 인증코드 시 상태 값 (OK)"),
                                fieldWithPath("message")
                                        .type(STRING)
                                        .description("올바른 인증코드 시 메시지 (OK)")
                        )));
    }
}