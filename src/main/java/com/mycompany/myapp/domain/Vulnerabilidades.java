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
 * A Vulnerabilidades.
 */
@Entity
@Table(name = "vulnerabilidades")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Vulnerabilidades implements Serializable {

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "vulnerabilidad")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "activos", "controles", "amenaza", "vulnerabilidad" }, allowSetters = true)
    private Set<Riesgo> riesgos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Vulnerabilidades id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Vulnerabilidades nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public Vulnerabilidades descripcion(String descripcion) {
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
            this.riesgos.forEach(i -> i.setVulnerabilidad(null));
        }
        if (riesgos != null) {
            riesgos.forEach(i -> i.setVulnerabilidad(this));
        }
        this.riesgos = riesgos;
    }

    public Vulnerabilidades riesgos(Set<Riesgo> riesgos) {
        this.setRiesgos(riesgos);
        return this;
    }

    public Vulnerabilidades addRiesgo(Riesgo riesgo) {
        this.riesgos.add(riesgo);
        riesgo.setVulnerabilidad(this);
        return this;
    }

    public Vulnerabilidades removeRiesgo(Riesgo riesgo) {
        this.riesgos.remove(riesgo);
        riesgo.setVulnerabilidad(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vulnerabilidades)) {
            return false;
        }
        return getId() != null && getId().equals(((Vulnerabilidades) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Vulnerabilidades{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            "}";
    }
}
