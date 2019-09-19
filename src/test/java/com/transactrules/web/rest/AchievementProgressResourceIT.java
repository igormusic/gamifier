package com.transactrules.web.rest;

import com.transactrules.GamifierApp;
import com.transactrules.domain.AchievementProgress;
import com.transactrules.repository.AchievementProgressRepository;
import com.transactrules.service.AchievementProgressService;
import com.transactrules.service.dto.AchievementProgressDTO;
import com.transactrules.service.mapper.AchievementProgressMapper;
import com.transactrules.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static com.transactrules.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.transactrules.domain.enumeration.AchievementStatus;
/**
 * Integration tests for the {@link AchievementProgressResource} REST controller.
 */
@SpringBootTest(classes = GamifierApp.class)
public class AchievementProgressResourceIT {

    private static final AchievementStatus DEFAULT_STATUS = AchievementStatus.PENDING;
    private static final AchievementStatus UPDATED_STATUS = AchievementStatus.COMPLETE;

    @Autowired
    private AchievementProgressRepository achievementProgressRepository;

    @Mock
    private AchievementProgressRepository achievementProgressRepositoryMock;

    @Autowired
    private AchievementProgressMapper achievementProgressMapper;

    @Mock
    private AchievementProgressService achievementProgressServiceMock;

    @Autowired
    private AchievementProgressService achievementProgressService;

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

    private MockMvc restAchievementProgressMockMvc;

    private AchievementProgress achievementProgress;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AchievementProgressResource achievementProgressResource = new AchievementProgressResource(achievementProgressService);
        this.restAchievementProgressMockMvc = MockMvcBuilders.standaloneSetup(achievementProgressResource)
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
    public static AchievementProgress createEntity(EntityManager em) {
        AchievementProgress achievementProgress = new AchievementProgress()
            .status(DEFAULT_STATUS);
        return achievementProgress;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AchievementProgress createUpdatedEntity(EntityManager em) {
        AchievementProgress achievementProgress = new AchievementProgress()
            .status(UPDATED_STATUS);
        return achievementProgress;
    }

    @BeforeEach
    public void initTest() {
        achievementProgress = createEntity(em);
    }

    @Test
    @Transactional
    public void createAchievementProgress() throws Exception {
        int databaseSizeBeforeCreate = achievementProgressRepository.findAll().size();

        // Create the AchievementProgress
        AchievementProgressDTO achievementProgressDTO = achievementProgressMapper.toDto(achievementProgress);
        restAchievementProgressMockMvc.perform(post("/api/achievement-progresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(achievementProgressDTO)))
            .andExpect(status().isCreated());

        // Validate the AchievementProgress in the database
        List<AchievementProgress> achievementProgressList = achievementProgressRepository.findAll();
        assertThat(achievementProgressList).hasSize(databaseSizeBeforeCreate + 1);
        AchievementProgress testAchievementProgress = achievementProgressList.get(achievementProgressList.size() - 1);
        assertThat(testAchievementProgress.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createAchievementProgressWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = achievementProgressRepository.findAll().size();

        // Create the AchievementProgress with an existing ID
        achievementProgress.setId(1L);
        AchievementProgressDTO achievementProgressDTO = achievementProgressMapper.toDto(achievementProgress);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAchievementProgressMockMvc.perform(post("/api/achievement-progresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(achievementProgressDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AchievementProgress in the database
        List<AchievementProgress> achievementProgressList = achievementProgressRepository.findAll();
        assertThat(achievementProgressList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAchievementProgresses() throws Exception {
        // Initialize the database
        achievementProgressRepository.saveAndFlush(achievementProgress);

        // Get all the achievementProgressList
        restAchievementProgressMockMvc.perform(get("/api/achievement-progresses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(achievementProgress.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllAchievementProgressesWithEagerRelationshipsIsEnabled() throws Exception {
        AchievementProgressResource achievementProgressResource = new AchievementProgressResource(achievementProgressServiceMock);
        when(achievementProgressServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restAchievementProgressMockMvc = MockMvcBuilders.standaloneSetup(achievementProgressResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restAchievementProgressMockMvc.perform(get("/api/achievement-progresses?eagerload=true"))
        .andExpect(status().isOk());

        verify(achievementProgressServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllAchievementProgressesWithEagerRelationshipsIsNotEnabled() throws Exception {
        AchievementProgressResource achievementProgressResource = new AchievementProgressResource(achievementProgressServiceMock);
            when(achievementProgressServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restAchievementProgressMockMvc = MockMvcBuilders.standaloneSetup(achievementProgressResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restAchievementProgressMockMvc.perform(get("/api/achievement-progresses?eagerload=true"))
        .andExpect(status().isOk());

            verify(achievementProgressServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getAchievementProgress() throws Exception {
        // Initialize the database
        achievementProgressRepository.saveAndFlush(achievementProgress);

        // Get the achievementProgress
        restAchievementProgressMockMvc.perform(get("/api/achievement-progresses/{id}", achievementProgress.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(achievementProgress.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAchievementProgress() throws Exception {
        // Get the achievementProgress
        restAchievementProgressMockMvc.perform(get("/api/achievement-progresses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAchievementProgress() throws Exception {
        // Initialize the database
        achievementProgressRepository.saveAndFlush(achievementProgress);

        int databaseSizeBeforeUpdate = achievementProgressRepository.findAll().size();

        // Update the achievementProgress
        AchievementProgress updatedAchievementProgress = achievementProgressRepository.findById(achievementProgress.getId()).get();
        // Disconnect from session so that the updates on updatedAchievementProgress are not directly saved in db
        em.detach(updatedAchievementProgress);
        updatedAchievementProgress
            .status(UPDATED_STATUS);
        AchievementProgressDTO achievementProgressDTO = achievementProgressMapper.toDto(updatedAchievementProgress);

        restAchievementProgressMockMvc.perform(put("/api/achievement-progresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(achievementProgressDTO)))
            .andExpect(status().isOk());

        // Validate the AchievementProgress in the database
        List<AchievementProgress> achievementProgressList = achievementProgressRepository.findAll();
        assertThat(achievementProgressList).hasSize(databaseSizeBeforeUpdate);
        AchievementProgress testAchievementProgress = achievementProgressList.get(achievementProgressList.size() - 1);
        assertThat(testAchievementProgress.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingAchievementProgress() throws Exception {
        int databaseSizeBeforeUpdate = achievementProgressRepository.findAll().size();

        // Create the AchievementProgress
        AchievementProgressDTO achievementProgressDTO = achievementProgressMapper.toDto(achievementProgress);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAchievementProgressMockMvc.perform(put("/api/achievement-progresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(achievementProgressDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AchievementProgress in the database
        List<AchievementProgress> achievementProgressList = achievementProgressRepository.findAll();
        assertThat(achievementProgressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAchievementProgress() throws Exception {
        // Initialize the database
        achievementProgressRepository.saveAndFlush(achievementProgress);

        int databaseSizeBeforeDelete = achievementProgressRepository.findAll().size();

        // Delete the achievementProgress
        restAchievementProgressMockMvc.perform(delete("/api/achievement-progresses/{id}", achievementProgress.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AchievementProgress> achievementProgressList = achievementProgressRepository.findAll();
        assertThat(achievementProgressList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AchievementProgress.class);
        AchievementProgress achievementProgress1 = new AchievementProgress();
        achievementProgress1.setId(1L);
        AchievementProgress achievementProgress2 = new AchievementProgress();
        achievementProgress2.setId(achievementProgress1.getId());
        assertThat(achievementProgress1).isEqualTo(achievementProgress2);
        achievementProgress2.setId(2L);
        assertThat(achievementProgress1).isNotEqualTo(achievementProgress2);
        achievementProgress1.setId(null);
        assertThat(achievementProgress1).isNotEqualTo(achievementProgress2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AchievementProgressDTO.class);
        AchievementProgressDTO achievementProgressDTO1 = new AchievementProgressDTO();
        achievementProgressDTO1.setId(1L);
        AchievementProgressDTO achievementProgressDTO2 = new AchievementProgressDTO();
        assertThat(achievementProgressDTO1).isNotEqualTo(achievementProgressDTO2);
        achievementProgressDTO2.setId(achievementProgressDTO1.getId());
        assertThat(achievementProgressDTO1).isEqualTo(achievementProgressDTO2);
        achievementProgressDTO2.setId(2L);
        assertThat(achievementProgressDTO1).isNotEqualTo(achievementProgressDTO2);
        achievementProgressDTO1.setId(null);
        assertThat(achievementProgressDTO1).isNotEqualTo(achievementProgressDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(achievementProgressMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(achievementProgressMapper.fromId(null)).isNull();
    }
}
