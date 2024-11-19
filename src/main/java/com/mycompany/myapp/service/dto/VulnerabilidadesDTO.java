package com.mycompany.myapp.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Vulnerabilidades} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class VulnerabilidadesDTO implements Serializable {

    private Long id;

    @NotNull
    private String nombre;

    @NotNull
    private String descripcion;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VulnerabilidadesDTO)) {
            return false;
        }

        VulnerabilidadesDTO vulnerabilidadesDTO = (VulnerabilidadesDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, vulnerabilidadesDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VulnerabilidadesDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            "}";
    }
}
