package com.transactrules.service.impl;

import com.transactrules.service.BehaviourService;
import com.transactrules.domain.Behaviour;
import com.transactrules.repository.BehaviourRepository;
import com.transactrules.service.dto.BehaviourDTO;
import com.transactrules.service.mapper.BehaviourMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Behaviour}.
 */
@Service
@Transactional
public class BehaviourServiceImpl implements BehaviourService {

    private final Logger log = LoggerFactory.getLogger(BehaviourServiceImpl.class);

    private final BehaviourRepository behaviourRepository;

    private final BehaviourMapper behaviourMapper;

    public BehaviourServiceImpl(BehaviourRepository behaviourRepository, BehaviourMapper behaviourMapper) {
        this.behaviourRepository = behaviourRepository;
        this.behaviourMapper = behaviourMapper;
    }

    /**
     * Save a behaviour.
     *
     * @param behaviourDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public BehaviourDTO save(BehaviourDTO behaviourDTO) {
        log.debug("Request to save Behaviour : {}", behaviourDTO);
        Behaviour behaviour = behaviourMapper.toEntity(behaviourDTO);
        behaviour = behaviourRepository.save(behaviour);
        return behaviourMapper.toDto(behaviour);
    }

    /**
     * Get all the behaviours.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<BehaviourDTO> findAll() {
        log.debug("Request to get all Behaviours");
        return behaviourRepository.findAll().stream()
            .map(behaviourMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one behaviour by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BehaviourDTO> findOne(Long id) {
        log.debug("Request to get Behaviour : {}", id);
        return behaviourRepository.findById(id)
            .map(behaviourMapper::toDto);
    }

    /**
     * Delete the behaviour by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Behaviour : {}", id);
        behaviourRepository.deleteById(id);
    }
}
