package ku_rum.backend.global.dataInit;

import ku_rum.backend.domain.building.domain.Building;
import ku_rum.backend.domain.building.domain.BuildingAbbrev;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;

@Component
public class BuildingInitializer {
  public static ArrayList<Building> initialize() {
    ArrayList<Building> buildings = new ArrayList<>();

    buildings.add(
            Building.of(
                    BuildingAbbrev.경영.getOriginalName(),
                    2L,
                    BuildingAbbrev.경영.name(),
                    BigDecimal.valueOf(37.544419),
                    BigDecimal.valueOf(127.076370))
    );
    buildings.add(
            Building.of(
                    BuildingAbbrev.상허관.getOriginalName(),
                    3L,
                    BuildingAbbrev.상허관.name(),
                    BigDecimal.valueOf(37.544292),
                    BigDecimal.valueOf(127.076212))
    );
    buildings.add(
            Building.of(
                    BuildingAbbrev.사.getOriginalName(),
                    4L,
                    BuildingAbbrev.사.name(),
                    BigDecimal.valueOf(37.544073),
                    BigDecimal.valueOf(127.073994))
    );
    buildings.add(
            Building.of(
                    BuildingAbbrev.예.getOriginalName(),
                    5L,
                    BuildingAbbrev.예.name(),
                    BigDecimal.valueOf(37.544419),
                    BigDecimal.valueOf(127.073994))
    );
    buildings.add(
            Building.of(
                    BuildingAbbrev.언어원.getOriginalName(),
                    6L,
                    BuildingAbbrev.언어원.name(),
                    BigDecimal.valueOf(37.542497),
                    BigDecimal.valueOf(127.074584))
    );
    buildings.add(
            Building.of(
                    BuildingAbbrev.종강.getOriginalName(),
                    8L,
                    BuildingAbbrev.종강.name(),
                    BigDecimal.valueOf(37.542260),
                    BigDecimal.valueOf(127.073637))
    );
    buildings.add(
            Building.of(
                    BuildingAbbrev.의.getOriginalName(),
                    10L,
                    BuildingAbbrev.의.name(),
                    BigDecimal.valueOf(37.541443),
                    BigDecimal.valueOf(127.072313))
    );
    buildings.add(
            Building.of(
                    BuildingAbbrev.생.getOriginalName(),
                    11L,
                    BuildingAbbrev.생.name(),
                    BigDecimal.valueOf(37.541004),
                    BigDecimal.valueOf(127.074197))
    );
    buildings.add(
            Building.of(
                    BuildingAbbrev.동.getOriginalName(),
                    12L,
                    BuildingAbbrev.동.name(),
                    BigDecimal.valueOf(37.540242),
                    BigDecimal.valueOf(127.074289))
    );
    buildings.add(
            Building.of(
                    BuildingAbbrev.산학.getOriginalName(),
                    14L,
                    BuildingAbbrev.산학.name(),
                    BigDecimal.valueOf(37.539701),
                    BigDecimal.valueOf(127.073102))
    );
    buildings.add(
            Building.of(
                    BuildingAbbrev.수.getOriginalName(),
                    15L,
                    BuildingAbbrev.수.name(),
                    BigDecimal.valueOf(37.539294),
                    BigDecimal.valueOf(127.074676))
    );
    buildings.add(
            Building.of(
                    BuildingAbbrev.새.getOriginalName(),
                    16L,
                    BuildingAbbrev.새.name(),
                    BigDecimal.valueOf(37.543549),
                    BigDecimal.valueOf(127.077408))
    );
    buildings.add(
            Building.of(
                    BuildingAbbrev.건.getOriginalName(),
                    17L,
                    BuildingAbbrev.건.name(),
                    BigDecimal.valueOf(37.543391),
                    BigDecimal.valueOf(127.078521))
    );
    buildings.add(
            Building.of(
                    BuildingAbbrev.부.getOriginalName(),
                    18L,
                    BuildingAbbrev.부.name(),
                    BigDecimal.valueOf(37.543443),
                    BigDecimal.valueOf(127.078225))
    );
    buildings.add(
            Building.of(
                    BuildingAbbrev.문.getOriginalName(),
                    19L,
                    BuildingAbbrev.문.name(),
                    BigDecimal.valueOf(37.542422),
                    BigDecimal.valueOf(127.078929))
    );
    buildings.add(
            Building.of(
                    BuildingAbbrev.공.getOriginalName(),
                    21L,
                    BuildingAbbrev.공.name(),
                    BigDecimal.valueOf(37.541822),
                    BigDecimal.valueOf(127.078845))
    );
    buildings.add(
            Building.of(
                    BuildingAbbrev.신공.getOriginalName(),
                    22L,
                    BuildingAbbrev.신공.name(),
                    BigDecimal.valueOf(37.540579),
                    BigDecimal.valueOf(127.079472))
    );
    buildings.add(
            Building.of(
                    BuildingAbbrev.이.getOriginalName(),
                    23L,
                    BuildingAbbrev.이.name(),
                    BigDecimal.valueOf(37.541646),
                    BigDecimal.valueOf(127.080408))
    );
    buildings.add(
            Building.of(
                    BuildingAbbrev.창.getOriginalName(),
                    24L,
                    BuildingAbbrev.창.name(),
                    BigDecimal.valueOf(37.540757),
                    BigDecimal.valueOf(127.079368))
    );
    return buildings;
  }
}