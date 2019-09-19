package com.transactrules.web.rest;

import com.transactrules.service.AchievementBehaviourService;
import com.transactrules.web.rest.errors.BadRequestAlertException;
import com.transactrules.service.dto.AchievementBehaviourDTO;

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
 * REST controller for managing {@link com.transactrules.domain.AchievementBehaviour}.
 */
@RestController
@RequestMapping("/api")
public class AchievementBehaviourResource {

    private final Logger log = LoggerFactory.getLogger(AchievementBehaviourResource.class);

    private static final String ENTITY_NAME = "achievementBehaviour";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AchievementBehaviourService achievementBehaviourService;

    public AchievementBehaviourResource(AchievementBehaviourService achievementBehaviourService) {
        this.achievementBehaviourService = achievementBehaviourService;
    }

    /**
     * {@code POST  /achievement-behaviours} : Create a new achievementBehaviour.
     *
     * @param achievementBehaviourDTO the achievementBehaviourDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new achievementBehaviourDTO, or with status {@code 400 (Bad Request)} if the achievementBehaviour has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/achievement-behaviours")
    public ResponseEntity<AchievementBehaviourDTO> createAchievementBehaviour(@Valid @RequestBody AchievementBehaviourDTO achievementBehaviourDTO) throws URISyntaxException {
        log.debug("REST request to save AchievementBehaviour : {}", achievementBehaviourDTO);
        if (achievementBehaviourDTO.getId() != null) {
            throw new BadRequestAlertException("A new achievementBehaviour cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AchievementBehaviourDTO result = achievementBehaviourService.save(achievementBehaviourDTO);
        return ResponseEntity.created(new URI("/api/achievement-behaviours/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /achievement-behaviours} : Updates an existing achievementBehaviour.
     *
     * @param achievementBehaviourDTO the achievementBehaviourDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated achievementBehaviourDTO,
     * or with status {@code 400 (Bad Request)} if the achievementBehaviourDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the achievementBehaviourDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/achievement-behaviours")
    public ResponseEntity<AchievementBehaviourDTO> updateAchievementBehaviour(@Valid @RequestBody AchievementBehaviourDTO achievementBehaviourDTO) throws URISyntaxException {
        log.debug("REST request to update AchievementBehaviour : {}", achievementBehaviourDTO);
        if (achievementBehaviourDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AchievementBehaviourDTO result = achievementBehaviourService.save(achievementBehaviourDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, achievementBehaviourDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /achievement-behaviours} : get all the achievementBehaviours.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of achievementBehaviours in body.
     */
    @GetMapping("/achievement-behaviours")
    public List<AchievementBehaviourDTO> getAllAchievementBehaviours() {
        log.debug("REST request to get all AchievementBehaviours");
        return achievementBehaviourService.findAll();
    }

    /**
     * {@code GET  /achievement-behaviours/:id} : get the "id" achievementBehaviour.
     *
     * @param id the id of the achievementBehaviourDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the achievementBehaviourDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/achievement-behaviours/{id}")
    public ResponseEntity<AchievementBehaviourDTO> getAchievementBehaviour(@PathVariable Long id) {
        log.debug("REST request to get AchievementBehaviour : {}", id);
        Optional<AchievementBehaviourDTO> achievementBehaviourDTO = achievementBehaviourService.findOne(id);
        return ResponseUtil.wrapOrNotFound(achievementBehaviourDTO);
    }

    /**
     * {@code DELETE  /achievement-behaviours/:id} : delete the "id" achievementBehaviour.
     *
     * @param id the id of the achievementBehaviourDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/achievement-behaviours/{id}")
    public ResponseEntity<Void> deleteAchievementBehaviour(@PathVariable Long id) {
        log.debug("REST request to delete AchievementBehaviour : {}", id);
        achievementBehaviourService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
