package com.transactrules.service.impl;

import com.transactrules.service.AchievementBehaviourService;
import com.transactrules.domain.AchievementBehaviour;
import com.transactrules.repository.AchievementBehaviourRepository;
import com.transactrules.service.dto.AchievementBehaviourDTO;
import com.transactrules.service.mapper.AchievementBehaviourMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link AchievementBehaviour}.
 */
@Service
@Transactional
public class AchievementBehaviourServiceImpl implements AchievementBehaviourService {

    private final Logger log = LoggerFactory.getLogger(AchievementBehaviourServiceImpl.class);

    private final AchievementBehaviourRepository achievementBehaviourRepository;

    private final AchievementBehaviourMapper achievementBehaviourMapper;

    public AchievementBehaviourServiceImpl(AchievementBehaviourRepository achievementBehaviourRepository, AchievementBehaviourMapper achievementBehaviourMapper) {
        this.achievementBehaviourRepository = achievementBehaviourRepository;
        this.achievementBehaviourMapper = achievementBehaviourMapper;
    }

    /**
     * Save a achievementBehaviour.
     *
     * @param achievementBehaviourDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public AchievementBehaviourDTO save(AchievementBehaviourDTO achievementBehaviourDTO) {
        log.debug("Request to save AchievementBehaviour : {}", achievementBehaviourDTO);
        AchievementBehaviour achievementBehaviour = achievementBehaviourMapper.toEntity(achievementBehaviourDTO);
        achievementBehaviour = achievementBehaviourRepository.save(achievementBehaviour);
        return achievementBehaviourMapper.toDto(achievementBehaviour);
    }

    /**
     * Get all the achievementBehaviours.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<AchievementBehaviourDTO> findAll() {
        log.debug("Request to get all AchievementBehaviours");
        return achievementBehaviourRepository.findAll().stream()
            .map(achievementBehaviourMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one achievementBehaviour by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AchievementBehaviourDTO> findOne(Long id) {
        log.debug("Request to get AchievementBehaviour : {}", id);
        return achievementBehaviourRepository.findById(id)
            .map(achievementBehaviourMapper::toDto);
    }

    /**
     * Delete the achievementBehaviour by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AchievementBehaviour : {}", id);
        achievementBehaviourRepository.deleteById(id);
    }
}
