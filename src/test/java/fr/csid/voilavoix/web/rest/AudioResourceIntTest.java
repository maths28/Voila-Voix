package fr.csid.voilavoix.web.rest;

import fr.csid.voilavoix.VoilaVoixApp;

import fr.csid.voilavoix.domain.Audio;
import fr.csid.voilavoix.repository.AudioRepository;
import fr.csid.voilavoix.repository.search.AudioSearchRepository;
import fr.csid.voilavoix.service.dto.AudioDTO;
import fr.csid.voilavoix.service.mapper.AudioMapper;

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
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AudioResource REST controller.
 *
 * @see AudioResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VoilaVoixApp.class)
public class AudioResourceIntTest {

    private static final byte[] DEFAULT_FILE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FILE = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_FILE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FILE_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private AudioRepository audioRepository;

    @Autowired
    private AudioMapper audioMapper;

    @Autowired
    private AudioSearchRepository audioSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restAudioMockMvc;

    private Audio audio;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            AudioResource audioResource = new AudioResource(audioRepository, audioMapper, audioSearchRepository);
        this.restAudioMockMvc = MockMvcBuilders.standaloneSetup(audioResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Audio createEntity(EntityManager em) {
        Audio audio = new Audio()
                .file(DEFAULT_FILE)
                .fileContentType(DEFAULT_FILE_CONTENT_TYPE)
                .name(DEFAULT_NAME);
        return audio;
    }

    @Before
    public void initTest() {
        audioSearchRepository.deleteAll();
        audio = createEntity(em);
    }

    @Test
    @Transactional
    public void createAudio() throws Exception {
        int databaseSizeBeforeCreate = audioRepository.findAll().size();

        // Create the Audio
        AudioDTO audioDTO = audioMapper.audioToAudioDTO(audio);

        restAudioMockMvc.perform(post("/api/audios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(audioDTO)))
            .andExpect(status().isCreated());

        // Validate the Audio in the database
        List<Audio> audioList = audioRepository.findAll();
        assertThat(audioList).hasSize(databaseSizeBeforeCreate + 1);
        Audio testAudio = audioList.get(audioList.size() - 1);
        assertThat(testAudio.getFile()).isEqualTo(DEFAULT_FILE);
        assertThat(testAudio.getFileContentType()).isEqualTo(DEFAULT_FILE_CONTENT_TYPE);
        assertThat(testAudio.getName()).isEqualTo(DEFAULT_NAME);

        // Validate the Audio in Elasticsearch
        Audio audioEs = audioSearchRepository.findOne(testAudio.getId());
        assertThat(audioEs).isEqualToComparingFieldByField(testAudio);
    }

    @Test
    @Transactional
    public void createAudioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = audioRepository.findAll().size();

        // Create the Audio with an existing ID
        Audio existingAudio = new Audio();
        existingAudio.setId(1L);
        AudioDTO existingAudioDTO = audioMapper.audioToAudioDTO(existingAudio);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAudioMockMvc.perform(post("/api/audios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingAudioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Audio> audioList = audioRepository.findAll();
        assertThat(audioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAudios() throws Exception {
        // Initialize the database
        audioRepository.saveAndFlush(audio);

        // Get all the audioList
        restAudioMockMvc.perform(get("/api/audios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(audio.getId().intValue())))
            .andExpect(jsonPath("$.[*].fileContentType").value(hasItem(DEFAULT_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].file").value(hasItem(Base64Utils.encodeToString(DEFAULT_FILE))))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getAudio() throws Exception {
        // Initialize the database
        audioRepository.saveAndFlush(audio);

        // Get the audio
        restAudioMockMvc.perform(get("/api/audios/{id}", audio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(audio.getId().intValue()))
            .andExpect(jsonPath("$.fileContentType").value(DEFAULT_FILE_CONTENT_TYPE))
            .andExpect(jsonPath("$.file").value(Base64Utils.encodeToString(DEFAULT_FILE)))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAudio() throws Exception {
        // Get the audio
        restAudioMockMvc.perform(get("/api/audios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAudio() throws Exception {
        // Initialize the database
        audioRepository.saveAndFlush(audio);
        audioSearchRepository.save(audio);
        int databaseSizeBeforeUpdate = audioRepository.findAll().size();

        // Update the audio
        Audio updatedAudio = audioRepository.findOne(audio.getId());
        updatedAudio
                .file(UPDATED_FILE)
                .fileContentType(UPDATED_FILE_CONTENT_TYPE)
                .name(UPDATED_NAME);
        AudioDTO audioDTO = audioMapper.audioToAudioDTO(updatedAudio);

        restAudioMockMvc.perform(put("/api/audios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(audioDTO)))
            .andExpect(status().isOk());

        // Validate the Audio in the database
        List<Audio> audioList = audioRepository.findAll();
        assertThat(audioList).hasSize(databaseSizeBeforeUpdate);
        Audio testAudio = audioList.get(audioList.size() - 1);
        assertThat(testAudio.getFile()).isEqualTo(UPDATED_FILE);
        assertThat(testAudio.getFileContentType()).isEqualTo(UPDATED_FILE_CONTENT_TYPE);
        assertThat(testAudio.getName()).isEqualTo(UPDATED_NAME);

        // Validate the Audio in Elasticsearch
        Audio audioEs = audioSearchRepository.findOne(testAudio.getId());
        assertThat(audioEs).isEqualToComparingFieldByField(testAudio);
    }

    @Test
    @Transactional
    public void updateNonExistingAudio() throws Exception {
        int databaseSizeBeforeUpdate = audioRepository.findAll().size();

        // Create the Audio
        AudioDTO audioDTO = audioMapper.audioToAudioDTO(audio);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAudioMockMvc.perform(put("/api/audios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(audioDTO)))
            .andExpect(status().isCreated());

        // Validate the Audio in the database
        List<Audio> audioList = audioRepository.findAll();
        assertThat(audioList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAudio() throws Exception {
        // Initialize the database
        audioRepository.saveAndFlush(audio);
        audioSearchRepository.save(audio);
        int databaseSizeBeforeDelete = audioRepository.findAll().size();

        // Get the audio
        restAudioMockMvc.perform(delete("/api/audios/{id}", audio.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean audioExistsInEs = audioSearchRepository.exists(audio.getId());
        assertThat(audioExistsInEs).isFalse();

        // Validate the database is empty
        List<Audio> audioList = audioRepository.findAll();
        assertThat(audioList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchAudio() throws Exception {
        // Initialize the database
        audioRepository.saveAndFlush(audio);
        audioSearchRepository.save(audio);

        // Search the audio
        restAudioMockMvc.perform(get("/api/_search/audios?query=id:" + audio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(audio.getId().intValue())))
            .andExpect(jsonPath("$.[*].fileContentType").value(hasItem(DEFAULT_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].file").value(hasItem(Base64Utils.encodeToString(DEFAULT_FILE))))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Audio.class);
    }
}
