package com.transactrules.repository;
import com.transactrules.domain.AchievementProgress;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the AchievementProgress entity.
 */
@Repository
public interface AchievementProgressRepository extends JpaRepository<AchievementProgress, Long> {

    @Query(value = "select distinct achievementProgress from AchievementProgress achievementProgress left join fetch achievementProgress.activities",
        countQuery = "select count(distinct achievementProgress) from AchievementProgress achievementProgress")
    Page<AchievementProgress> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct achievementProgress from AchievementProgress achievementProgress left join fetch achievementProgress.activities")
    List<AchievementProgress> findAllWithEagerRelationships();

    @Query("select achievementProgress from AchievementProgress achievementProgress left join fetch achievementProgress.activities where achievementProgress.id =:id")
    Optional<AchievementProgress> findOneWithEagerRelationships(@Param("id") Long id);

}
