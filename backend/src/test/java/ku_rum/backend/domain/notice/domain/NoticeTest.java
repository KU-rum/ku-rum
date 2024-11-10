package ku_rum.backend.domain.notice.domain;

import ku_rum.backend.domain.building.domain.Building;
import ku_rum.backend.domain.department.domain.Department;
import ku_rum.backend.domain.user.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static ku_rum.backend.domain.notice.domain.NoticeStatus.*;
import static ku_rum.backend.domain.notice.domain.QNotice.notice;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class NoticeTest {

    @DisplayName("공지 생성 시 유저, 학과 정보를 넣어준다.")
    @Test
    void registeredNoticeWithUser() {
        //given
        Building building = createBuilding();
        Department department = getDepartment(building);
        User user = User.of("사용자1", "미미미누", "password123", "202112322", department);

        //when
        Notice notice = Notice.of("가나다라", "naver.com/abc123");

        //then
        assertThat(notice.getTitle()).isEqualTo("가나다라");
        assertThat(notice.getUrl()).isEqualTo("naver.com/abc123");
//        assertThat(notice.getUser()).isEqualTo(user);
    }

    @DisplayName("공지 생성 시 유저, 공지 상태는 일반이다.")
    @Test
    void init() {
        //given
        Building building = createBuilding();
        Department department = getDepartment(building);
        User user = User.of("사용자1", "미미미누", "password123", "202112322", department);

        //when
        Notice notice = Notice.of("가나다라", "naver.com/abc123");

        //then
        assertThat(notice.getNoticeStatus()).isEqualTo(GENERAL);
    }

    private Department getDepartment(Building building) {
        String Deptname = "컴퓨터공학부";
        Department department = Department.of(Deptname, building);
        return department;
    }

    private Building createBuilding() {
        BigDecimal latitude = BigDecimal.valueOf(64.3423423);
        BigDecimal longitude = BigDecimal.valueOf(342.2343434);
        return (Building.of("신공학관", "신공", latitude, longitude));
    }
}