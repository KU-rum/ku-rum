package ku_rum.backend.domain.building.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import ku_rum.backend.domain.building.domain.QBuildingCategory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class BuildingCategoryQueryRepository {
  private final JPAQueryFactory queryFactory;

  public List<Long> findBuildingIds(List<Long> categoryIds) {
    QBuildingCategory qBuildingCategory = QBuildingCategory.buildingCategory;

    // categoryIds에 포함된 category_id에 해당하는 building_id를 조회
    return queryFactory
            .select(qBuildingCategory.building.id)  // building_id를 선택
            .from(qBuildingCategory)
            .where(qBuildingCategory.category.id.in(categoryIds))  // category_id가 categoryIds 목록에 포함된 경우
            .fetch();  // 결과를 List<Long>로 반환
  }

  public List<Long> findBuildingsByCategoryIds(List<Long> buildingCategoryIds) {
    QBuildingCategory qBuildingCategory = QBuildingCategory.buildingCategory;

    return queryFactory
            .select(qBuildingCategory.building.id)
            .from(qBuildingCategory)
            .where(qBuildingCategory.id.in(buildingCategoryIds))
            .fetch();
  }

  public List<Long> findByBuildingIds(List<Long> buildingIds) {
    QBuildingCategory qBuildingCategory = QBuildingCategory.buildingCategory;
    return queryFactory
            .select(qBuildingCategory.building.id)
            .from(qBuildingCategory)
            .where(qBuildingCategory.id.in(buildingIds))
            .fetch();
  }
}
