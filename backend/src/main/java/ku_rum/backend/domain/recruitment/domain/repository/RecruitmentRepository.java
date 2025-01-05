package ku_rum.backend.domain.recruitment.domain.repository;

import ku_rum.backend.domain.recruitment.domain.Recruitment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecruitmentRepository extends JpaRepository<Recruitment, String> {

}
