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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 500)
    private String category;

    @Enumerated(EnumType.STRING)
    private NoticeCategory noticeCategory;

    @Enumerated(value = STRING)
    private NoticeStatus noticeStatus;

    @Builder
    private Notice(String title, String url, User user, NoticeStatus noticeStatus) {
        this.title = title;
        this.url = url;
        this.user = user;
        this.noticeStatus = noticeStatus;
    }

    public static Notice of(String title, String url, User user) {
        return Notice.builder()
                .title(title)
                .url(url)
                .user(user)
                .noticeStatus(NoticeStatus.GENERAL)
                .build();
    }
}
