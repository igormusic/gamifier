package com.transactrules.web.rest;

import com.transactrules.service.AchievementService;
import com.transactrules.web.rest.errors.BadRequestAlertException;
import com.transactrules.service.dto.AchievementDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.transactrules.domain.Achievement}.
 */
@RestController
@RequestMapping("/api")
public class AchievementResource {

    private final Logger log = LoggerFactory.getLogger(AchievementResource.class);

    private static final String ENTITY_NAME = "achievement";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AchievementService achievementService;

    public AchievementResource(AchievementService achievementService) {
        this.achievementService = achievementService;
    }

    /**
     * {@code POST  /achievements} : Create a new achievement.
     *
     * @param achievementDTO the achievementDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new achievementDTO, or with status {@code 400 (Bad Request)} if the achievement has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/achievements")
    public ResponseEntity<AchievementDTO> createAchievement(@Valid @RequestBody AchievementDTO achievementDTO) throws URISyntaxException {
        log.debug("REST request to save Achievement : {}", achievementDTO);
        if (achievementDTO.getId() != null) {
            throw new BadRequestAlertException("A new achievement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AchievementDTO result = achievementService.save(achievementDTO);
        return ResponseEntity.created(new URI("/api/achievements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /achievements} : Updates an existing achievement.
     *
     * @param achievementDTO the achievementDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated achievementDTO,
     * or with status {@code 400 (Bad Request)} if the achievementDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the achievementDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/achievements")
    public ResponseEntity<AchievementDTO> updateAchievement(@Valid @RequestBody AchievementDTO achievementDTO) throws URISyntaxException {
        log.debug("REST request to update Achievement : {}", achievementDTO);
        if (achievementDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AchievementDTO result = achievementService.save(achievementDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, achievementDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /achievements} : get all the achievements.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of achievements in body.
     */
    @GetMapping("/achievements")
    public List<AchievementDTO> getAllAchievements() {
        log.debug("REST request to get all Achievements");
        return achievementService.findAll();
    }

    /**
     * {@code GET  /achievements/:id} : get the "id" achievement.
     *
     * @param id the id of the achievementDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the achievementDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/achievements/{id}")
    public ResponseEntity<AchievementDTO> getAchievement(@PathVariable Long id) {
        log.debug("REST request to get Achievement : {}", id);
        Optional<AchievementDTO> achievementDTO = achievementService.findOne(id);
        return ResponseUtil.wrapOrNotFound(achievementDTO);
    }

    /**
     * {@code DELETE  /achievements/:id} : delete the "id" achievement.
     *
     * @param id the id of the achievementDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/achievements/{id}")
    public ResponseEntity<Void> deleteAchievement(@PathVariable Long id) {
        log.debug("REST request to delete Achievement : {}", id);
        achievementService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
