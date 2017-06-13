package fr.csid.voilavoix.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Word.
 */
@Entity
@Table(name = "word")
@Document(indexName = "word")
public class Word implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "word")
    private String word;

    @OneToMany(mappedBy = "word")
    @JsonIgnore
    private Set<Definition> definitions = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public Word word(String word) {
        this.word = word;
        return this;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Set<Definition> getDefinitions() {
        return definitions;
    }

    public Word definitions(Set<Definition> definitions) {
        this.definitions = definitions;
        return this;
    }

    public Word addDefinition(Definition definition) {
        this.definitions.add(definition);
        definition.setWord(this);
        return this;
    }

    public Word removeDefinition(Definition definition) {
        this.definitions.remove(definition);
        definition.setWord(null);
        return this;
    }

    public void setDefinitions(Set<Definition> definitions) {
        this.definitions = definitions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Word word = (Word) o;
        if (word.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, word.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Word{" +
            "id=" + id +
            ", word='" + word + "'" +
            '}';
    }
}
