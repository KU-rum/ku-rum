package ku_rum.backend.domain.bookmark.domain;

import jakarta.persistence.*;
import ku_rum.backend.domain.notice.domain.Notice;
import ku_rum.backend.domain.user.domain.User;
import ku_rum.backend.global.type.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Bookmark extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notice_url", referencedColumnName = "url", nullable = false)
    private Notice notice;

    @Builder
    private Bookmark(User user, Notice notice) {
        this.user = user;
        this.notice = notice;
    }

    public static Bookmark of(User user, Notice notice) {
        return Bookmark.builder()
                .user(user)
                .notice(notice)
                .build();
    }
}
