package ku_rum.backend.domain.department.domain;

import ku_rum.backend.domain.building.domain.Building;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

class DepartmentTest {

    @DisplayName("학과 생성 시 빌딩 정보를 넣어준다.")
    @Test
    void registeredDepartment() {
        //given
        Building building = createBuilding();

        //when
        Department department = Department.of("컴퓨터공학부", building);

        //then
        assertThat(department.getBuilding()).isEqualTo(building);
    }

    @DisplayName("학과 생성 시 학과 이름을 넣어준다.")
    @Test
    void registeredDepartmentWithName() {
        //given
        Building building = createBuilding();
        String Deptname = "컴퓨터공학부";

        //when
        Department department = Department.of(Deptname, building);

        //then
        assertThat(department.getName()).isEqualTo(Deptname);
    }

    private Building createBuilding() {
        BigDecimal latitude = BigDecimal.valueOf(64.3423423);
        BigDecimal longitude = BigDecimal.valueOf(342.2343434);
        return (Building.of("신공학관", "신공", latitude, longitude));
    }
}