package fr.csid.voilavoix.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.csid.voilavoix.domain.Word;

import fr.csid.voilavoix.repository.WordRepository;
import fr.csid.voilavoix.repository.search.WordSearchRepository;
import fr.csid.voilavoix.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Word.
 */
@RestController
@RequestMapping("/api")
public class WordResource {

    private final Logger log = LoggerFactory.getLogger(WordResource.class);

    private static final String ENTITY_NAME = "word";
        
    private final WordRepository wordRepository;

    private final WordSearchRepository wordSearchRepository;

    public WordResource(WordRepository wordRepository, WordSearchRepository wordSearchRepository) {
        this.wordRepository = wordRepository;
        this.wordSearchRepository = wordSearchRepository;
    }

    /**
     * POST  /words : Create a new word.
     *
     * @param word the word to create
     * @return the ResponseEntity with status 201 (Created) and with body the new word, or with status 400 (Bad Request) if the word has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/words")
    @Timed
    public ResponseEntity<Word> createWord(@RequestBody Word word) throws URISyntaxException {
        log.debug("REST request to save Word : {}", word);
        if (word.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new word cannot already have an ID")).body(null);
        }
        Word result = wordRepository.save(word);
        wordSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/words/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /words : Updates an existing word.
     *
     * @param word the word to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated word,
     * or with status 400 (Bad Request) if the word is not valid,
     * or with status 500 (Internal Server Error) if the word couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/words")
    @Timed
    public ResponseEntity<Word> updateWord(@RequestBody Word word) throws URISyntaxException {
        log.debug("REST request to update Word : {}", word);
        if (word.getId() == null) {
            return createWord(word);
        }
        Word result = wordRepository.save(word);
        wordSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, word.getId().toString()))
            .body(result);
    }

    /**
     * GET  /words : get all the words.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of words in body
     */
    @GetMapping("/words")
    @Timed
    public List<Word> getAllWords() {
        log.debug("REST request to get all Words");
        List<Word> words = wordRepository.findAll();
        return words;
    }

    /**
     * GET  /words/:id : get the "id" word.
     *
     * @param id the id of the word to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the word, or with status 404 (Not Found)
     */
    @GetMapping("/words/{id}")
    @Timed
    public ResponseEntity<Word> getWord(@PathVariable Long id) {
        log.debug("REST request to get Word : {}", id);
        Word word = wordRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(word));
    }

    /**
     * DELETE  /words/:id : delete the "id" word.
     *
     * @param id the id of the word to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/words/{id}")
    @Timed
    public ResponseEntity<Void> deleteWord(@PathVariable Long id) {
        log.debug("REST request to delete Word : {}", id);
        wordRepository.delete(id);
        wordSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/words?query=:query : search for the word corresponding
     * to the query.
     *
     * @param query the query of the word search 
     * @return the result of the search
     */
    @GetMapping("/_search/words")
    @Timed
    public List<Word> searchWords(@RequestParam String query) {
        log.debug("REST request to search Words for query {}", query);
        return StreamSupport
            .stream(wordSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}
