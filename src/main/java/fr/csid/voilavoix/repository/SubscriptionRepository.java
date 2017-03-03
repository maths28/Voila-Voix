package fr.csid.voilavoix.repository;

import fr.csid.voilavoix.domain.Subscription;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Subscription entity.
 */
@SuppressWarnings("unused")
public interface SubscriptionRepository extends JpaRepository<Subscription,Long> {

}
