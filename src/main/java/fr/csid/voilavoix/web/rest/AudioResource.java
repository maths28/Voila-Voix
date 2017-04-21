package fr.csid.voilavoix.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.csid.voilavoix.domain.Audio;

import fr.csid.voilavoix.repository.AudioRepository;
import fr.csid.voilavoix.repository.search.AudioSearchRepository;
import fr.csid.voilavoix.web.rest.util.HeaderUtil;
import fr.csid.voilavoix.service.dto.AudioDTO;
import fr.csid.voilavoix.service.mapper.AudioMapper;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Audio.
 */
@RestController
@RequestMapping("/api")
public class AudioResource {

    private final Logger log = LoggerFactory.getLogger(AudioResource.class);

    private static final String ENTITY_NAME = "audio";
        
    private final AudioRepository audioRepository;

    private final AudioMapper audioMapper;

    private final AudioSearchRepository audioSearchRepository;

    public AudioResource(AudioRepository audioRepository, AudioMapper audioMapper, AudioSearchRepository audioSearchRepository) {
        this.audioRepository = audioRepository;
        this.audioMapper = audioMapper;
        this.audioSearchRepository = audioSearchRepository;
    }

    /**
     * POST  /audios : Create a new audio.
     *
     * @param audioDTO the audioDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new audioDTO, or with status 400 (Bad Request) if the audio has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/audios")
    @Timed
    public ResponseEntity<AudioDTO> createAudio(@RequestBody AudioDTO audioDTO) throws URISyntaxException {
        log.debug("REST request to save Audio : {}", audioDTO);
        if (audioDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new audio cannot already have an ID")).body(null);
        }
        Audio audio = audioMapper.audioDTOToAudio(audioDTO);
        audio = audioRepository.save(audio);
        AudioDTO result = audioMapper.audioToAudioDTO(audio);
        audioSearchRepository.save(audio);
        return ResponseEntity.created(new URI("/api/audios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /audios : Updates an existing audio.
     *
     * @param audioDTO the audioDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated audioDTO,
     * or with status 400 (Bad Request) if the audioDTO is not valid,
     * or with status 500 (Internal Server Error) if the audioDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/audios")
    @Timed
    public ResponseEntity<AudioDTO> updateAudio(@RequestBody AudioDTO audioDTO) throws URISyntaxException {
        log.debug("REST request to update Audio : {}", audioDTO);
        if (audioDTO.getId() == null) {
            return createAudio(audioDTO);
        }
        Audio audio = audioMapper.audioDTOToAudio(audioDTO);
        audio = audioRepository.save(audio);
        AudioDTO result = audioMapper.audioToAudioDTO(audio);
        audioSearchRepository.save(audio);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, audioDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /audios : get all the audios.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of audios in body
     */
    @GetMapping("/audios")
    @Timed
    public List<AudioDTO> getAllAudios() {
        log.debug("REST request to get all Audios");
        List<Audio> audios = audioRepository.findAll();
        return audioMapper.audiosToAudioDTOs(audios);
    }

    /**
     * GET  /audios/:id : get the "id" audio.
     *
     * @param id the id of the audioDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the audioDTO, or with status 404 (Not Found)
     */
    @GetMapping("/audios/{id}")
    @Timed
    public ResponseEntity<AudioDTO> getAudio(@PathVariable Long id) {
        log.debug("REST request to get Audio : {}", id);
        Audio audio = audioRepository.findOne(id);
        AudioDTO audioDTO = audioMapper.audioToAudioDTO(audio);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(audioDTO));
    }

    /**
     * DELETE  /audios/:id : delete the "id" audio.
     *
     * @param id the id of the audioDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/audios/{id}")
    @Timed
    public ResponseEntity<Void> deleteAudio(@PathVariable Long id) {
        log.debug("REST request to delete Audio : {}", id);
        audioRepository.delete(id);
        audioSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/audios?query=:query : search for the audio corresponding
     * to the query.
     *
     * @param query the query of the audio search 
     * @return the result of the search
     */
    @GetMapping("/_search/audios")
    @Timed
    public List<AudioDTO> searchAudios(@RequestParam String query) {
        log.debug("REST request to search Audios for query {}", query);
        return StreamSupport
            .stream(audioSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(audioMapper::audioToAudioDTO)
            .collect(Collectors.toList());
    }


}
