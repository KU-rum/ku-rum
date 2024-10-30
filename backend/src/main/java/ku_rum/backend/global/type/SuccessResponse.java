package ku_rum.backend.global.type;

import lombok.Getter;

@Getter
public class SuccessResponse<T> extends  ApiResponse {
  private final T data; //실제 응답 데이터를 담을 필드

  public SuccessResponse(T data) {
    super(200, "요청에 성공하였습니다.");
    this.data = data;
  }

  //커스텀 메시지가 필요한 경우를 위한 생성자
  public SuccessResponse(T data, String message) {
    super(200, message);
    this.data = data;
  }
}
