package ku_rum.backend.domain.category.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import ku_rum.backend.domain.category.domain.QCategory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class CategoryQueryRepository {
  private final JPAQueryFactory queryFactory;

  public Optional<List<Long>> findAllByName(String category) {
    QCategory qCategory = QCategory.category;
    List<Long> buildingCategoryIds = queryFactory
            .select(qCategory.id)  // 필요한 필드 : category객체의 id
            .from(qCategory)
            .where(qCategory.name.eq(category))
            .fetch();

    return buildingCategoryIds.isEmpty() ? Optional.empty() : Optional.of(buildingCategoryIds);
  }
}
