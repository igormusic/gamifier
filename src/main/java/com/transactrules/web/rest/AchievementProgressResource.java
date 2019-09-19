package com.transactrules.web.rest;

import com.transactrules.service.AchievementProgressService;
import com.transactrules.web.rest.errors.BadRequestAlertException;
import com.transactrules.service.dto.AchievementProgressDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.transactrules.domain.AchievementProgress}.
 */
@RestController
@RequestMapping("/api")
public class AchievementProgressResource {

    private final Logger log = LoggerFactory.getLogger(AchievementProgressResource.class);

    private static final String ENTITY_NAME = "achievementProgress";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AchievementProgressService achievementProgressService;

    public AchievementProgressResource(AchievementProgressService achievementProgressService) {
        this.achievementProgressService = achievementProgressService;
    }

    /**
     * {@code POST  /achievement-progresses} : Create a new achievementProgress.
     *
     * @param achievementProgressDTO the achievementProgressDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new achievementProgressDTO, or with status {@code 400 (Bad Request)} if the achievementProgress has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/achievement-progresses")
    public ResponseEntity<AchievementProgressDTO> createAchievementProgress(@RequestBody AchievementProgressDTO achievementProgressDTO) throws URISyntaxException {
        log.debug("REST request to save AchievementProgress : {}", achievementProgressDTO);
        if (achievementProgressDTO.getId() != null) {
            throw new BadRequestAlertException("A new achievementProgress cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AchievementProgressDTO result = achievementProgressService.save(achievementProgressDTO);
        return ResponseEntity.created(new URI("/api/achievement-progresses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /achievement-progresses} : Updates an existing achievementProgress.
     *
     * @param achievementProgressDTO the achievementProgressDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated achievementProgressDTO,
     * or with status {@code 400 (Bad Request)} if the achievementProgressDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the achievementProgressDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/achievement-progresses")
    public ResponseEntity<AchievementProgressDTO> updateAchievementProgress(@RequestBody AchievementProgressDTO achievementProgressDTO) throws URISyntaxException {
        log.debug("REST request to update AchievementProgress : {}", achievementProgressDTO);
        if (achievementProgressDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AchievementProgressDTO result = achievementProgressService.save(achievementProgressDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, achievementProgressDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /achievement-progresses} : get all the achievementProgresses.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of achievementProgresses in body.
     */
    @GetMapping("/achievement-progresses")
    public List<AchievementProgressDTO> getAllAchievementProgresses(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all AchievementProgresses");
        return achievementProgressService.findAll();
    }

    /**
     * {@code GET  /achievement-progresses/:id} : get the "id" achievementProgress.
     *
     * @param id the id of the achievementProgressDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the achievementProgressDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/achievement-progresses/{id}")
    public ResponseEntity<AchievementProgressDTO> getAchievementProgress(@PathVariable Long id) {
        log.debug("REST request to get AchievementProgress : {}", id);
        Optional<AchievementProgressDTO> achievementProgressDTO = achievementProgressService.findOne(id);
        return ResponseUtil.wrapOrNotFound(achievementProgressDTO);
    }

    /**
     * {@code DELETE  /achievement-progresses/:id} : delete the "id" achievementProgress.
     *
     * @param id the id of the achievementProgressDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/achievement-progresses/{id}")
    public ResponseEntity<Void> deleteAchievementProgress(@PathVariable Long id) {
        log.debug("REST request to delete AchievementProgress : {}", id);
        achievementProgressService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
