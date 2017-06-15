package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.VoilaVoix2App;

import com.mycompany.myapp.domain.AudiosPostComputing;
import com.mycompany.myapp.repository.AudiosPostComputingRepository;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AudiosPostComputingResource REST controller.
 *
 * @see AudiosPostComputingResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VoilaVoix2App.class)
public class AudiosPostComputingResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_ID_API = 1;
    private static final Integer UPDATED_ID_API = 2;

    private static final String DEFAULT_RESULT = "AAAAAAAAAA";
    private static final String UPDATED_RESULT = "BBBBBBBBBB";

    private static final String DEFAULT_SHA_ONE = "AAAAAAAAAA";
    private static final String UPDATED_SHA_ONE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private AudiosPostComputingRepository audiosPostComputingRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAudiosPostComputingMockMvc;

    private AudiosPostComputing audiosPostComputing;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AudiosPostComputingResource audiosPostComputingResource = new AudiosPostComputingResource(audiosPostComputingRepository);
        this.restAudiosPostComputingMockMvc = MockMvcBuilders.standaloneSetup(audiosPostComputingResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AudiosPostComputing createEntity(EntityManager em) {
        AudiosPostComputing audiosPostComputing = new AudiosPostComputing()
            .name(DEFAULT_NAME)
            .idApi(DEFAULT_ID_API)
            .result(DEFAULT_RESULT)
            .shaOne(DEFAULT_SHA_ONE)
            .date(DEFAULT_DATE);
        return audiosPostComputing;
    }

    @Before
    public void initTest() {
        audiosPostComputing = createEntity(em);
    }

    @Test
    @Transactional
    public void createAudiosPostComputing() throws Exception {
        int databaseSizeBeforeCreate = audiosPostComputingRepository.findAll().size();

        // Create the AudiosPostComputing
        restAudiosPostComputingMockMvc.perform(post("/api/audios-post-computings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(audiosPostComputing)))
            .andExpect(status().isCreated());

        // Validate the AudiosPostComputing in the database
        List<AudiosPostComputing> audiosPostComputingList = audiosPostComputingRepository.findAll();
        assertThat(audiosPostComputingList).hasSize(databaseSizeBeforeCreate + 1);
        AudiosPostComputing testAudiosPostComputing = audiosPostComputingList.get(audiosPostComputingList.size() - 1);
        assertThat(testAudiosPostComputing.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAudiosPostComputing.getIdApi()).isEqualTo(DEFAULT_ID_API);
        assertThat(testAudiosPostComputing.getResult()).isEqualTo(DEFAULT_RESULT);
        assertThat(testAudiosPostComputing.getShaOne()).isEqualTo(DEFAULT_SHA_ONE);
        assertThat(testAudiosPostComputing.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createAudiosPostComputingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = audiosPostComputingRepository.findAll().size();

        // Create the AudiosPostComputing with an existing ID
        audiosPostComputing.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAudiosPostComputingMockMvc.perform(post("/api/audios-post-computings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(audiosPostComputing)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<AudiosPostComputing> audiosPostComputingList = audiosPostComputingRepository.findAll();
        assertThat(audiosPostComputingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAudiosPostComputings() throws Exception {
        // Initialize the database
        audiosPostComputingRepository.saveAndFlush(audiosPostComputing);

        // Get all the audiosPostComputingList
        restAudiosPostComputingMockMvc.perform(get("/api/audios-post-computings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(audiosPostComputing.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].idApi").value(hasItem(DEFAULT_ID_API)))
            .andExpect(jsonPath("$.[*].result").value(hasItem(DEFAULT_RESULT.toString())))
            .andExpect(jsonPath("$.[*].shaOne").value(hasItem(DEFAULT_SHA_ONE.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }

    @Test
    @Transactional
    public void getAudiosPostComputing() throws Exception {
        // Initialize the database
        audiosPostComputingRepository.saveAndFlush(audiosPostComputing);

        // Get the audiosPostComputing
        restAudiosPostComputingMockMvc.perform(get("/api/audios-post-computings/{id}", audiosPostComputing.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(audiosPostComputing.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.idApi").value(DEFAULT_ID_API))
            .andExpect(jsonPath("$.result").value(DEFAULT_RESULT.toString()))
            .andExpect(jsonPath("$.shaOne").value(DEFAULT_SHA_ONE.toString()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAudiosPostComputing() throws Exception {
        // Get the audiosPostComputing
        restAudiosPostComputingMockMvc.perform(get("/api/audios-post-computings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAudiosPostComputing() throws Exception {
        // Initialize the database
        audiosPostComputingRepository.saveAndFlush(audiosPostComputing);
        int databaseSizeBeforeUpdate = audiosPostComputingRepository.findAll().size();

        // Update the audiosPostComputing
        AudiosPostComputing updatedAudiosPostComputing = audiosPostComputingRepository.findOne(audiosPostComputing.getId());
        updatedAudiosPostComputing
            .name(UPDATED_NAME)
            .idApi(UPDATED_ID_API)
            .result(UPDATED_RESULT)
            .shaOne(UPDATED_SHA_ONE)
            .date(UPDATED_DATE);

        restAudiosPostComputingMockMvc.perform(put("/api/audios-post-computings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAudiosPostComputing)))
            .andExpect(status().isOk());

        // Validate the AudiosPostComputing in the database
        List<AudiosPostComputing> audiosPostComputingList = audiosPostComputingRepository.findAll();
        assertThat(audiosPostComputingList).hasSize(databaseSizeBeforeUpdate);
        AudiosPostComputing testAudiosPostComputing = audiosPostComputingList.get(audiosPostComputingList.size() - 1);
        assertThat(testAudiosPostComputing.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAudiosPostComputing.getIdApi()).isEqualTo(UPDATED_ID_API);
        assertThat(testAudiosPostComputing.getResult()).isEqualTo(UPDATED_RESULT);
        assertThat(testAudiosPostComputing.getShaOne()).isEqualTo(UPDATED_SHA_ONE);
        assertThat(testAudiosPostComputing.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingAudiosPostComputing() throws Exception {
        int databaseSizeBeforeUpdate = audiosPostComputingRepository.findAll().size();

        // Create the AudiosPostComputing

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAudiosPostComputingMockMvc.perform(put("/api/audios-post-computings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(audiosPostComputing)))
            .andExpect(status().isCreated());

        // Validate the AudiosPostComputing in the database
        List<AudiosPostComputing> audiosPostComputingList = audiosPostComputingRepository.findAll();
        assertThat(audiosPostComputingList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAudiosPostComputing() throws Exception {
        // Initialize the database
        audiosPostComputingRepository.saveAndFlush(audiosPostComputing);
        int databaseSizeBeforeDelete = audiosPostComputingRepository.findAll().size();

        // Get the audiosPostComputing
        restAudiosPostComputingMockMvc.perform(delete("/api/audios-post-computings/{id}", audiosPostComputing.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AudiosPostComputing> audiosPostComputingList = audiosPostComputingRepository.findAll();
        assertThat(audiosPostComputingList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AudiosPostComputing.class);
        AudiosPostComputing audiosPostComputing1 = new AudiosPostComputing();
        audiosPostComputing1.setId(1L);
        AudiosPostComputing audiosPostComputing2 = new AudiosPostComputing();
        audiosPostComputing2.setId(audiosPostComputing1.getId());
        assertThat(audiosPostComputing1).isEqualTo(audiosPostComputing2);
        audiosPostComputing2.setId(2L);
        assertThat(audiosPostComputing1).isNotEqualTo(audiosPostComputing2);
        audiosPostComputing1.setId(null);
        assertThat(audiosPostComputing1).isNotEqualTo(audiosPostComputing2);
    }
}
