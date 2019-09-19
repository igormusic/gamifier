package com.transactrules.service;

import com.transactrules.service.dto.AchievementBehaviourDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.transactrules.domain.AchievementBehaviour}.
 */
public interface AchievementBehaviourService {

    /**
     * Save a achievementBehaviour.
     *
     * @param achievementBehaviourDTO the entity to save.
     * @return the persisted entity.
     */
    AchievementBehaviourDTO save(AchievementBehaviourDTO achievementBehaviourDTO);

    /**
     * Get all the achievementBehaviours.
     *
     * @return the list of entities.
     */
    List<AchievementBehaviourDTO> findAll();


    /**
     * Get the "id" achievementBehaviour.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AchievementBehaviourDTO> findOne(Long id);

    /**
     * Delete the "id" achievementBehaviour.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
