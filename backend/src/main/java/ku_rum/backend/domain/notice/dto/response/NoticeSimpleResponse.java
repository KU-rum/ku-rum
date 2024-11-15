package ku_rum.backend.domain.notice.dto.response;

import ku_rum.backend.domain.notice.domain.Notice;
import ku_rum.backend.domain.notice.domain.NoticeStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NoticeSimpleResponse {
    private String url;
    private String title;
    private String date;
    private String category;
    private boolean isImportant; // 중요 공지 여부를 Boolean으로 표시

    public NoticeSimpleResponse(Notice notice) {
        this.url = notice.getUrl();
        this.title = notice.getTitle();
        this.date = notice.getDate();
        this.category = notice.getNoticeCategory().getText();
        this.isImportant = notice.getNoticeStatus().isImportant();
    }
}
