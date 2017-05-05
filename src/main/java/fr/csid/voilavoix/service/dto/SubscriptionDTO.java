package fr.csid.voilavoix.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Subscription entity.
 */
public class SubscriptionDTO implements Serializable {

    private Long id;

    private String label;

    private String description;

    private String api;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SubscriptionDTO subscriptionDTO = (SubscriptionDTO) o;

        if ( ! Objects.equals(id, subscriptionDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SubscriptionDTO{" +
            "id=" + id +
            ", label='" + label + "'" +
            ", description='" + description + "'" +
            ", api='" + api + "'" +
            '}';
    }
}
