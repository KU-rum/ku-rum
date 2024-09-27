package ku_rum.backend.domain.building.domain;

import jakarta.persistence.*;
import ku_rum.backend.global.type.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Building extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String abbreviation;

    @Column(nullable = false, precision = 16, scale = 13)
    private BigDecimal latitude;

    @Column(nullable = false, precision = 16, scale = 13)
    private BigDecimal longitude;

    @Builder
    private Building(String name, String abbreviation, BigDecimal latitude, BigDecimal longitude) {
        this.name = name;
        this.abbreviation = abbreviation;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public static Building of(String name, String abbreviation, BigDecimal latitude, BigDecimal longitude) {
        return Building.builder()
                .name(name)
                .abbreviation(abbreviation)
                .latitude(latitude)
                .longitude(longitude)
                .build();
    }
}
