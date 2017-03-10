package fr.csid.voilavoix.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.csid.voilavoix.domain.Subscription;

import fr.csid.voilavoix.repository.SubscriptionRepository;
import fr.csid.voilavoix.repository.search.SubscriptionSearchRepository;
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
 * REST controller for managing Subscription.
 */
@RestController
@RequestMapping("/api")
public class SubscriptionResource {

    private final Logger log = LoggerFactory.getLogger(SubscriptionResource.class);

    private static final String ENTITY_NAME = "subscription";
        
    private final SubscriptionRepository subscriptionRepository;

    private final SubscriptionSearchRepository subscriptionSearchRepository;

    public SubscriptionResource(SubscriptionRepository subscriptionRepository, SubscriptionSearchRepository subscriptionSearchRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.subscriptionSearchRepository = subscriptionSearchRepository;
    }

    /**
     * POST  /subscriptions : Create a new subscription.
     *
     * @param subscription the subscription to create
     * @return the ResponseEntity with status 201 (Created) and with body the new subscription, or with status 400 (Bad Request) if the subscription has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/subscriptions")
    @Timed
    public ResponseEntity<Subscription> createSubscription(@RequestBody Subscription subscription) throws URISyntaxException {
        log.debug("REST request to save Subscription : {}", subscription);
        if (subscription.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new subscription cannot already have an ID")).body(null);
        }
        Subscription result = subscriptionRepository.save(subscription);
        subscriptionSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/subscriptions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /subscriptions : Updates an existing subscription.
     *
     * @param subscription the subscription to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated subscription,
     * or with status 400 (Bad Request) if the subscription is not valid,
     * or with status 500 (Internal Server Error) if the subscription couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/subscriptions")
    @Timed
    public ResponseEntity<Subscription> updateSubscription(@RequestBody Subscription subscription) throws URISyntaxException {
        log.debug("REST request to update Subscription : {}", subscription);
        if (subscription.getId() == null) {
            return createSubscription(subscription);
        }
        Subscription result = subscriptionRepository.save(subscription);
        subscriptionSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, subscription.getId().toString()))
            .body(result);
    }

    /**
     * GET  /subscriptions : get all the subscriptions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of subscriptions in body
     */
    @GetMapping("/subscriptions")
    @Timed
    public List<Subscription> getAllSubscriptions() {
        log.debug("REST request to get all Subscriptions");
        List<Subscription> subscriptions = subscriptionRepository.findAll();
        return subscriptions;
    }

    /**
     * GET  /subscriptions/:id : get the "id" subscription.
     *
     * @param id the id of the subscription to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the subscription, or with status 404 (Not Found)
     */
    @GetMapping("/subscriptions/{id}")
    @Timed
    public ResponseEntity<Subscription> getSubscription(@PathVariable Long id) {
        log.debug("REST request to get Subscription : {}", id);
        Subscription subscription = subscriptionRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(subscription));
    }

    /**
     * DELETE  /subscriptions/:id : delete the "id" subscription.
     *
     * @param id the id of the subscription to delete
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
    public List<Subscription> searchSubscriptions(@RequestParam String query) {
        log.debug("REST request to search Subscriptions for query {}", query);
        return StreamSupport
            .stream(subscriptionSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}
