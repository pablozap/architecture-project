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
 * A Controles.
 */
@Entity
@Table(name = "controles")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Controles implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotNull
    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "controles")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "activos", "controles", "amenaza", "vulnerabilidad" }, allowSetters = true)
    private Set<Riesgo> riesgos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Controles id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Controles nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public Controles descripcion(String descripcion) {
        this.setDescripcion(descripcion);
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Set<Riesgo> getRiesgos() {
        return this.riesgos;
    }

    public void setRiesgos(Set<Riesgo> riesgos) {
        if (this.riesgos != null) {
            this.riesgos.forEach(i -> i.removeControles(this));
        }
        if (riesgos != null) {
            riesgos.forEach(i -> i.addControles(this));
        }
        this.riesgos = riesgos;
    }

    public Controles riesgos(Set<Riesgo> riesgos) {
        this.setRiesgos(riesgos);
        return this;
    }

    public Controles addRiesgo(Riesgo riesgo) {
        this.riesgos.add(riesgo);
        riesgo.getControles().add(this);
        return this;
    }

    public Controles removeRiesgo(Riesgo riesgo) {
        this.riesgos.remove(riesgo);
        riesgo.getControles().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Controles)) {
            return false;
        }
        return getId() != null && getId().equals(((Controles) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Controles{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            "}";
    }
}
