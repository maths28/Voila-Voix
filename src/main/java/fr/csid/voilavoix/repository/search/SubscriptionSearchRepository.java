package fr.csid.voilavoix.repository.search;

import fr.csid.voilavoix.domain.Subscription;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Subscription entity.
 */
public interface SubscriptionSearchRepository extends ElasticsearchRepository<Subscription, Long> {
}
