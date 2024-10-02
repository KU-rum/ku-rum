package ku_rum.backend.domain.notice.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NoticeCategory {
    AFFAIR("학사"),
    SCHOLARSHIP("장학"),
    STARTUP("취창업"),
    INTERNATIONAL("국제");

    private final String text;
}
