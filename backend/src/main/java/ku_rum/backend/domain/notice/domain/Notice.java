package ku_rum.backend.domain.notice.domain;

import jakarta.persistence.*;
import ku_rum.backend.domain.user.domain.User;
import ku_rum.backend.global.type.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.EnumType.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notice extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 500)
    private String title;

    @Column(nullable = false, length = 1024)
    private String url;

    @Enumerated(EnumType.STRING)
    private NoticeCategory noticeCategory;

    @Enumerated(value = STRING)
    private NoticeStatus noticeStatus;

    @Builder
    private Notice(String title, String url, NoticeCategory noticeCategory, NoticeStatus noticeStatus) {
        this.title = title;
        this.url = url;
        this.noticeCategory = noticeCategory;
        this.noticeStatus = noticeStatus;
    }

    public static Notice of(String title, String url) {
        return Notice.builder()
                .title(title)
                .url(url)
                .noticeStatus(NoticeStatus.GENERAL)
                .build();
    }
}
