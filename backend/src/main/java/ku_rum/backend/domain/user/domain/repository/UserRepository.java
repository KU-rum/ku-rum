package ku_rum.backend.domain.user.domain.repository;

import ku_rum.backend.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    boolean existsByStudentId(String studentId);

}
