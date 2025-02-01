package ku_rum.backend.domain.user.domain.repository;

import ku_rum.backend.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByLoginId(String loginId);
    boolean existsByStudentId(String studentId);
    boolean existsByEmail(String email);
    Optional<User> findUserById(Long id);
    Optional<User> findUserByNickname(String nickname);
    Optional<User> findUserByLoginId(String loginId);
    Optional<User> findUserByEmail(String email);
}
