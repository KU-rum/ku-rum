package ku_rum.backend.domain.building.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

class BuildingTest {

    @DisplayName("빌딩 생성 시 좌표값을 온전하게 저장할 수 있다.")
    @Test
    void registeredBuildingWithCoordinate() {
        //given
        BigDecimal latitude = BigDecimal.valueOf(64.3423423);
        BigDecimal longitude = BigDecimal.valueOf(342.2343434);

        //when
        Building building = Building.of("신공학관", "신공", latitude, longitude);

        //then
        assertThat(building.getLatitude()).isEqualTo(latitude);
        assertThat(building.getLongitude()).isEqualTo(longitude);
    }
}