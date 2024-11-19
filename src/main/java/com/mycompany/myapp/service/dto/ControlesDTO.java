package com.mycompany.myapp.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Controles} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ControlesDTO implements Serializable {

    private Long id;

    @NotNull
    private String nombre;

    @NotNull
    private String descripcion;

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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
        if (!(o instanceof ControlesDTO)) {
            return false;
        }

        ControlesDTO controlesDTO = (ControlesDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, controlesDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ControlesDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", riesgos=" + getRiesgos() +
            "}";
    }
}
