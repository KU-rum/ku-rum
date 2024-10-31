package ku_rum.backend.global.response.status;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum BaseExceptionResponseStatus implements ResponseStatus{

    /**
     * 100: 요청 성공 (OK)
     */
    SUCCESS(100, HttpStatus.OK, "요청에 성공하였습니다."),

    /**
     * 200: Request 오류 (BAD_REQUEST)
     */
    BAD_REQUEST(200, HttpStatus.BAD_REQUEST, "유효하지 않은 요청입니다."),
    URL_NOT_FOUND(201, HttpStatus.BAD_REQUEST, "유효하지 않은 URL 입니다."),

    /**
     * 300: User 오류
     */
    DUPLICATE_EMAIL(300, HttpStatus.BAD_REQUEST, "이미 존재하는 이메일입니다."),
    DUPLICATE_STUDENT_ID(301, HttpStatus.BAD_REQUEST, "이미 존재하는 학번입니다."),

    /**
     * 400: Department 오류
     */
    NO_SUCH_DEPARTMENT(400, HttpStatus.BAD_REQUEST, "존재하지 않는 학과명입니다."),

    /**
     * 500: Building 오류
     */
    Buildings_NOT_FOUND(500, HttpStatus.NOT_FOUND, "건물 정보를 찾을 수 없습니다."),
    NO_BUILDING_REGISTERED(501, HttpStatus.OK, "등록된 건물이 없습니다.");


    private final int code;
    private final HttpStatus status;
    private final String message;

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
