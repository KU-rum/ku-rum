package ku_rum.backend.domain.building.domain;

import jakarta.persistence.*;
import ku_rum.backend.global.type.BaseEntity;
import lombok.*;

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
    private Long number;

    private String abbreviation;

    private Long floor = 0L;

    @Column(nullable = false, precision = 16, scale = 13)
    private BigDecimal latitude;

    @Column(nullable = false, precision = 16, scale = 13)
    private BigDecimal longitude;

    @Builder
    private Building(String name, Long number, String abbreviation, Long floor, BigDecimal latitude, BigDecimal longitude) {
        this.name = name;
        this.number = number;
        this.abbreviation = abbreviation;
        this.floor = floor;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Builder
    private Building(String name, Long number, String abbreviation, BigDecimal latitude, BigDecimal longitude) {
        this.name = name;
        this.number = number;
        this.abbreviation = abbreviation;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public static Building of(String name, Long number, String abbreviation, Long floor, BigDecimal latitude, BigDecimal longitude) {
        return Building.builder()
                .name(name)
                .number(number)
                .abbreviation(abbreviation)
                .floor(floor)
                .latitude(latitude)
                .longitude(longitude)
                .build();
    }

    public static Building of(String name, Long number, String abbreviation,BigDecimal latitude, BigDecimal longitude) {
        return Building.builder()
                .name(name)
                .number(number)
                .abbreviation(abbreviation)
                .latitude(latitude)
                .longitude(longitude)
                .build();
    }
}