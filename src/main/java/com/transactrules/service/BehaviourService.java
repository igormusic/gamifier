package com.transactrules.service;

import com.transactrules.service.dto.BehaviourDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.transactrules.domain.Behaviour}.
 */
public interface BehaviourService {

    /**
     * Save a behaviour.
     *
     * @param behaviourDTO the entity to save.
     * @return the persisted entity.
     */
    BehaviourDTO save(BehaviourDTO behaviourDTO);

    /**
     * Get all the behaviours.
     *
     * @return the list of entities.
     */
    List<BehaviourDTO> findAll();


    /**
     * Get the "id" behaviour.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BehaviourDTO> findOne(Long id);

    /**
     * Delete the "id" behaviour.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
