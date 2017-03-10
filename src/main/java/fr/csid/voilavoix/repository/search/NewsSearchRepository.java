package fr.csid.voilavoix.repository.search;

import fr.csid.voilavoix.domain.News;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the News entity.
 */
public interface NewsSearchRepository extends ElasticsearchRepository<News, Long> {
}
