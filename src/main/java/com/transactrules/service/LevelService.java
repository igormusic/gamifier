package com.transactrules.service;

import com.transactrules.service.dto.LevelDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.transactrules.domain.Level}.
 */
public interface LevelService {

    /**
     * Save a level.
     *
     * @param levelDTO the entity to save.
     * @return the persisted entity.
     */
    LevelDTO save(LevelDTO levelDTO);

    /**
     * Get all the levels.
     *
     * @return the list of entities.
     */
    List<LevelDTO> findAll();


    /**
     * Get the "id" level.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LevelDTO> findOne(Long id);

    /**
     * Delete the "id" level.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
