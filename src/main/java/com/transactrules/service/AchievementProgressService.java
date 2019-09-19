package com.transactrules.service;

import com.transactrules.service.dto.AchievementProgressDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.transactrules.domain.AchievementProgress}.
 */
public interface AchievementProgressService {

    /**
     * Save a achievementProgress.
     *
     * @param achievementProgressDTO the entity to save.
     * @return the persisted entity.
     */
    AchievementProgressDTO save(AchievementProgressDTO achievementProgressDTO);

    /**
     * Get all the achievementProgresses.
     *
     * @return the list of entities.
     */
    List<AchievementProgressDTO> findAll();

    /**
     * Get all the achievementProgresses with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<AchievementProgressDTO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" achievementProgress.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AchievementProgressDTO> findOne(Long id);

    /**
     * Delete the "id" achievementProgress.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
