package com.transactrules.web.rest;

import com.transactrules.GamifierApp;
import com.transactrules.domain.Behaviour;
import com.transactrules.repository.BehaviourRepository;
import com.transactrules.service.BehaviourService;
import com.transactrules.service.dto.BehaviourDTO;
import com.transactrules.service.mapper.BehaviourMapper;
import com.transactrules.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.transactrules.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link BehaviourResource} REST controller.
 */
@SpringBootTest(classes = GamifierApp.class)
public class BehaviourResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_POINTS = 1;
    private static final Integer UPDATED_POINTS = 2;
    private static final Integer SMALLER_POINTS = 1 - 1;

    @Autowired
    private BehaviourRepository behaviourRepository;

    @Autowired
    private BehaviourMapper behaviourMapper;

    @Autowired
    private BehaviourService behaviourService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restBehaviourMockMvc;

    private Behaviour behaviour;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BehaviourResource behaviourResource = new BehaviourResource(behaviourService);
        this.restBehaviourMockMvc = MockMvcBuilders.standaloneSetup(behaviourResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Behaviour createEntity(EntityManager em) {
        Behaviour behaviour = new Behaviour()
            .name(DEFAULT_NAME)
            .points(DEFAULT_POINTS);
        return behaviour;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Behaviour createUpdatedEntity(EntityManager em) {
        Behaviour behaviour = new Behaviour()
            .name(UPDATED_NAME)
            .points(UPDATED_POINTS);
        return behaviour;
    }

    @BeforeEach
    public void initTest() {
        behaviour = createEntity(em);
    }

    @Test
    @Transactional
    public void createBehaviour() throws Exception {
        int databaseSizeBeforeCreate = behaviourRepository.findAll().size();

        // Create the Behaviour
        BehaviourDTO behaviourDTO = behaviourMapper.toDto(behaviour);
        restBehaviourMockMvc.perform(post("/api/behaviours")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(behaviourDTO)))
            .andExpect(status().isCreated());

        // Validate the Behaviour in the database
        List<Behaviour> behaviourList = behaviourRepository.findAll();
        assertThat(behaviourList).hasSize(databaseSizeBeforeCreate + 1);
        Behaviour testBehaviour = behaviourList.get(behaviourList.size() - 1);
        assertThat(testBehaviour.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBehaviour.getPoints()).isEqualTo(DEFAULT_POINTS);
    }

    @Test
    @Transactional
    public void createBehaviourWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = behaviourRepository.findAll().size();

        // Create the Behaviour with an existing ID
        behaviour.setId(1L);
        BehaviourDTO behaviourDTO = behaviourMapper.toDto(behaviour);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBehaviourMockMvc.perform(post("/api/behaviours")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(behaviourDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Behaviour in the database
        List<Behaviour> behaviourList = behaviourRepository.findAll();
        assertThat(behaviourList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = behaviourRepository.findAll().size();
        // set the field null
        behaviour.setName(null);

        // Create the Behaviour, which fails.
        BehaviourDTO behaviourDTO = behaviourMapper.toDto(behaviour);

        restBehaviourMockMvc.perform(post("/api/behaviours")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(behaviourDTO)))
            .andExpect(status().isBadRequest());

        List<Behaviour> behaviourList = behaviourRepository.findAll();
        assertThat(behaviourList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPointsIsRequired() throws Exception {
        int databaseSizeBeforeTest = behaviourRepository.findAll().size();
        // set the field null
        behaviour.setPoints(null);

        // Create the Behaviour, which fails.
        BehaviourDTO behaviourDTO = behaviourMapper.toDto(behaviour);

        restBehaviourMockMvc.perform(post("/api/behaviours")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(behaviourDTO)))
            .andExpect(status().isBadRequest());

        List<Behaviour> behaviourList = behaviourRepository.findAll();
        assertThat(behaviourList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBehaviours() throws Exception {
        // Initialize the database
        behaviourRepository.saveAndFlush(behaviour);

        // Get all the behaviourList
        restBehaviourMockMvc.perform(get("/api/behaviours?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(behaviour.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].points").value(hasItem(DEFAULT_POINTS)));
    }
    
    @Test
    @Transactional
    public void getBehaviour() throws Exception {
        // Initialize the database
        behaviourRepository.saveAndFlush(behaviour);

        // Get the behaviour
        restBehaviourMockMvc.perform(get("/api/behaviours/{id}", behaviour.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(behaviour.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.points").value(DEFAULT_POINTS));
    }

    @Test
    @Transactional
    public void getNonExistingBehaviour() throws Exception {
        // Get the behaviour
        restBehaviourMockMvc.perform(get("/api/behaviours/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBehaviour() throws Exception {
        // Initialize the database
        behaviourRepository.saveAndFlush(behaviour);

        int databaseSizeBeforeUpdate = behaviourRepository.findAll().size();

        // Update the behaviour
        Behaviour updatedBehaviour = behaviourRepository.findById(behaviour.getId()).get();
        // Disconnect from session so that the updates on updatedBehaviour are not directly saved in db
        em.detach(updatedBehaviour);
        updatedBehaviour
            .name(UPDATED_NAME)
            .points(UPDATED_POINTS);
        BehaviourDTO behaviourDTO = behaviourMapper.toDto(updatedBehaviour);

        restBehaviourMockMvc.perform(put("/api/behaviours")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(behaviourDTO)))
            .andExpect(status().isOk());

        // Validate the Behaviour in the database
        List<Behaviour> behaviourList = behaviourRepository.findAll();
        assertThat(behaviourList).hasSize(databaseSizeBeforeUpdate);
        Behaviour testBehaviour = behaviourList.get(behaviourList.size() - 1);
        assertThat(testBehaviour.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBehaviour.getPoints()).isEqualTo(UPDATED_POINTS);
    }

    @Test
    @Transactional
    public void updateNonExistingBehaviour() throws Exception {
        int databaseSizeBeforeUpdate = behaviourRepository.findAll().size();

        // Create the Behaviour
        BehaviourDTO behaviourDTO = behaviourMapper.toDto(behaviour);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBehaviourMockMvc.perform(put("/api/behaviours")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(behaviourDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Behaviour in the database
        List<Behaviour> behaviourList = behaviourRepository.findAll();
        assertThat(behaviourList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBehaviour() throws Exception {
        // Initialize the database
        behaviourRepository.saveAndFlush(behaviour);

        int databaseSizeBeforeDelete = behaviourRepository.findAll().size();

        // Delete the behaviour
        restBehaviourMockMvc.perform(delete("/api/behaviours/{id}", behaviour.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Behaviour> behaviourList = behaviourRepository.findAll();
        assertThat(behaviourList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Behaviour.class);
        Behaviour behaviour1 = new Behaviour();
        behaviour1.setId(1L);
        Behaviour behaviour2 = new Behaviour();
        behaviour2.setId(behaviour1.getId());
        assertThat(behaviour1).isEqualTo(behaviour2);
        behaviour2.setId(2L);
        assertThat(behaviour1).isNotEqualTo(behaviour2);
        behaviour1.setId(null);
        assertThat(behaviour1).isNotEqualTo(behaviour2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BehaviourDTO.class);
        BehaviourDTO behaviourDTO1 = new BehaviourDTO();
        behaviourDTO1.setId(1L);
        BehaviourDTO behaviourDTO2 = new BehaviourDTO();
        assertThat(behaviourDTO1).isNotEqualTo(behaviourDTO2);
        behaviourDTO2.setId(behaviourDTO1.getId());
        assertThat(behaviourDTO1).isEqualTo(behaviourDTO2);
        behaviourDTO2.setId(2L);
        assertThat(behaviourDTO1).isNotEqualTo(behaviourDTO2);
        behaviourDTO1.setId(null);
        assertThat(behaviourDTO1).isNotEqualTo(behaviourDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(behaviourMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(behaviourMapper.fromId(null)).isNull();
    }
}
