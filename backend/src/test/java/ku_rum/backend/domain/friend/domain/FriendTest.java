package ku_rum.backend.domain.friend.domain;

import ku_rum.backend.domain.building.domain.Building;
import ku_rum.backend.domain.department.domain.Department;
import ku_rum.backend.domain.user.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class FriendTest {

    @DisplayName("친구 생성 시, 팔로우/팔로잉 정보를 넣어준다.")
    @Test
    void registeredFriendWithFromUserAndToUser() {
        //given
        User fromUser = createUser("사용자1", "202112322");
        User toUser = createUser("사용자2", "202123232");

        //when
        Friend friend = Friend.of(fromUser, toUser);

        //then
        assertThat(friend.getFromUser()).isEqualTo(fromUser);
        assertThat(friend.getToUser()).isEqualTo(toUser);
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