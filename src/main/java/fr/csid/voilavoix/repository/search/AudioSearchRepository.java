package fr.csid.voilavoix.repository.search;

import fr.csid.voilavoix.domain.Audio;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Audio entity.
 */
public interface AudioSearchRepository extends ElasticsearchRepository<Audio, Long> {
}
