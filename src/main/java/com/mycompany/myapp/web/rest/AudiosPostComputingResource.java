package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.AudiosPostComputing;

import com.mycompany.myapp.repository.AudiosPostComputingRepository;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing AudiosPostComputing.
 */
@RestController
@RequestMapping("/api")
public class AudiosPostComputingResource {

    private final Logger log = LoggerFactory.getLogger(AudiosPostComputingResource.class);

    private static final String ENTITY_NAME = "audiosPostComputing";

    private final AudiosPostComputingRepository audiosPostComputingRepository;

    public AudiosPostComputingResource(AudiosPostComputingRepository audiosPostComputingRepository) {
        this.audiosPostComputingRepository = audiosPostComputingRepository;
    }

    /**
     * POST  /audios-post-computings : Create a new audiosPostComputing.
     *
     * @param audiosPostComputing the audiosPostComputing to create
     * @return the ResponseEntity with status 201 (Created) and with body the new audiosPostComputing, or with status 400 (Bad Request) if the audiosPostComputing has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/audios-post-computings")
    @Timed
    public ResponseEntity<AudiosPostComputing> createAudiosPostComputing(@RequestBody AudiosPostComputing audiosPostComputing) throws URISyntaxException {
        log.debug("REST request to save AudiosPostComputing : {}", audiosPostComputing);
        if (audiosPostComputing.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new audiosPostComputing cannot already have an ID")).body(null);
        }
        AudiosPostComputing result = audiosPostComputingRepository.save(audiosPostComputing);
        return ResponseEntity.created(new URI("/api/audios-post-computings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /audios-post-computings : Updates an existing audiosPostComputing.
     *
     * @param audiosPostComputing the audiosPostComputing to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated audiosPostComputing,
     * or with status 400 (Bad Request) if the audiosPostComputing is not valid,
     * or with status 500 (Internal Server Error) if the audiosPostComputing couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/audios-post-computings")
    @Timed
    public ResponseEntity<AudiosPostComputing> updateAudiosPostComputing(@RequestBody AudiosPostComputing audiosPostComputing) throws URISyntaxException {
        log.debug("REST request to update AudiosPostComputing : {}", audiosPostComputing);
        if (audiosPostComputing.getId() == null) {
            return createAudiosPostComputing(audiosPostComputing);
        }
        AudiosPostComputing result = audiosPostComputingRepository.save(audiosPostComputing);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, audiosPostComputing.getId().toString()))
            .body(result);
    }

    /**
     * GET  /audios-post-computings : get all the audiosPostComputings.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of audiosPostComputings in body
     */
    @GetMapping("/audios-post-computings")
    @Timed
    public List<AudiosPostComputing> getAllAudiosPostComputings() {
        log.debug("REST request to get all AudiosPostComputings");
        return audiosPostComputingRepository.findAll();
    }

    /**
     * GET  /audios-post-computings/:id : get the "id" audiosPostComputing.
     *
     * @param id the id of the audiosPostComputing to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the audiosPostComputing, or with status 404 (Not Found)
     */
    @GetMapping("/audios-post-computings/{id}")
    @Timed
    public ResponseEntity<AudiosPostComputing> getAudiosPostComputing(@PathVariable Long id) {
        log.debug("REST request to get AudiosPostComputing : {}", id);
        AudiosPostComputing audiosPostComputing = audiosPostComputingRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(audiosPostComputing));
    }

    /**
     * DELETE  /audios-post-computings/:id : delete the "id" audiosPostComputing.
     *
     * @param id the id of the audiosPostComputing to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/audios-post-computings/{id}")
    @Timed
    public ResponseEntity<Void> deleteAudiosPostComputing(@PathVariable Long id) {
        log.debug("REST request to delete AudiosPostComputing : {}", id);
        audiosPostComputingRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
