package fr.csid.voilavoix.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.csid.voilavoix.domain.Definition;

import fr.csid.voilavoix.repository.DefinitionRepository;
import fr.csid.voilavoix.repository.search.DefinitionSearchRepository;
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
 * REST controller for managing Definition.
 */
@RestController
@RequestMapping("/api")
public class DefinitionResource {

    private final Logger log = LoggerFactory.getLogger(DefinitionResource.class);

    private static final String ENTITY_NAME = "definition";
        
    private final DefinitionRepository definitionRepository;

    private final DefinitionSearchRepository definitionSearchRepository;

    public DefinitionResource(DefinitionRepository definitionRepository, DefinitionSearchRepository definitionSearchRepository) {
        this.definitionRepository = definitionRepository;
        this.definitionSearchRepository = definitionSearchRepository;
    }

    /**
     * POST  /definitions : Create a new definition.
     *
     * @param definition the definition to create
     * @return the ResponseEntity with status 201 (Created) and with body the new definition, or with status 400 (Bad Request) if the definition has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/definitions")
    @Timed
    public ResponseEntity<Definition> createDefinition(@RequestBody Definition definition) throws URISyntaxException {
        log.debug("REST request to save Definition : {}", definition);
        if (definition.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new definition cannot already have an ID")).body(null);
        }
        Definition result = definitionRepository.save(definition);
        definitionSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/definitions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /definitions : Updates an existing definition.
     *
     * @param definition the definition to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated definition,
     * or with status 400 (Bad Request) if the definition is not valid,
     * or with status 500 (Internal Server Error) if the definition couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/definitions")
    @Timed
    public ResponseEntity<Definition> updateDefinition(@RequestBody Definition definition) throws URISyntaxException {
        log.debug("REST request to update Definition : {}", definition);
        if (definition.getId() == null) {
            return createDefinition(definition);
        }
        Definition result = definitionRepository.save(definition);
        definitionSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, definition.getId().toString()))
            .body(result);
    }

    /**
     * GET  /definitions : get all the definitions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of definitions in body
     */
    @GetMapping("/definitions")
    @Timed
    public List<Definition> getAllDefinitions() {
        log.debug("REST request to get all Definitions");
        List<Definition> definitions = definitionRepository.findAll();
        return definitions;
    }

    /**
     * GET  /definitions/:id : get the "id" definition.
     *
     * @param id the id of the definition to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the definition, or with status 404 (Not Found)
     */
    @GetMapping("/definitions/{id}")
    @Timed
    public ResponseEntity<Definition> getDefinition(@PathVariable Long id) {
        log.debug("REST request to get Definition : {}", id);
        Definition definition = definitionRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(definition));
    }

    /**
     * DELETE  /definitions/:id : delete the "id" definition.
     *
     * @param id the id of the definition to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/definitions/{id}")
    @Timed
    public ResponseEntity<Void> deleteDefinition(@PathVariable Long id) {
        log.debug("REST request to delete Definition : {}", id);
        definitionRepository.delete(id);
        definitionSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/definitions?query=:query : search for the definition corresponding
     * to the query.
     *
     * @param query the query of the definition search 
     * @return the result of the search
     */
    @GetMapping("/_search/definitions")
    @Timed
    public List<Definition> searchDefinitions(@RequestParam String query) {
        log.debug("REST request to search Definitions for query {}", query);
        return StreamSupport
            .stream(definitionSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}
