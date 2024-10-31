package ku_rum.backend.domain.department.domain;

import jakarta.persistence.*;
import ku_rum.backend.domain.building.domain.Building;
import ku_rum.backend.global.type.BaseEntity;
import lombok.*;

@Getter
@Entity
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Department extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buliding_id", nullable = false)
    private Building building;

    @Builder
    private Department(String name, Building building) {
        this.name = name;
        this.building = building;
    }

    public static Department of(String name, Building building) {
        return Department.builder()
                .name(name)
                .building(building)
                .build();
    }
}
