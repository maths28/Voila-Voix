package fr.csid.voilavoix.repository.search;

import fr.csid.voilavoix.domain.Definition;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Definition entity.
 */
public interface DefinitionSearchRepository extends ElasticsearchRepository<Definition, Long> {
}
