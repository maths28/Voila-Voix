package fr.csid.voilavoix.service.dto;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the News entity.
 */
public class NewsDTO implements Serializable {

    private Long id;

    private String title;

    private LocalDate newsDate;

    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public LocalDate getNewsDate() {
        return newsDate;
    }

    public void setNewsDate(LocalDate newsDate) {
        this.newsDate = newsDate;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        NewsDTO newsDTO = (NewsDTO) o;

        if ( ! Objects.equals(id, newsDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "NewsDTO{" +
            "id=" + id +
            ", title='" + title + "'" +
            ", newsDate='" + newsDate + "'" +
            ", description='" + description + "'" +
            '}';
    }
}
