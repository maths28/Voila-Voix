package fr.csid.voilavoix.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.csid.voilavoix.domain.Subscription;

import fr.csid.voilavoix.repository.SubscriptionRepository;
import fr.csid.voilavoix.repository.search.SubscriptionSearchRepository;
import fr.csid.voilavoix.web.rest.util.HeaderUtil;
import fr.csid.voilavoix.service.dto.SubscriptionDTO;
import fr.csid.voilavoix.service.mapper.SubscriptionMapper;
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
 * REST controller for managing Subscription.
 */
@RestController
@RequestMapping("/api")
public class SubscriptionResource {

    private final Logger log = LoggerFactory.getLogger(SubscriptionResource.class);

    private static final String ENTITY_NAME = "subscription";
        
    private final SubscriptionRepository subscriptionRepository;

    private final SubscriptionMapper subscriptionMapper;

    private final SubscriptionSearchRepository subscriptionSearchRepository;

    public SubscriptionResource(SubscriptionRepository subscriptionRepository, SubscriptionMapper subscriptionMapper, SubscriptionSearchRepository subscriptionSearchRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.subscriptionMapper = subscriptionMapper;
        this.subscriptionSearchRepository = subscriptionSearchRepository;
    }

    /**
     * POST  /subscriptions : Create a new subscription.
     *
     * @param subscriptionDTO the subscriptionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new subscriptionDTO, or with status 400 (Bad Request) if the subscription has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/subscriptions")
    @Timed
    public ResponseEntity<SubscriptionDTO> createSubscription(@RequestBody SubscriptionDTO subscriptionDTO) throws URISyntaxException {
        log.debug("REST request to save Subscription : {}", subscriptionDTO);
        if (subscriptionDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new subscription cannot already have an ID")).body(null);
        }
        Subscription subscription = subscriptionMapper.subscriptionDTOToSubscription(subscriptionDTO);
        subscription = subscriptionRepository.save(subscription);
        SubscriptionDTO result = subscriptionMapper.subscriptionToSubscriptionDTO(subscription);
        subscriptionSearchRepository.save(subscription);
        return ResponseEntity.created(new URI("/api/subscriptions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /subscriptions : Updates an existing subscription.
     *
     * @param subscriptionDTO the subscriptionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated subscriptionDTO,
     * or with status 400 (Bad Request) if the subscriptionDTO is not valid,
     * or with status 500 (Internal Server Error) if the subscriptionDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/subscriptions")
    @Timed
    public ResponseEntity<SubscriptionDTO> updateSubscription(@RequestBody SubscriptionDTO subscriptionDTO) throws URISyntaxException {
        log.debug("REST request to update Subscription : {}", subscriptionDTO);
        if (subscriptionDTO.getId() == null) {
            return createSubscription(subscriptionDTO);
        }
        Subscription subscription = subscriptionMapper.subscriptionDTOToSubscription(subscriptionDTO);
        subscription = subscriptionRepository.save(subscription);
        SubscriptionDTO result = subscriptionMapper.subscriptionToSubscriptionDTO(subscription);
        subscriptionSearchRepository.save(subscription);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, subscriptionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /subscriptions : get all the subscriptions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of subscriptions in body
     */
    @GetMapping("/subscriptions")
    @Timed
    public List<SubscriptionDTO> getAllSubscriptions() {
        log.debug("REST request to get all Subscriptions");
        List<Subscription> subscriptions = subscriptionRepository.findAll();
        return subscriptionMapper.subscriptionsToSubscriptionDTOs(subscriptions);
    }

    /**
     * GET  /subscriptions/:id : get the "id" subscription.
     *
     * @param id the id of the subscriptionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the subscriptionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/subscriptions/{id}")
    @Timed
    public ResponseEntity<SubscriptionDTO> getSubscription(@PathVariable Long id) {
        log.debug("REST request to get Subscription : {}", id);
        Subscription subscription = subscriptionRepository.findOne(id);
        SubscriptionDTO subscriptionDTO = subscriptionMapper.subscriptionToSubscriptionDTO(subscription);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(subscriptionDTO));
    }

    /**
     * DELETE  /subscriptions/:id : delete the "id" subscription.
     *
     * @param id the id of the subscriptionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/subscriptions/{id}")
    @Timed
    public ResponseEntity<Void> deleteSubscription(@PathVariable Long id) {
        log.debug("REST request to delete Subscription : {}", id);
        subscriptionRepository.delete(id);
        subscriptionSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/subscriptions?query=:query : search for the subscription corresponding
     * to the query.
     *
     * @param query the query of the subscription search 
     * @return the result of the search
     */
    @GetMapping("/_search/subscriptions")
    @Timed
    public List<SubscriptionDTO> searchSubscriptions(@RequestParam String query) {
        log.debug("REST request to search Subscriptions for query {}", query);
        return StreamSupport
            .stream(subscriptionSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(subscriptionMapper::subscriptionToSubscriptionDTO)
            .collect(Collectors.toList());
    }


}
