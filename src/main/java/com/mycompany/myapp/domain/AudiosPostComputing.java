package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A AudiosPostComputing.
 */
@Entity
@Table(name = "audios_post_computing")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AudiosPostComputing implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "id_api")
    private Integer idApi;

    @Column(name = "result")
    private String result;

    @Column(name = "sha_one")
    private String shaOne;

    @Column(name = "jhi_date")
    private LocalDate date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public AudiosPostComputing name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIdApi() {
        return idApi;
    }

    public AudiosPostComputing idApi(Integer idApi) {
        this.idApi = idApi;
        return this;
    }

    public void setIdApi(Integer idApi) {
        this.idApi = idApi;
    }

    public String getResult() {
        return result;
    }

    public AudiosPostComputing result(String result) {
        this.result = result;
        return this;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getShaOne() {
        return shaOne;
    }

    public AudiosPostComputing shaOne(String shaOne) {
        this.shaOne = shaOne;
        return this;
    }

    public void setShaOne(String shaOne) {
        this.shaOne = shaOne;
    }

    public LocalDate getDate() {
        return date;
    }

    public AudiosPostComputing date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AudiosPostComputing audiosPostComputing = (AudiosPostComputing) o;
        if (audiosPostComputing.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), audiosPostComputing.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AudiosPostComputing{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", idApi='" + getIdApi() + "'" +
            ", result='" + getResult() + "'" +
            ", shaOne='" + getShaOne() + "'" +
            ", date='" + getDate() + "'" +
            "}";
    }
}
