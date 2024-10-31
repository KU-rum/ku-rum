package ku_rum.backend.domain.bookmark.domain;

import ku_rum.backend.domain.building.domain.Building;
import ku_rum.backend.domain.department.domain.Department;
import ku_rum.backend.domain.notice.domain.Notice;
import ku_rum.backend.domain.user.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class BookmarkTest {

    @DisplayName("북마크 생성시, 유저와 공지사항을 넣어준다.")
    @Test
    void saveBookmarkWithUserAndNotice() {
        //given
        User user = createUser("사용자1", "202112322");
        Notice notice = Notice.of("가나다라", "naver.com/abc123", user);

        //when
        Bookmark bookmark = Bookmark.of(user, notice);

        //then
        assertThat(bookmark.getUser()).isEqualTo(user);
        assertThat(bookmark.getNotice()).isEqualTo(notice);
    }

    private User createUser(String username, String studentID) {
        Building building = createBuilding();
        Department department = Department.of("컴퓨터공학부", building);
        return User.of(username, studentID, "password123", "202112322", department);
    }

    private Building createBuilding() {
        BigDecimal latitude = BigDecimal.valueOf(64.3423423);
        BigDecimal longitude = BigDecimal.valueOf(342.2343434);
        return Building.of("신공학관", "신공", latitude, longitude);
    }
}