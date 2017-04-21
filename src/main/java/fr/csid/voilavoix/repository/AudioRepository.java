package fr.csid.voilavoix.repository;

import fr.csid.voilavoix.domain.Audio;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Audio entity.
 */
@SuppressWarnings("unused")
public interface AudioRepository extends JpaRepository<Audio,Long> {

}
