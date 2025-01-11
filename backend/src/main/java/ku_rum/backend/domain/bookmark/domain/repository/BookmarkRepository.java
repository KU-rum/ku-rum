package ku_rum.backend.domain.bookmark.domain.repository;

import ku_rum.backend.domain.bookmark.domain.Bookmark;
import ku_rum.backend.domain.notice.domain.Notice;
import ku_rum.backend.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    List<Bookmark> findByUser(User user);
    Optional<Bookmark> findByUserAndNotice(User user, Notice notice);
}
