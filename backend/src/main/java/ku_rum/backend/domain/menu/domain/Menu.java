package ku_rum.backend.domain.menu.domain;

import jakarta.persistence.*;
import ku_rum.backend.domain.category.domain.Category;
import ku_rum.backend.global.type.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Menu extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30)
    private String name;

    private Long price;

    @Column(length = 300)
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Builder
    private Menu(String name, Long price, String imageUrl, Category category) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.category = category;
    }

    public static Menu of(String name, Long price, String imageUrl, Category category) {
        return Menu.builder()
                .name(name)
                .price(price)
                .imageUrl(imageUrl)
                .category(category)
                .build();
    }
}
