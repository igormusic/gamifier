package com.transactrules.repository;
import com.transactrules.domain.AchievementBehaviour;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AchievementBehaviour entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AchievementBehaviourRepository extends JpaRepository<AchievementBehaviour, Long> {

}
