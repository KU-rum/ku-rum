package ku_rum.backend.global.exception.category;

import ku_rum.backend.global.response.status.BaseExceptionResponseStatus;
import lombok.Getter;

@Getter
public class CategoryNotProvidingDetail extends RuntimeException {
  private BaseExceptionResponseStatus status;
  public CategoryNotProvidingDetail(BaseExceptionResponseStatus status) {
    this.status = status;
  }
}