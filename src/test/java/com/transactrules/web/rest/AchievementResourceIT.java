package com.transactrules.web.rest;

import com.transactrules.GamifierApp;
import com.transactrules.domain.Achievement;
import com.transactrules.repository.AchievementRepository;
import com.transactrules.service.AchievementService;
import com.transactrules.service.dto.AchievementDTO;
import com.transactrules.service.mapper.AchievementMapper;
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
 * Integration tests for the {@link AchievementResource} REST controller.
 */
@SpringBootTest(classes = GamifierApp.class)
public class AchievementResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private AchievementRepository achievementRepository;

    @Autowired
    private AchievementMapper achievementMapper;

    @Autowired
    private AchievementService achievementService;

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

    private MockMvc restAchievementMockMvc;

    private Achievement achievement;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AchievementResource achievementResource = new AchievementResource(achievementService);
        this.restAchievementMockMvc = MockMvcBuilders.standaloneSetup(achievementResource)
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
    public static Achievement createEntity(EntityManager em) {
        Achievement achievement = new Achievement()
            .name(DEFAULT_NAME);
        return achievement;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Achievement createUpdatedEntity(EntityManager em) {
        Achievement achievement = new Achievement()
            .name(UPDATED_NAME);
        return achievement;
    }

    @BeforeEach
    public void initTest() {
        achievement = createEntity(em);
    }

    @Test
    @Transactional
    public void createAchievement() throws Exception {
        int databaseSizeBeforeCreate = achievementRepository.findAll().size();

        // Create the Achievement
        AchievementDTO achievementDTO = achievementMapper.toDto(achievement);
        restAchievementMockMvc.perform(post("/api/achievements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(achievementDTO)))
            .andExpect(status().isCreated());

        // Validate the Achievement in the database
        List<Achievement> achievementList = achievementRepository.findAll();
        assertThat(achievementList).hasSize(databaseSizeBeforeCreate + 1);
        Achievement testAchievement = achievementList.get(achievementList.size() - 1);
        assertThat(testAchievement.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createAchievementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = achievementRepository.findAll().size();

        // Create the Achievement with an existing ID
        achievement.setId(1L);
        AchievementDTO achievementDTO = achievementMapper.toDto(achievement);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAchievementMockMvc.perform(post("/api/achievements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(achievementDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Achievement in the database
        List<Achievement> achievementList = achievementRepository.findAll();
        assertThat(achievementList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = achievementRepository.findAll().size();
        // set the field null
        achievement.setName(null);

        // Create the Achievement, which fails.
        AchievementDTO achievementDTO = achievementMapper.toDto(achievement);

        restAchievementMockMvc.perform(post("/api/achievements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(achievementDTO)))
            .andExpect(status().isBadRequest());

        List<Achievement> achievementList = achievementRepository.findAll();
        assertThat(achievementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAchievements() throws Exception {
        // Initialize the database
        achievementRepository.saveAndFlush(achievement);

        // Get all the achievementList
        restAchievementMockMvc.perform(get("/api/achievements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(achievement.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getAchievement() throws Exception {
        // Initialize the database
        achievementRepository.saveAndFlush(achievement);

        // Get the achievement
        restAchievementMockMvc.perform(get("/api/achievements/{id}", achievement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(achievement.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAchievement() throws Exception {
        // Get the achievement
        restAchievementMockMvc.perform(get("/api/achievements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAchievement() throws Exception {
        // Initialize the database
        achievementRepository.saveAndFlush(achievement);

        int databaseSizeBeforeUpdate = achievementRepository.findAll().size();

        // Update the achievement
        Achievement updatedAchievement = achievementRepository.findById(achievement.getId()).get();
        // Disconnect from session so that the updates on updatedAchievement are not directly saved in db
        em.detach(updatedAchievement);
        updatedAchievement
            .name(UPDATED_NAME);
        AchievementDTO achievementDTO = achievementMapper.toDto(updatedAchievement);

        restAchievementMockMvc.perform(put("/api/achievements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(achievementDTO)))
            .andExpect(status().isOk());

        // Validate the Achievement in the database
        List<Achievement> achievementList = achievementRepository.findAll();
        assertThat(achievementList).hasSize(databaseSizeBeforeUpdate);
        Achievement testAchievement = achievementList.get(achievementList.size() - 1);
        assertThat(testAchievement.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingAchievement() throws Exception {
        int databaseSizeBeforeUpdate = achievementRepository.findAll().size();

        // Create the Achievement
        AchievementDTO achievementDTO = achievementMapper.toDto(achievement);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAchievementMockMvc.perform(put("/api/achievements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(achievementDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Achievement in the database
        List<Achievement> achievementList = achievementRepository.findAll();
        assertThat(achievementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAchievement() throws Exception {
        // Initialize the database
        achievementRepository.saveAndFlush(achievement);

        int databaseSizeBeforeDelete = achievementRepository.findAll().size();

        // Delete the achievement
        restAchievementMockMvc.perform(delete("/api/achievements/{id}", achievement.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Achievement> achievementList = achievementRepository.findAll();
        assertThat(achievementList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Achievement.class);
        Achievement achievement1 = new Achievement();
        achievement1.setId(1L);
        Achievement achievement2 = new Achievement();
        achievement2.setId(achievement1.getId());
        assertThat(achievement1).isEqualTo(achievement2);
        achievement2.setId(2L);
        assertThat(achievement1).isNotEqualTo(achievement2);
        achievement1.setId(null);
        assertThat(achievement1).isNotEqualTo(achievement2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AchievementDTO.class);
        AchievementDTO achievementDTO1 = new AchievementDTO();
        achievementDTO1.setId(1L);
        AchievementDTO achievementDTO2 = new AchievementDTO();
        assertThat(achievementDTO1).isNotEqualTo(achievementDTO2);
        achievementDTO2.setId(achievementDTO1.getId());
        assertThat(achievementDTO1).isEqualTo(achievementDTO2);
        achievementDTO2.setId(2L);
        assertThat(achievementDTO1).isNotEqualTo(achievementDTO2);
        achievementDTO1.setId(null);
        assertThat(achievementDTO1).isNotEqualTo(achievementDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(achievementMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(achievementMapper.fromId(null)).isNull();
    }
}
