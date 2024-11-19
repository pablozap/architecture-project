package com.mycompany.myapp.service.dto;

import com.mycompany.myapp.domain.enumeration.Confidencialidad;
import com.mycompany.myapp.domain.enumeration.Criticidad;
import com.mycompany.myapp.domain.enumeration.Disponibilidad;
import com.mycompany.myapp.domain.enumeration.Integridad;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.GrupoActivos} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class GrupoActivosDTO implements Serializable {

    private Long id;

    @NotNull
    private String nombre;

    @NotNull
    private Disponibilidad disponibilidad;

    @NotNull
    private Integridad integridad;

    @NotNull
    private Confidencialidad confidencialidad;

    @NotNull
    private Criticidad criticidad;

    private Set<RiesgoDTO> riesgos = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Disponibilidad getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(Disponibilidad disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public Integridad getIntegridad() {
        return integridad;
    }

    public void setIntegridad(Integridad integridad) {
        this.integridad = integridad;
    }

    public Confidencialidad getConfidencialidad() {
        return confidencialidad;
    }

    public void setConfidencialidad(Confidencialidad confidencialidad) {
        this.confidencialidad = confidencialidad;
    }

    public Criticidad getCriticidad() {
        return criticidad;
    }

    public void setCriticidad(Criticidad criticidad) {
        this.criticidad = criticidad;
    }

    public Set<RiesgoDTO> getRiesgos() {
        return riesgos;
    }

    public void setRiesgos(Set<RiesgoDTO> riesgos) {
        this.riesgos = riesgos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GrupoActivosDTO)) {
            return false;
        }

        GrupoActivosDTO grupoActivosDTO = (GrupoActivosDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, grupoActivosDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GrupoActivosDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", disponibilidad='" + getDisponibilidad() + "'" +
            ", integridad='" + getIntegridad() + "'" +
            ", confidencialidad='" + getConfidencialidad() + "'" +
            ", criticidad='" + getCriticidad() + "'" +
            ", riesgos=" + getRiesgos() +
            "}";
    }
}
