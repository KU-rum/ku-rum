package ku_rum.backend.domain.user.domain;

import jakarta.persistence.*;
import ku_rum.backend.domain.department.domain.Department;
import ku_rum.backend.global.type.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 15)
    private String studentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @Builder
    private User(String name, String studentId, Department department) {
        this.name = name;
        this.studentId = studentId;
        this.department = department;
    }

    public static User of(String name, String studentId, Department department) {
        return User.builder()
                .name(name)
                .studentId(studentId)
                .department(department)
                .build();
    }
}
