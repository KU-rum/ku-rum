package ku_rum.backend.domain.notice.domain;

import jakarta.persistence.*;
import ku_rum.backend.global.type.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "notice")
public class Notice extends BaseEntity {

    @Id
    @Column(length = 1024, nullable = false)
    private String url; // 공지사항 URL을 Primary Key로 사용

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String date; // 공지사항 작성일

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NoticeCategory noticeCategory;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NoticeStatus noticeStatus;

    @Builder
    public Notice(String url, String title, String date, NoticeCategory noticeCategory, NoticeStatus noticeStatus) {
        this.url = url;
        this.title = title;
        this.date = date;
        this.noticeCategory = noticeCategory;
        this.noticeStatus = noticeStatus;
    }

    public static Notice of(String title, String url) {
        return Notice.builder()
                .title(title)
                .url(url)
                .noticeCategory(NoticeCategory.AFFAIR)
                .noticeStatus(NoticeStatus.GENERAL)
                .build();
    }
}
