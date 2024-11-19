package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.Confidencialidad;
import com.mycompany.myapp.domain.enumeration.Criticidad;
import com.mycompany.myapp.domain.enumeration.Disponibilidad;
import com.mycompany.myapp.domain.enumeration.Integridad;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A GrupoActivos.
 */
@Entity
@Table(name = "grupo_activos")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class GrupoActivos implements Serializable {

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
    @Enumerated(EnumType.STRING)
    @Column(name = "disponibilidad", nullable = false)
    private Disponibilidad disponibilidad;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "integridad", nullable = false)
    private Integridad integridad;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "confidencialidad", nullable = false)
    private Confidencialidad confidencialidad;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "criticidad", nullable = false)
    private Criticidad criticidad;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "grupoActivo")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "grupoActivo" }, allowSetters = true)
    private Set<ActivoInformacion> activos = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "activos")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "activos", "controles", "amenaza", "vulnerabilidad" }, allowSetters = true)
    private Set<Riesgo> riesgos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public GrupoActivos id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public GrupoActivos nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Disponibilidad getDisponibilidad() {
        return this.disponibilidad;
    }

    public GrupoActivos disponibilidad(Disponibilidad disponibilidad) {
        this.setDisponibilidad(disponibilidad);
        return this;
    }

    public void setDisponibilidad(Disponibilidad disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public Integridad getIntegridad() {
        return this.integridad;
    }

    public GrupoActivos integridad(Integridad integridad) {
        this.setIntegridad(integridad);
        return this;
    }

    public void setIntegridad(Integridad integridad) {
        this.integridad = integridad;
    }

    public Confidencialidad getConfidencialidad() {
        return this.confidencialidad;
    }

    public GrupoActivos confidencialidad(Confidencialidad confidencialidad) {
        this.setConfidencialidad(confidencialidad);
        return this;
    }

    public void setConfidencialidad(Confidencialidad confidencialidad) {
        this.confidencialidad = confidencialidad;
    }

    public Criticidad getCriticidad() {
        return this.criticidad;
    }

    public GrupoActivos criticidad(Criticidad criticidad) {
        this.setCriticidad(criticidad);
        return this;
    }

    public void setCriticidad(Criticidad criticidad) {
        this.criticidad = criticidad;
    }

    public Set<ActivoInformacion> getActivos() {
        return this.activos;
    }

    public void setActivos(Set<ActivoInformacion> activoInformacions) {
        if (this.activos != null) {
            this.activos.forEach(i -> i.setGrupoActivo(null));
        }
        if (activoInformacions != null) {
            activoInformacions.forEach(i -> i.setGrupoActivo(this));
        }
        this.activos = activoInformacions;
    }

    public GrupoActivos activos(Set<ActivoInformacion> activoInformacions) {
        this.setActivos(activoInformacions);
        return this;
    }

    public GrupoActivos addActivos(ActivoInformacion activoInformacion) {
        this.activos.add(activoInformacion);
        activoInformacion.setGrupoActivo(this);
        return this;
    }

    public GrupoActivos removeActivos(ActivoInformacion activoInformacion) {
        this.activos.remove(activoInformacion);
        activoInformacion.setGrupoActivo(null);
        return this;
    }

    public Set<Riesgo> getRiesgos() {
        return this.riesgos;
    }

    public void setRiesgos(Set<Riesgo> riesgos) {
        if (this.riesgos != null) {
            this.riesgos.forEach(i -> i.removeActivos(this));
        }
        if (riesgos != null) {
            riesgos.forEach(i -> i.addActivos(this));
        }
        this.riesgos = riesgos;
    }

    public GrupoActivos riesgos(Set<Riesgo> riesgos) {
        this.setRiesgos(riesgos);
        return this;
    }

    public GrupoActivos addRiesgos(Riesgo riesgo) {
        this.riesgos.add(riesgo);
        riesgo.getActivos().add(this);
        return this;
    }

    public GrupoActivos removeRiesgos(Riesgo riesgo) {
        this.riesgos.remove(riesgo);
        riesgo.getActivos().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GrupoActivos)) {
            return false;
        }
        return getId() != null && getId().equals(((GrupoActivos) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GrupoActivos{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", disponibilidad='" + getDisponibilidad() + "'" +
            ", integridad='" + getIntegridad() + "'" +
            ", confidencialidad='" + getConfidencialidad() + "'" +
            ", criticidad='" + getCriticidad() + "'" +
            "}";
    }
}
