package ku_rum.backend.domain.department.domain;

import jakarta.persistence.*;
import ku_rum.backend.domain.building.domain.Building;
import ku_rum.backend.domain.category.domain.Category;
import ku_rum.backend.global.type.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
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
