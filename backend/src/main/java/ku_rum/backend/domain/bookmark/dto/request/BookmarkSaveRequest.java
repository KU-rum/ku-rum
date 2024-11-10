package ku_rum.backend.domain.bookmark.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BookmarkSaveRequest {

    @NotNull(message = "userId는 필수 입력값입니다.")
    private Long userId;

    @NotNull(message = "url은 필수 입력값입니다.")
    private String url;

    @Builder
    public BookmarkSaveRequest(Long userId, String url) {
        this.userId = userId;
        this.url = url;
    }

}
