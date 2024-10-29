package ku_rum.backend.domain.category.domain;

import jakarta.persistence.*;
import ku_rum.backend.global.type.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Builder
    private Category(String name) {
        this.name = name;
    }

    public static Category of(String name) {
        return Category.builder()
                .name(name)
                .build();
    }
}
