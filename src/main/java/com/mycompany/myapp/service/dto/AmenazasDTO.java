package com.mycompany.myapp.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Amenazas} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AmenazasDTO implements Serializable {

    private Long id;

    @NotNull
    private String nombre;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AmenazasDTO)) {
            return false;
        }

        AmenazasDTO amenazasDTO = (AmenazasDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, amenazasDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AmenazasDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            "}";
    }
}
