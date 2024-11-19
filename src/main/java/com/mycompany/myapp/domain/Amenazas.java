package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Amenazas.
 */
@Entity
@Table(name = "amenazas")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Amenazas implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "amenaza")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "activos", "controles", "amenaza", "vulnerabilidad" }, allowSetters = true)
    private Set<Riesgo> riesgos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Amenazas id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Amenazas nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<Riesgo> getRiesgos() {
        return this.riesgos;
    }

    public void setRiesgos(Set<Riesgo> riesgos) {
        if (this.riesgos != null) {
            this.riesgos.forEach(i -> i.setAmenaza(null));
        }
        if (riesgos != null) {
            riesgos.forEach(i -> i.setAmenaza(this));
        }
        this.riesgos = riesgos;
    }

    public Amenazas riesgos(Set<Riesgo> riesgos) {
        this.setRiesgos(riesgos);
        return this;
    }

    public Amenazas addRiesgo(Riesgo riesgo) {
        this.riesgos.add(riesgo);
        riesgo.setAmenaza(this);
        return this;
    }

    public Amenazas removeRiesgo(Riesgo riesgo) {
        this.riesgos.remove(riesgo);
        riesgo.setAmenaza(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Amenazas)) {
            return false;
        }
        return getId() != null && getId().equals(((Amenazas) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Amenazas{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            "}";
    }
}
