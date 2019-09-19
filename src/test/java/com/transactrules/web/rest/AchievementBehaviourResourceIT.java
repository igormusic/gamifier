package com.transactrules.web.rest;

import com.transactrules.GamifierApp;
import com.transactrules.domain.AchievementBehaviour;
import com.transactrules.repository.AchievementBehaviourRepository;
import com.transactrules.service.AchievementBehaviourService;
import com.transactrules.service.dto.AchievementBehaviourDTO;
import com.transactrules.service.mapper.AchievementBehaviourMapper;
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
 * Integration tests for the {@link AchievementBehaviourResource} REST controller.
 */
@SpringBootTest(classes = GamifierApp.class)
public class AchievementBehaviourResourceIT {

    private static final Integer DEFAULT_TIMES = 1;
    private static final Integer UPDATED_TIMES = 2;
    private static final Integer SMALLER_TIMES = 1 - 1;

    @Autowired
    private AchievementBehaviourRepository achievementBehaviourRepository;

    @Autowired
    private AchievementBehaviourMapper achievementBehaviourMapper;

    @Autowired
    private AchievementBehaviourService achievementBehaviourService;

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

    private MockMvc restAchievementBehaviourMockMvc;

    private AchievementBehaviour achievementBehaviour;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AchievementBehaviourResource achievementBehaviourResource = new AchievementBehaviourResource(achievementBehaviourService);
        this.restAchievementBehaviourMockMvc = MockMvcBuilders.standaloneSetup(achievementBehaviourResource)
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
    public static AchievementBehaviour createEntity(EntityManager em) {
        AchievementBehaviour achievementBehaviour = new AchievementBehaviour()
            .times(DEFAULT_TIMES);
        return achievementBehaviour;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AchievementBehaviour createUpdatedEntity(EntityManager em) {
        AchievementBehaviour achievementBehaviour = new AchievementBehaviour()
            .times(UPDATED_TIMES);
        return achievementBehaviour;
    }

    @BeforeEach
    public void initTest() {
        achievementBehaviour = createEntity(em);
    }

    @Test
    @Transactional
    public void createAchievementBehaviour() throws Exception {
        int databaseSizeBeforeCreate = achievementBehaviourRepository.findAll().size();

        // Create the AchievementBehaviour
        AchievementBehaviourDTO achievementBehaviourDTO = achievementBehaviourMapper.toDto(achievementBehaviour);
        restAchievementBehaviourMockMvc.perform(post("/api/achievement-behaviours")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(achievementBehaviourDTO)))
            .andExpect(status().isCreated());

        // Validate the AchievementBehaviour in the database
        List<AchievementBehaviour> achievementBehaviourList = achievementBehaviourRepository.findAll();
        assertThat(achievementBehaviourList).hasSize(databaseSizeBeforeCreate + 1);
        AchievementBehaviour testAchievementBehaviour = achievementBehaviourList.get(achievementBehaviourList.size() - 1);
        assertThat(testAchievementBehaviour.getTimes()).isEqualTo(DEFAULT_TIMES);
    }

    @Test
    @Transactional
    public void createAchievementBehaviourWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = achievementBehaviourRepository.findAll().size();

        // Create the AchievementBehaviour with an existing ID
        achievementBehaviour.setId(1L);
        AchievementBehaviourDTO achievementBehaviourDTO = achievementBehaviourMapper.toDto(achievementBehaviour);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAchievementBehaviourMockMvc.perform(post("/api/achievement-behaviours")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(achievementBehaviourDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AchievementBehaviour in the database
        List<AchievementBehaviour> achievementBehaviourList = achievementBehaviourRepository.findAll();
        assertThat(achievementBehaviourList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTimesIsRequired() throws Exception {
        int databaseSizeBeforeTest = achievementBehaviourRepository.findAll().size();
        // set the field null
        achievementBehaviour.setTimes(null);

        // Create the AchievementBehaviour, which fails.
        AchievementBehaviourDTO achievementBehaviourDTO = achievementBehaviourMapper.toDto(achievementBehaviour);

        restAchievementBehaviourMockMvc.perform(post("/api/achievement-behaviours")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(achievementBehaviourDTO)))
            .andExpect(status().isBadRequest());

        List<AchievementBehaviour> achievementBehaviourList = achievementBehaviourRepository.findAll();
        assertThat(achievementBehaviourList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAchievementBehaviours() throws Exception {
        // Initialize the database
        achievementBehaviourRepository.saveAndFlush(achievementBehaviour);

        // Get all the achievementBehaviourList
        restAchievementBehaviourMockMvc.perform(get("/api/achievement-behaviours?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(achievementBehaviour.getId().intValue())))
            .andExpect(jsonPath("$.[*].times").value(hasItem(DEFAULT_TIMES)));
    }
    
    @Test
    @Transactional
    public void getAchievementBehaviour() throws Exception {
        // Initialize the database
        achievementBehaviourRepository.saveAndFlush(achievementBehaviour);

        // Get the achievementBehaviour
        restAchievementBehaviourMockMvc.perform(get("/api/achievement-behaviours/{id}", achievementBehaviour.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(achievementBehaviour.getId().intValue()))
            .andExpect(jsonPath("$.times").value(DEFAULT_TIMES));
    }

    @Test
    @Transactional
    public void getNonExistingAchievementBehaviour() throws Exception {
        // Get the achievementBehaviour
        restAchievementBehaviourMockMvc.perform(get("/api/achievement-behaviours/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAchievementBehaviour() throws Exception {
        // Initialize the database
        achievementBehaviourRepository.saveAndFlush(achievementBehaviour);

        int databaseSizeBeforeUpdate = achievementBehaviourRepository.findAll().size();

        // Update the achievementBehaviour
        AchievementBehaviour updatedAchievementBehaviour = achievementBehaviourRepository.findById(achievementBehaviour.getId()).get();
        // Disconnect from session so that the updates on updatedAchievementBehaviour are not directly saved in db
        em.detach(updatedAchievementBehaviour);
        updatedAchievementBehaviour
            .times(UPDATED_TIMES);
        AchievementBehaviourDTO achievementBehaviourDTO = achievementBehaviourMapper.toDto(updatedAchievementBehaviour);

        restAchievementBehaviourMockMvc.perform(put("/api/achievement-behaviours")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(achievementBehaviourDTO)))
            .andExpect(status().isOk());

        // Validate the AchievementBehaviour in the database
        List<AchievementBehaviour> achievementBehaviourList = achievementBehaviourRepository.findAll();
        assertThat(achievementBehaviourList).hasSize(databaseSizeBeforeUpdate);
        AchievementBehaviour testAchievementBehaviour = achievementBehaviourList.get(achievementBehaviourList.size() - 1);
        assertThat(testAchievementBehaviour.getTimes()).isEqualTo(UPDATED_TIMES);
    }

    @Test
    @Transactional
    public void updateNonExistingAchievementBehaviour() throws Exception {
        int databaseSizeBeforeUpdate = achievementBehaviourRepository.findAll().size();

        // Create the AchievementBehaviour
        AchievementBehaviourDTO achievementBehaviourDTO = achievementBehaviourMapper.toDto(achievementBehaviour);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAchievementBehaviourMockMvc.perform(put("/api/achievement-behaviours")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(achievementBehaviourDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AchievementBehaviour in the database
        List<AchievementBehaviour> achievementBehaviourList = achievementBehaviourRepository.findAll();
        assertThat(achievementBehaviourList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAchievementBehaviour() throws Exception {
        // Initialize the database
        achievementBehaviourRepository.saveAndFlush(achievementBehaviour);

        int databaseSizeBeforeDelete = achievementBehaviourRepository.findAll().size();

        // Delete the achievementBehaviour
        restAchievementBehaviourMockMvc.perform(delete("/api/achievement-behaviours/{id}", achievementBehaviour.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AchievementBehaviour> achievementBehaviourList = achievementBehaviourRepository.findAll();
        assertThat(achievementBehaviourList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AchievementBehaviour.class);
        AchievementBehaviour achievementBehaviour1 = new AchievementBehaviour();
        achievementBehaviour1.setId(1L);
        AchievementBehaviour achievementBehaviour2 = new AchievementBehaviour();
        achievementBehaviour2.setId(achievementBehaviour1.getId());
        assertThat(achievementBehaviour1).isEqualTo(achievementBehaviour2);
        achievementBehaviour2.setId(2L);
        assertThat(achievementBehaviour1).isNotEqualTo(achievementBehaviour2);
        achievementBehaviour1.setId(null);
        assertThat(achievementBehaviour1).isNotEqualTo(achievementBehaviour2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AchievementBehaviourDTO.class);
        AchievementBehaviourDTO achievementBehaviourDTO1 = new AchievementBehaviourDTO();
        achievementBehaviourDTO1.setId(1L);
        AchievementBehaviourDTO achievementBehaviourDTO2 = new AchievementBehaviourDTO();
        assertThat(achievementBehaviourDTO1).isNotEqualTo(achievementBehaviourDTO2);
        achievementBehaviourDTO2.setId(achievementBehaviourDTO1.getId());
        assertThat(achievementBehaviourDTO1).isEqualTo(achievementBehaviourDTO2);
        achievementBehaviourDTO2.setId(2L);
        assertThat(achievementBehaviourDTO1).isNotEqualTo(achievementBehaviourDTO2);
        achievementBehaviourDTO1.setId(null);
        assertThat(achievementBehaviourDTO1).isNotEqualTo(achievementBehaviourDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(achievementBehaviourMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(achievementBehaviourMapper.fromId(null)).isNull();
    }
}
