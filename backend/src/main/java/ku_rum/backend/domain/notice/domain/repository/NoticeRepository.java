package ku_rum.backend.domain.notice.domain.repository;

import ku_rum.backend.domain.notice.domain.Notice;
import ku_rum.backend.domain.notice.domain.NoticeCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {
    List<Notice> findByNoticeCategory(NoticeCategory noticeCategory);
}
