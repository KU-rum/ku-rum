package ku_rum.backend.global.exception.category;

import ku_rum.backend.global.response.status.BaseExceptionResponseStatus;
import lombok.Getter;

@Getter
public class CategoryNotExist extends RuntimeException {
  private BaseExceptionResponseStatus status;
  public CategoryNotExist(BaseExceptionResponseStatus status) {
    this.status = status;
  }
}
