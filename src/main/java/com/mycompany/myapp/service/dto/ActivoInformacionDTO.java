package com.mycompany.myapp.service.dto;

import com.mycompany.myapp.domain.enumeration.ClasificacionInformacion1712;
import com.mycompany.myapp.domain.enumeration.Formato;
import com.mycompany.myapp.domain.enumeration.Proceso;
import com.mycompany.myapp.domain.enumeration.TipoActivo;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.ActivoInformacion} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ActivoInformacionDTO implements Serializable {

    private Long id;

    private Proceso proceso;

    @NotNull
    private String nombre;

    @NotNull
    private String descripcion;

    @NotNull
    private TipoActivo tipoActivo;

    @NotNull
    private Boolean ley1581;

    @NotNull
    private ClasificacionInformacion1712 clasificacionInformacion1712;

    @NotNull
    private Boolean ley1266;

    private Formato formato;

    private String propietario;

    private String usuario;

    private String custodio;

    private String usuarioFinal;

    private LocalDate fecha;

    private String estadoActivo;

    private LocalDate fechaIngreso;

    private LocalDate fechaRetiro;

    private GrupoActivosDTO grupoActivo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Proceso getProceso() {
        return proceso;
    }

    public void setProceso(Proceso proceso) {
        this.proceso = proceso;
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

    public TipoActivo getTipoActivo() {
        return tipoActivo;
    }

    public void setTipoActivo(TipoActivo tipoActivo) {
        this.tipoActivo = tipoActivo;
    }

    public Boolean getLey1581() {
        return ley1581;
    }

    public void setLey1581(Boolean ley1581) {
        this.ley1581 = ley1581;
    }

    public ClasificacionInformacion1712 getClasificacionInformacion1712() {
        return clasificacionInformacion1712;
    }

    public void setClasificacionInformacion1712(ClasificacionInformacion1712 clasificacionInformacion1712) {
        this.clasificacionInformacion1712 = clasificacionInformacion1712;
    }

    public Boolean getLey1266() {
        return ley1266;
    }

    public void setLey1266(Boolean ley1266) {
        this.ley1266 = ley1266;
    }

    public Formato getFormato() {
        return formato;
    }

    public void setFormato(Formato formato) {
        this.formato = formato;
    }

    public String getPropietario() {
        return propietario;
    }

    public void setPropietario(String propietario) {
        this.propietario = propietario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getCustodio() {
        return custodio;
    }

    public void setCustodio(String custodio) {
        this.custodio = custodio;
    }

    public String getUsuarioFinal() {
        return usuarioFinal;
    }

    public void setUsuarioFinal(String usuarioFinal) {
        this.usuarioFinal = usuarioFinal;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getEstadoActivo() {
        return estadoActivo;
    }

    public void setEstadoActivo(String estadoActivo) {
        this.estadoActivo = estadoActivo;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public LocalDate getFechaRetiro() {
        return fechaRetiro;
    }

    public void setFechaRetiro(LocalDate fechaRetiro) {
        this.fechaRetiro = fechaRetiro;
    }

    public GrupoActivosDTO getGrupoActivo() {
        return grupoActivo;
    }

    public void setGrupoActivo(GrupoActivosDTO grupoActivo) {
        this.grupoActivo = grupoActivo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ActivoInformacionDTO)) {
            return false;
        }

        ActivoInformacionDTO activoInformacionDTO = (ActivoInformacionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, activoInformacionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ActivoInformacionDTO{" +
            "id=" + getId() +
            ", proceso='" + getProceso() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", tipoActivo='" + getTipoActivo() + "'" +
            ", ley1581='" + getLey1581() + "'" +
            ", clasificacionInformacion1712='" + getClasificacionInformacion1712() + "'" +
            ", ley1266='" + getLey1266() + "'" +
            ", formato='" + getFormato() + "'" +
            ", propietario='" + getPropietario() + "'" +
            ", usuario='" + getUsuario() + "'" +
            ", custodio='" + getCustodio() + "'" +
            ", usuarioFinal='" + getUsuarioFinal() + "'" +
            ", fecha='" + getFecha() + "'" +
            ", estadoActivo='" + getEstadoActivo() + "'" +
            ", fechaIngreso='" + getFechaIngreso() + "'" +
            ", fechaRetiro='" + getFechaRetiro() + "'" +
            ", grupoActivo=" + getGrupoActivo() +
            "}";
    }
}
