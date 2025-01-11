package ku_rum.backend.domain.user.domain;

import jakarta.persistence.*;
import ku_rum.backend.domain.department.domain.Department;
import ku_rum.backend.global.type.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20, unique = true)
    private String email;

    @Column(nullable = false, length = 50, unique = true)
    private String nickname;

    @Column(length = 128)
    private String password;

    @Column(nullable = false, length = 15)
    private String studentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @ElementCollection
    private List<String> roles = new ArrayList<>();

    @Builder
    private User(String email, String nickname, String password, String studentId, Department department) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.studentId = studentId;
        this.department = department;
        this.roles.add("ROLE_USER");
    }

    public static User of(String email, String nickname, String password, String studentId, Department department) {
        return User.builder()
                .email(email)
                .nickname(nickname)
                .password(password)
                .studentId(studentId)
                .department(department)
                .build();
    }
}
