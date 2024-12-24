package ku_rum.backend.domain.building.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("빌딩 데이터 생성 테스트")
class BuildingTest {
    @Nested
    @DisplayName("빌딩 생성 시 좌표값 저장")
    class Building_make{
        
        @Test
        @DisplayName("층값(floor) 없을 때")
        void registeredBuildingWithCoordinateWithoutFloor() {
            //given
            BigDecimal latitude = BigDecimal.valueOf(64.3423423);
            BigDecimal longitude = BigDecimal.valueOf(342.2343434);

            //when
            Building building = Building.of("신공학관", "신공", latitude, longitude);

            //then
            assertThat(building.getLatitude()).isEqualTo(latitude);
            assertThat(building.getLongitude()).isEqualTo(longitude);
        }

        @Test
        @DisplayName("층값(floor) 있을 때")
        void registeredBuildingWithCoordinateWithFloor() {
            //given
            BigDecimal latitude = BigDecimal.valueOf(64.3423423);
            BigDecimal longitude = BigDecimal.valueOf(342.2343434);

            //when
            Building building = Building.of("신공학관", "신공", (long)3, latitude, longitude);

            //then
            assertThat(building.getLatitude()).isEqualTo(latitude);
            assertThat(building.getLongitude()).isEqualTo(longitude);
            assertThat(building.getFloor()).isEqualTo(3);
        }
    }

}