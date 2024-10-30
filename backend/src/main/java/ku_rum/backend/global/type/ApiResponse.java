package ku_rum.backend.global.type;

import lombok.Getter;

import java.util.UUID;

@Getter
public abstract class ApiResponse {
  protected int code;
  protected String message;
  protected String requestId;

  public ApiResponse(int code, String message) {
    this.code = code;
    this.message = message;
    this.requestId = generateRequestId();
  }

  public ApiResponse(int code) {
    this.code = code;
    this.message = "요청에 성공하였습니다.";
    this.requestId = generateRequestId();
  }
  private String generateRequestId(){
    return "requestId-" + System.currentTimeMillis() + "-" + UUID.randomUUID().toString();
  }
}
