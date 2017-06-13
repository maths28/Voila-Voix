package fr.csid.voilavoix.repository;

import fr.csid.voilavoix.domain.Definition;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Definition entity.
 */
@SuppressWarnings("unused")
public interface DefinitionRepository extends JpaRepository<Definition,Long> {

}
