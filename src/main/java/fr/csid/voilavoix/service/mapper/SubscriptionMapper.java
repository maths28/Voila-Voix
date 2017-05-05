package fr.csid.voilavoix.service.mapper;

import fr.csid.voilavoix.domain.*;
import fr.csid.voilavoix.service.dto.SubscriptionDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Subscription and its DTO SubscriptionDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SubscriptionMapper {

    SubscriptionDTO subscriptionToSubscriptionDTO(Subscription subscription);

    List<SubscriptionDTO> subscriptionsToSubscriptionDTOs(List<Subscription> subscriptions);

    Subscription subscriptionDTOToSubscription(SubscriptionDTO subscriptionDTO);

    List<Subscription> subscriptionDTOsToSubscriptions(List<SubscriptionDTO> subscriptionDTOs);
}
