package com.transactrules.web.rest;

import com.transactrules.service.BehaviourService;
import com.transactrules.web.rest.errors.BadRequestAlertException;
import com.transactrules.service.dto.BehaviourDTO;

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
 * REST controller for managing {@link com.transactrules.domain.Behaviour}.
 */
@RestController
@RequestMapping("/api")
public class BehaviourResource {

    private final Logger log = LoggerFactory.getLogger(BehaviourResource.class);

    private static final String ENTITY_NAME = "behaviour";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BehaviourService behaviourService;

    public BehaviourResource(BehaviourService behaviourService) {
        this.behaviourService = behaviourService;
    }

    /**
     * {@code POST  /behaviours} : Create a new behaviour.
     *
     * @param behaviourDTO the behaviourDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new behaviourDTO, or with status {@code 400 (Bad Request)} if the behaviour has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/behaviours")
    public ResponseEntity<BehaviourDTO> createBehaviour(@Valid @RequestBody BehaviourDTO behaviourDTO) throws URISyntaxException {
        log.debug("REST request to save Behaviour : {}", behaviourDTO);
        if (behaviourDTO.getId() != null) {
            throw new BadRequestAlertException("A new behaviour cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BehaviourDTO result = behaviourService.save(behaviourDTO);
        return ResponseEntity.created(new URI("/api/behaviours/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /behaviours} : Updates an existing behaviour.
     *
     * @param behaviourDTO the behaviourDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated behaviourDTO,
     * or with status {@code 400 (Bad Request)} if the behaviourDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the behaviourDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/behaviours")
    public ResponseEntity<BehaviourDTO> updateBehaviour(@Valid @RequestBody BehaviourDTO behaviourDTO) throws URISyntaxException {
        log.debug("REST request to update Behaviour : {}", behaviourDTO);
        if (behaviourDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BehaviourDTO result = behaviourService.save(behaviourDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, behaviourDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /behaviours} : get all the behaviours.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of behaviours in body.
     */
    @GetMapping("/behaviours")
    public List<BehaviourDTO> getAllBehaviours() {
        log.debug("REST request to get all Behaviours");
        return behaviourService.findAll();
    }

    /**
     * {@code GET  /behaviours/:id} : get the "id" behaviour.
     *
     * @param id the id of the behaviourDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the behaviourDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/behaviours/{id}")
    public ResponseEntity<BehaviourDTO> getBehaviour(@PathVariable Long id) {
        log.debug("REST request to get Behaviour : {}", id);
        Optional<BehaviourDTO> behaviourDTO = behaviourService.findOne(id);
        return ResponseUtil.wrapOrNotFound(behaviourDTO);
    }

    /**
     * {@code DELETE  /behaviours/:id} : delete the "id" behaviour.
     *
     * @param id the id of the behaviourDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/behaviours/{id}")
    public ResponseEntity<Void> deleteBehaviour(@PathVariable Long id) {
        log.debug("REST request to delete Behaviour : {}", id);
        behaviourService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
