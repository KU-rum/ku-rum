package ku_rum.backend.domain.recruitment.domain;

import jakarta.persistence.*;
import ku_rum.backend.global.type.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.C;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "recruitment")
public class Recruitment extends BaseEntity {
    @Id
    @Column(length = 1024, nullable = false)
    private String url; // 공지사항 URL을 Primary Key로 사용

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private String career;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RecruitCategory recruitCategory;

    @Builder
    private Recruitment(String url, String title, String location, String career, RecruitCategory recruitCategory) {
        this.url = url;
        this.title = title;
        this.location = location;
        this.career = career;
        this.recruitCategory = recruitCategory;
    }

    public static Recruitment of(String url, String title, String location, String career, RecruitCategory recruitCategory) {
        return Recruitment.builder()
                .title(title)
                .url(url)
                .location(location)
                .career(career)
                .recruitCategory(recruitCategory)
                .build();
    }
}
