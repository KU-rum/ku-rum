package ku_rum.backend.domain.user.dto.request;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;

import static org.assertj.core.api.Assertions.*;

class UserSaveRequestTest {
    private static Validator validator;

    @BeforeAll
    static void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("회원가입 아이디에 빈 문자열이 들어오는 경우 처리한다.")
    void blankEmail(String email) {
        UserSaveRequest userSaveRequest = new UserSaveRequest(email, "password12", "202112322", "컴퓨터공학부", "미미미누");

        assertThat(validator.validate(userSaveRequest))
                .anyMatch(violation -> violation.getMessage().equals("아이디 입력은 필수입니다. 최소 6자 이상입니다."));
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("회원가입 비밀번호에 빈 문자열이 들어온 경우 처리한다.")
    void blankPassword(String password) {
        UserSaveRequest userSaveRequest = new UserSaveRequest("kmw106933", password, "202112322", "컴퓨터공학부", "미미미누");

        assertThat(validator.validate(userSaveRequest))
                .anyMatch(violation -> violation.getMessage().equals("비밀번호 입력은 필수입니다."));
    }

    @ParameterizedTest
    @CsvSource(value = {"가나다라1234:true", "test1234:false", "abcabcab:true", "123456789:true"}, delimiter = ':')
    @DisplayName("회원가입 비밀번호에 옳지 않은 비밀번호 형식의 문자열이 들어오면 처리한다.")
    void invalidPassword(String password, boolean flag) {
        UserSaveRequest userSaveRequest = new UserSaveRequest("kmw106933", password, "202112322", "컴퓨터공학부", "미미미누");

        assertThat(validator.validate(userSaveRequest).stream()
                .anyMatch(violation -> violation.getMessage().equals("비밀번호는 영어와 숫자를 포함해서 8자 이상 20자 이내로 입력해주세요.")))
                .isEqualTo(flag);
    }

    @ParameterizedTest
    @CsvSource(value = {"202112322:false", "asdafdsdf:true", "212023233:true", "202612322:true"}, delimiter = ':')
    @DisplayName("학번에 옳지 않은 형식의 문자열이 들어오면 처리한다.")
    void invalidUserName(String studentId, boolean flag) {
        UserSaveRequest userSaveRequest = new UserSaveRequest("kmw106933", "password123", studentId, "컴퓨터공학부", "미미미누");

        assertThat(validator.validate(userSaveRequest).stream()
                .anyMatch(violation -> violation.getMessage().equals("학번은 20으로 시작하고, 9자리여야 합니다.")))
                .isEqualTo(flag);
    }

}