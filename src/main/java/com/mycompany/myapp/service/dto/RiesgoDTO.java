package com.mycompany.myapp.service.dto;

import com.mycompany.myapp.domain.enumeration.Afectacion;
import com.mycompany.myapp.domain.enumeration.AfectacionEconomica;
import com.mycompany.myapp.domain.enumeration.AfectacionReputacional;
import com.mycompany.myapp.domain.enumeration.Clasificacion;
import com.mycompany.myapp.domain.enumeration.FrecuenciaControl;
import com.mycompany.myapp.domain.enumeration.ImpactoInherente;
import com.mycompany.myapp.domain.enumeration.ImpactoResidualFinal;
import com.mycompany.myapp.domain.enumeration.Implementacion;
import com.mycompany.myapp.domain.enumeration.ProbabilidadInherente;
import com.mycompany.myapp.domain.enumeration.ProbabilidadResidualFinal;
import com.mycompany.myapp.domain.enumeration.TipoControl;
import com.mycompany.myapp.domain.enumeration.TipoRiesgo;
import com.mycompany.myapp.domain.enumeration.TratamientoRiesgo;
import com.mycompany.myapp.domain.enumeration.ZonaRiesgo;
import com.mycompany.myapp.domain.enumeration.ZonaRiesgoFinalControl;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Riesgo} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RiesgoDTO implements Serializable {

    private Long id;

    private String proceso;

    private TipoRiesgo tipoRiesgo;

    private String descripcion;

    private Clasificacion clasificacion;

    @NotNull
    private Integer frecuencia;

    @NotNull
    private AfectacionEconomica afectacionEconomica;

    private AfectacionReputacional impactoReputacional;

    private ProbabilidadInherente probabilidadInherente;

    private ImpactoInherente impactoInherente;

    private ZonaRiesgo zonaRiesgo;

    @NotNull
    private Afectacion afectacion;

    @NotNull
    private TipoControl tipoControl;

    @NotNull
    private Implementacion implementacion;

    @Max(value = 100)
    private Integer calificacionControl;

    private Boolean documentado;

    @NotNull
    private FrecuenciaControl frecuenciaControl;

    private Boolean evidencia;

    private Integer probabilidad;

    private Integer impacto;

    private ProbabilidadResidualFinal probabilidadResidualFinal;

    private ImpactoResidualFinal impactoResidualFinal;

    private ZonaRiesgoFinalControl zonaDeRiesgoFinal;

    private String riesgoResidual;

    @NotNull
    private TratamientoRiesgo tratamiento;

    private String planAccion;

    private String responsable;

    private LocalDate fechaImplementacion;

    private String seguimientoControlExistente;

    private String estado;

    private String observaciones;

    private LocalDate fechaMonitoreo;

    @NotNull
    private Set<GrupoActivosDTO> activos = new HashSet<>();

    private Set<ControlesDTO> controles = new HashSet<>();

    @NotNull
    private AmenazasDTO amenaza;

    @NotNull
    private VulnerabilidadesDTO vulnerabilidad;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProceso() {
        return proceso;
    }

    public void setProceso(String proceso) {
        this.proceso = proceso;
    }

    public TipoRiesgo getTipoRiesgo() {
        return tipoRiesgo;
    }

    public void setTipoRiesgo(TipoRiesgo tipoRiesgo) {
        this.tipoRiesgo = tipoRiesgo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Clasificacion getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(Clasificacion clasificacion) {
        this.clasificacion = clasificacion;
    }

    public Integer getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(Integer frecuencia) {
        this.frecuencia = frecuencia;
    }

    public AfectacionEconomica getAfectacionEconomica() {
        return afectacionEconomica;
    }

    public void setAfectacionEconomica(AfectacionEconomica afectacionEconomica) {
        this.afectacionEconomica = afectacionEconomica;
    }

    public AfectacionReputacional getImpactoReputacional() {
        return impactoReputacional;
    }

    public void setImpactoReputacional(AfectacionReputacional impactoReputacional) {
        this.impactoReputacional = impactoReputacional;
    }

    public ProbabilidadInherente getProbabilidadInherente() {
        return probabilidadInherente;
    }

    public void setProbabilidadInherente(ProbabilidadInherente probabilidadInherente) {
        this.probabilidadInherente = probabilidadInherente;
    }

    public ImpactoInherente getImpactoInherente() {
        return impactoInherente;
    }

    public void setImpactoInherente(ImpactoInherente impactoInherente) {
        this.impactoInherente = impactoInherente;
    }

    public ZonaRiesgo getZonaRiesgo() {
        return zonaRiesgo;
    }

    public void setZonaRiesgo(ZonaRiesgo zonaRiesgo) {
        this.zonaRiesgo = zonaRiesgo;
    }

    public Afectacion getAfectacion() {
        return afectacion;
    }

    public void setAfectacion(Afectacion afectacion) {
        this.afectacion = afectacion;
    }

    public TipoControl getTipoControl() {
        return tipoControl;
    }

    public void setTipoControl(TipoControl tipoControl) {
        this.tipoControl = tipoControl;
    }

    public Implementacion getImplementacion() {
        return implementacion;
    }

    public void setImplementacion(Implementacion implementacion) {
        this.implementacion = implementacion;
    }

    public Integer getCalificacionControl() {
        return calificacionControl;
    }

    public void setCalificacionControl(Integer calificacionControl) {
        this.calificacionControl = calificacionControl;
    }

    public Boolean getDocumentado() {
        return documentado;
    }

    public void setDocumentado(Boolean documentado) {
        this.documentado = documentado;
    }

    public FrecuenciaControl getFrecuenciaControl() {
        return frecuenciaControl;
    }

    public void setFrecuenciaControl(FrecuenciaControl frecuenciaControl) {
        this.frecuenciaControl = frecuenciaControl;
    }

    public Boolean getEvidencia() {
        return evidencia;
    }

    public void setEvidencia(Boolean evidencia) {
        this.evidencia = evidencia;
    }

    public Integer getProbabilidad() {
        return probabilidad;
    }

    public void setProbabilidad(Integer probabilidad) {
        this.probabilidad = probabilidad;
    }

    public Integer getImpacto() {
        return impacto;
    }

    public void setImpacto(Integer impacto) {
        this.impacto = impacto;
    }

    public ProbabilidadResidualFinal getProbabilidadResidualFinal() {
        return probabilidadResidualFinal;
    }

    public void setProbabilidadResidualFinal(ProbabilidadResidualFinal probabilidadResidualFinal) {
        this.probabilidadResidualFinal = probabilidadResidualFinal;
    }

    public ImpactoResidualFinal getImpactoResidualFinal() {
        return impactoResidualFinal;
    }

    public void setImpactoResidualFinal(ImpactoResidualFinal impactoResidualFinal) {
        this.impactoResidualFinal = impactoResidualFinal;
    }

    public ZonaRiesgoFinalControl getZonaDeRiesgoFinal() {
        return zonaDeRiesgoFinal;
    }

    public void setZonaDeRiesgoFinal(ZonaRiesgoFinalControl zonaDeRiesgoFinal) {
        this.zonaDeRiesgoFinal = zonaDeRiesgoFinal;
    }

    public String getRiesgoResidual() {
        return riesgoResidual;
    }

    public void setRiesgoResidual(String riesgoResidual) {
        this.riesgoResidual = riesgoResidual;
    }

    public TratamientoRiesgo getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(TratamientoRiesgo tratamiento) {
        this.tratamiento = tratamiento;
    }

    public String getPlanAccion() {
        return planAccion;
    }

    public void setPlanAccion(String planAccion) {
        this.planAccion = planAccion;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public LocalDate getFechaImplementacion() {
        return fechaImplementacion;
    }

    public void setFechaImplementacion(LocalDate fechaImplementacion) {
        this.fechaImplementacion = fechaImplementacion;
    }

    public String getSeguimientoControlExistente() {
        return seguimientoControlExistente;
    }

    public void setSeguimientoControlExistente(String seguimientoControlExistente) {
        this.seguimientoControlExistente = seguimientoControlExistente;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public LocalDate getFechaMonitoreo() {
        return fechaMonitoreo;
    }

    public void setFechaMonitoreo(LocalDate fechaMonitoreo) {
        this.fechaMonitoreo = fechaMonitoreo;
    }

    public Set<GrupoActivosDTO> getActivos() {
        return activos;
    }

    public void setActivos(Set<GrupoActivosDTO> activos) {
        this.activos = activos;
    }

    public Set<ControlesDTO> getControles() {
        return controles;
    }

    public void setControles(Set<ControlesDTO> controles) {
        this.controles = controles;
    }

    public AmenazasDTO getAmenaza() {
        return amenaza;
    }

    public void setAmenaza(AmenazasDTO amenaza) {
        this.amenaza = amenaza;
    }

    public VulnerabilidadesDTO getVulnerabilidad() {
        return vulnerabilidad;
    }

    public void setVulnerabilidad(VulnerabilidadesDTO vulnerabilidad) {
        this.vulnerabilidad = vulnerabilidad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RiesgoDTO)) {
            return false;
        }

        RiesgoDTO riesgoDTO = (RiesgoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, riesgoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RiesgoDTO{" +
            "id=" + getId() +
            ", proceso='" + getProceso() + "'" +
            ", tipoRiesgo='" + getTipoRiesgo() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", clasificacion='" + getClasificacion() + "'" +
            ", frecuencia=" + getFrecuencia() +
            ", afectacionEconomica='" + getAfectacionEconomica() + "'" +
            ", impactoReputacional='" + getImpactoReputacional() + "'" +
            ", probabilidadInherente='" + getProbabilidadInherente() + "'" +
            ", impactoInherente='" + getImpactoInherente() + "'" +
            ", zonaRiesgo='" + getZonaRiesgo() + "'" +
            ", afectacion='" + getAfectacion() + "'" +
            ", tipoControl='" + getTipoControl() + "'" +
            ", implementacion='" + getImplementacion() + "'" +
            ", calificacionControl=" + getCalificacionControl() +
            ", documentado='" + getDocumentado() + "'" +
            ", frecuenciaControl='" + getFrecuenciaControl() + "'" +
            ", evidencia='" + getEvidencia() + "'" +
            ", probabilidad=" + getProbabilidad() +
            ", impacto=" + getImpacto() +
            ", probabilidadResidualFinal='" + getProbabilidadResidualFinal() + "'" +
            ", impactoResidualFinal='" + getImpactoResidualFinal() + "'" +
            ", zonaDeRiesgoFinal='" + getZonaDeRiesgoFinal() + "'" +
            ", riesgoResidual='" + getRiesgoResidual() + "'" +
            ", tratamiento='" + getTratamiento() + "'" +
            ", planAccion='" + getPlanAccion() + "'" +
            ", responsable='" + getResponsable() + "'" +
            ", fechaImplementacion='" + getFechaImplementacion() + "'" +
            ", seguimientoControlExistente='" + getSeguimientoControlExistente() + "'" +
            ", estado='" + getEstado() + "'" +
            ", observaciones='" + getObservaciones() + "'" +
            ", fechaMonitoreo='" + getFechaMonitoreo() + "'" +
            ", activos=" + getActivos() +
            ", controles=" + getControles() +
            ", amenaza=" + getAmenaza() +
            ", vulnerabilidad=" + getVulnerabilidad() +
            "}";
    }
}
