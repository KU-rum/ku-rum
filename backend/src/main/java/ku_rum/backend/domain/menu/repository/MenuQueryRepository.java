package ku_rum.backend.domain.menu.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import ku_rum.backend.domain.menu.response.MenuSimpleResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class MenuQueryRepository {

  @PersistenceContext
  EntityManager entityManager;

  public List<MenuSimpleResponse> findAllByCategoryId(Long categoryId) {

  }
}
