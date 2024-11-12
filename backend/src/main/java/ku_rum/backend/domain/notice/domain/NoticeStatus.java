package ku_rum.backend.domain.notice.domain;

public enum NoticeStatus {
    IMPORTANT,
    GENERAL;

    public boolean isImportant() {
        return this == IMPORTANT;
    }
}
