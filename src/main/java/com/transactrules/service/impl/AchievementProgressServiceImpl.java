package com.transactrules.service.impl;

import com.transactrules.service.AchievementProgressService;
import com.transactrules.domain.AchievementProgress;
import com.transactrules.repository.AchievementProgressRepository;
import com.transactrules.service.dto.AchievementProgressDTO;
import com.transactrules.service.mapper.AchievementProgressMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link AchievementProgress}.
 */
@Service
@Transactional
public class AchievementProgressServiceImpl implements AchievementProgressService {

    private final Logger log = LoggerFactory.getLogger(AchievementProgressServiceImpl.class);

    private final AchievementProgressRepository achievementProgressRepository;

    private final AchievementProgressMapper achievementProgressMapper;

    public AchievementProgressServiceImpl(AchievementProgressRepository achievementProgressRepository, AchievementProgressMapper achievementProgressMapper) {
        this.achievementProgressRepository = achievementProgressRepository;
        this.achievementProgressMapper = achievementProgressMapper;
    }

    /**
     * Save a achievementProgress.
     *
     * @param achievementProgressDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public AchievementProgressDTO save(AchievementProgressDTO achievementProgressDTO) {
        log.debug("Request to save AchievementProgress : {}", achievementProgressDTO);
        AchievementProgress achievementProgress = achievementProgressMapper.toEntity(achievementProgressDTO);
        achievementProgress = achievementProgressRepository.save(achievementProgress);
        return achievementProgressMapper.toDto(achievementProgress);
    }

    /**
     * Get all the achievementProgresses.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<AchievementProgressDTO> findAll() {
        log.debug("Request to get all AchievementProgresses");
        return achievementProgressRepository.findAllWithEagerRelationships().stream()
            .map(achievementProgressMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the achievementProgresses with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<AchievementProgressDTO> findAllWithEagerRelationships(Pageable pageable) {
        return achievementProgressRepository.findAllWithEagerRelationships(pageable).map(achievementProgressMapper::toDto);
    }
    

    /**
     * Get one achievementProgress by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AchievementProgressDTO> findOne(Long id) {
        log.debug("Request to get AchievementProgress : {}", id);
        return achievementProgressRepository.findOneWithEagerRelationships(id)
            .map(achievementProgressMapper::toDto);
    }

    /**
     * Delete the achievementProgress by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AchievementProgress : {}", id);
        achievementProgressRepository.deleteById(id);
    }
}
