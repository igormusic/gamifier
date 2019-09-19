package com.transactrules.service.impl;

import com.transactrules.service.LevelService;
import com.transactrules.domain.Level;
import com.transactrules.repository.LevelRepository;
import com.transactrules.service.dto.LevelDTO;
import com.transactrules.service.mapper.LevelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Level}.
 */
@Service
@Transactional
public class LevelServiceImpl implements LevelService {

    private final Logger log = LoggerFactory.getLogger(LevelServiceImpl.class);

    private final LevelRepository levelRepository;

    private final LevelMapper levelMapper;

    public LevelServiceImpl(LevelRepository levelRepository, LevelMapper levelMapper) {
        this.levelRepository = levelRepository;
        this.levelMapper = levelMapper;
    }

    /**
     * Save a level.
     *
     * @param levelDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public LevelDTO save(LevelDTO levelDTO) {
        log.debug("Request to save Level : {}", levelDTO);
        Level level = levelMapper.toEntity(levelDTO);
        level = levelRepository.save(level);
        return levelMapper.toDto(level);
    }

    /**
     * Get all the levels.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<LevelDTO> findAll() {
        log.debug("Request to get all Levels");
        return levelRepository.findAll().stream()
            .map(levelMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one level by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<LevelDTO> findOne(Long id) {
        log.debug("Request to get Level : {}", id);
        return levelRepository.findById(id)
            .map(levelMapper::toDto);
    }

    /**
     * Delete the level by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Level : {}", id);
        levelRepository.deleteById(id);
    }
}
