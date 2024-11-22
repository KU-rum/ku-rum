package ku_rum.backend.domain.bookmark.application;

import ku_rum.backend.domain.bookmark.domain.Bookmark;
import ku_rum.backend.domain.bookmark.domain.repository.BookmarkRepository;
import ku_rum.backend.domain.bookmark.dto.request.BookmarkSaveRequest;
import ku_rum.backend.domain.notice.domain.Notice;
import ku_rum.backend.domain.notice.domain.repository.NoticeRepository;
import ku_rum.backend.domain.notice.dto.response.NoticeSimpleResponse;
import ku_rum.backend.domain.user.domain.User;
import ku_rum.backend.domain.user.domain.repository.UserRepository;
import ku_rum.backend.global.exception.notice.NoSuchNoticeException;
import ku_rum.backend.global.exception.user.NoSuchUserException;
import ku_rum.backend.global.response.status.BaseExceptionResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ku_rum.backend.global.response.status.BaseExceptionResponseStatus.NO_SUCH_USER;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final UserRepository userRepository;
    private final NoticeRepository noticeRepository;

    @Transactional
    public void addBookmark(BookmarkSaveRequest bookmarkRequest) {

        User user = userRepository.findById(bookmarkRequest.getUserId())
                .orElseThrow(() -> new NoSuchUserException(NO_SUCH_USER));

        Notice notice = noticeRepository.findByUrl(bookmarkRequest.getUrl())
                .orElseThrow(() -> new NoSuchNoticeException(BaseExceptionResponseStatus.NO_SUCH_NOTICE));

        if (bookmarkRepository.findByUserAndNotice(user, notice).isPresent()) {
            throw new IllegalArgumentException("이미 북마크된 공지사항입니다.");
        }

        Bookmark bookmark = Bookmark.builder()
                .user(user)
                .notice(notice)
                .build();
        bookmarkRepository.save(bookmark);
    }

    public List<NoticeSimpleResponse> getBookmarks(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchUserException(NO_SUCH_USER));

        List<Bookmark> bookmarks = bookmarkRepository.findByUser(user);
        return bookmarks.stream()
                .map(bookmark -> new NoticeSimpleResponse(bookmark.getNotice()))
                .collect(Collectors.toList());
    }
}
