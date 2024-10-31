package ku_rum.backend.domain.user.application;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import ku_rum.backend.domain.building.repository.BuildingRepository;
import ku_rum.backend.domain.building.domain.Building;
import ku_rum.backend.domain.department.domain.Department;
import ku_rum.backend.domain.department.domain.repository.DepartmentRepository;
import ku_rum.backend.domain.user.domain.repository.UserRepository;
import ku_rum.backend.domain.user.dto.request.UserSaveRequest;
import ku_rum.backend.domain.user.dto.response.UserSaveResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.PlatformTransactionManager;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private BuildingRepository buildingRepository;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private EntityManager entityManager;

    @AfterEach
    void tearDown() {
        userRepository.deleteAllInBatch();
        departmentRepository.deleteAllInBatch();
        buildingRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("회원을 올바르게 저장을 요청하면 저장한다.")
    void saveMember() {

        //given
        Building building = Building.of("신공학관", "신공", BigDecimal.valueOf(64.3423423), BigDecimal.valueOf(64.3423423));
        Building saveBuliding = buildingRepository.save(building);

        Department department = Department.of("컴퓨터공학부1", saveBuliding);
        Department save = departmentRepository.save(department);

        save.setName("컴퓨터공학부");

        UserSaveRequest request = UserSaveRequest.builder()
                .email("kmw106933")
                .password("password123")
                .nickname("미미미누")
                .studentId("202112322")
                .department("컴퓨터공학부")
                .build();
        //when
        UserSaveResponse userSaveResponse = userService.saveUser(request);

        //then
        assertThat(userSaveResponse.getId()).isEqualTo(1L);
    }

}