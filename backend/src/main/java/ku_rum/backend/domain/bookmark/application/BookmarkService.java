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
import ku_rum.backend.global.response.status.BaseExceptionResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final UserRepository userRepository;
    private final NoticeRepository noticeRepository;

    @Transactional
    public void addBookmark(BookmarkSaveRequest bookmarkRequest) {

        Optional<User> user = userRepository.findById(bookmarkRequest.getUserId()); //예외 처리 필요?? (이미 로그인되어서 온 상태니까..)

        Notice notice = noticeRepository.findByUrl(bookmarkRequest.getUrl())
                .orElseThrow(() -> new NoSuchNoticeException(BaseExceptionResponseStatus.NO_SUCH_NOTICE));

        if (bookmarkRepository.findByUserAndNotice(user.get(), notice).isPresent()) {
            throw new IllegalArgumentException("이미 북마크된 공지사항입니다.");
        }

        Bookmark bookmark = Bookmark.of(user.get(), notice);
        bookmarkRepository.save(bookmark);
    }

    public List<NoticeSimpleResponse> getBookmarks(Long userId) {
        Optional<User> user = userRepository.findById(userId);

        List<Bookmark> bookmarks = bookmarkRepository.findByUser(user.get());
        return bookmarks.stream()
                .map(bookmark -> new NoticeSimpleResponse(bookmark.getNotice()))
                .collect(Collectors.toList());
    }
}
