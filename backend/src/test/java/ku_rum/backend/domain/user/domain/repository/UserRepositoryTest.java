package ku_rum.backend.domain.user.domain.repository;

import ku_rum.backend.domain.building.domain.Building;
import ku_rum.backend.domain.building.domain.repository.BuildingRepository;
import ku_rum.backend.domain.department.domain.Department;
import ku_rum.backend.domain.department.domain.repository.DepartmentRepository;
import ku_rum.backend.domain.user.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserRepositoryTest {

    private User user;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BuildingRepository buildingRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @BeforeEach
    void setup() {
        Building building = Building.of("신공학관", "신공", BigDecimal.valueOf(23.32), BigDecimal.valueOf(23.32));
        buildingRepository.save(building);

        Department department = Department.of("컴퓨터공학부" , building);
        departmentRepository.save(department);

        user = User.builder()
                .email("kmw106933@naver.com")
                .nickname("미미미누")
                .password("password123")
                .studentId("202112322")
                .department(department)
                .build();
    }

    @Test
    @DisplayName("User를 저장한다.")
    void save() {
        // given when
        User savedUser = userRepository.save(user);

        //then
        assertNotNull(savedUser);
        Assertions.assertThat(savedUser.getId()).isNotNull();
    }

}