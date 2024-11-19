package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Riesgo.
 */
@Entity
@Table(name = "riesgo")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Riesgo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "proceso")
    private String proceso;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_riesgo")
    private TipoRiesgo tipoRiesgo;

    @Column(name = "descripcion")
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(name = "clasificacion")
    private Clasificacion clasificacion;

    @NotNull
    @Column(name = "frecuencia", nullable = false)
    private Integer frecuencia;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "afectacion_economica", nullable = false)
    private AfectacionEconomica afectacionEconomica;

    @Enumerated(EnumType.STRING)
    @Column(name = "impacto_reputacional")
    private AfectacionReputacional impactoReputacional;

    @Enumerated(EnumType.STRING)
    @Column(name = "probabilidad_inherente")
    private ProbabilidadInherente probabilidadInherente;

    @Enumerated(EnumType.STRING)
    @Column(name = "impacto_inherente")
    private ImpactoInherente impactoInherente;

    @Enumerated(EnumType.STRING)
    @Column(name = "zona_riesgo")
    private ZonaRiesgo zonaRiesgo;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "afectacion", nullable = false)
    private Afectacion afectacion;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_control", nullable = false)
    private TipoControl tipoControl;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "implementacion", nullable = false)
    private Implementacion implementacion;

    @Max(value = 100)
    @Column(name = "calificacion_control")
    private Integer calificacionControl;

    @Column(name = "documentado")
    private Boolean documentado;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "frecuencia_control", nullable = false)
    private FrecuenciaControl frecuenciaControl;

    @Column(name = "evidencia")
    private Boolean evidencia;

    @Column(name = "probabilidad")
    private Integer probabilidad;

    @Column(name = "impacto")
    private Integer impacto;

    @Enumerated(EnumType.STRING)
    @Column(name = "probabilidad_residual_final")
    private ProbabilidadResidualFinal probabilidadResidualFinal;

    @Enumerated(EnumType.STRING)
    @Column(name = "impacto_residual_final")
    private ImpactoResidualFinal impactoResidualFinal;

    @Enumerated(EnumType.STRING)
    @Column(name = "zona_de_riesgo_final")
    private ZonaRiesgoFinalControl zonaDeRiesgoFinal;

    @Column(name = "riesgo_residual")
    private String riesgoResidual;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tratamiento", nullable = false)
    private TratamientoRiesgo tratamiento;

    @Column(name = "plan_accion")
    private String planAccion;

    @Column(name = "responsable")
    private String responsable;

    @Column(name = "fecha_implementacion")
    private LocalDate fechaImplementacion;

    @Column(name = "seguimiento_control_existente")
    private String seguimientoControlExistente;

    @Column(name = "estado")
    private String estado;

    @Column(name = "observaciones")
    private String observaciones;

    @Column(name = "fecha_monitoreo")
    private LocalDate fechaMonitoreo;

    @ManyToMany(fetch = FetchType.LAZY)
    @NotNull
    @JoinTable(
        name = "rel_riesgo__activos",
        joinColumns = @JoinColumn(name = "riesgo_id"),
        inverseJoinColumns = @JoinColumn(name = "activos_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "activos", "riesgos" }, allowSetters = true)
    private Set<GrupoActivos> activos = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_riesgo__controles",
        joinColumns = @JoinColumn(name = "riesgo_id"),
        inverseJoinColumns = @JoinColumn(name = "controles_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "riesgos" }, allowSetters = true)
    private Set<Controles> controles = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "riesgos" }, allowSetters = true)
    private Amenazas amenaza;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "riesgos" }, allowSetters = true)
    private Vulnerabilidades vulnerabilidad;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Riesgo id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProceso() {
        return this.proceso;
    }

    public Riesgo proceso(String proceso) {
        this.setProceso(proceso);
        return this;
    }

    public void setProceso(String proceso) {
        this.proceso = proceso;
    }

    public TipoRiesgo getTipoRiesgo() {
        return this.tipoRiesgo;
    }

    public Riesgo tipoRiesgo(TipoRiesgo tipoRiesgo) {
        this.setTipoRiesgo(tipoRiesgo);
        return this;
    }

    public void setTipoRiesgo(TipoRiesgo tipoRiesgo) {
        this.tipoRiesgo = tipoRiesgo;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public Riesgo descripcion(String descripcion) {
        this.setDescripcion(descripcion);
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Clasificacion getClasificacion() {
        return this.clasificacion;
    }

    public Riesgo clasificacion(Clasificacion clasificacion) {
        this.setClasificacion(clasificacion);
        return this;
    }

    public void setClasificacion(Clasificacion clasificacion) {
        this.clasificacion = clasificacion;
    }

    public Integer getFrecuencia() {
        return this.frecuencia;
    }

    public Riesgo frecuencia(Integer frecuencia) {
        this.setFrecuencia(frecuencia);
        return this;
    }

    public void setFrecuencia(Integer frecuencia) {
        this.frecuencia = frecuencia;
    }

    public AfectacionEconomica getAfectacionEconomica() {
        return this.afectacionEconomica;
    }

    public Riesgo afectacionEconomica(AfectacionEconomica afectacionEconomica) {
        this.setAfectacionEconomica(afectacionEconomica);
        return this;
    }

    public void setAfectacionEconomica(AfectacionEconomica afectacionEconomica) {
        this.afectacionEconomica = afectacionEconomica;
    }

    public AfectacionReputacional getImpactoReputacional() {
        return this.impactoReputacional;
    }

    public Riesgo impactoReputacional(AfectacionReputacional impactoReputacional) {
        this.setImpactoReputacional(impactoReputacional);
        return this;
    }

    public void setImpactoReputacional(AfectacionReputacional impactoReputacional) {
        this.impactoReputacional = impactoReputacional;
    }

    public ProbabilidadInherente getProbabilidadInherente() {
        return this.probabilidadInherente;
    }

    public Riesgo probabilidadInherente(ProbabilidadInherente probabilidadInherente) {
        this.setProbabilidadInherente(probabilidadInherente);
        return this;
    }

    public void setProbabilidadInherente(ProbabilidadInherente probabilidadInherente) {
        this.probabilidadInherente = probabilidadInherente;
    }

    public ImpactoInherente getImpactoInherente() {
        return this.impactoInherente;
    }

    public Riesgo impactoInherente(ImpactoInherente impactoInherente) {
        this.setImpactoInherente(impactoInherente);
        return this;
    }

    public void setImpactoInherente(ImpactoInherente impactoInherente) {
        this.impactoInherente = impactoInherente;
    }

    public ZonaRiesgo getZonaRiesgo() {
        return this.zonaRiesgo;
    }

    public Riesgo zonaRiesgo(ZonaRiesgo zonaRiesgo) {
        this.setZonaRiesgo(zonaRiesgo);
        return this;
    }

    public void setZonaRiesgo(ZonaRiesgo zonaRiesgo) {
        this.zonaRiesgo = zonaRiesgo;
    }

    public Afectacion getAfectacion() {
        return this.afectacion;
    }

    public Riesgo afectacion(Afectacion afectacion) {
        this.setAfectacion(afectacion);
        return this;
    }

    public void setAfectacion(Afectacion afectacion) {
        this.afectacion = afectacion;
    }

    public TipoControl getTipoControl() {
        return this.tipoControl;
    }

    public Riesgo tipoControl(TipoControl tipoControl) {
        this.setTipoControl(tipoControl);
        return this;
    }

    public void setTipoControl(TipoControl tipoControl) {
        this.tipoControl = tipoControl;
    }

    public Implementacion getImplementacion() {
        return this.implementacion;
    }

    public Riesgo implementacion(Implementacion implementacion) {
        this.setImplementacion(implementacion);
        return this;
    }

    public void setImplementacion(Implementacion implementacion) {
        this.implementacion = implementacion;
    }

    public Integer getCalificacionControl() {
        return this.calificacionControl;
    }

    public Riesgo calificacionControl(Integer calificacionControl) {
        this.setCalificacionControl(calificacionControl);
        return this;
    }

    public void setCalificacionControl(Integer calificacionControl) {
        this.calificacionControl = calificacionControl;
    }

    public Boolean getDocumentado() {
        return this.documentado;
    }

    public Riesgo documentado(Boolean documentado) {
        this.setDocumentado(documentado);
        return this;
    }

    public void setDocumentado(Boolean documentado) {
        this.documentado = documentado;
    }

    public FrecuenciaControl getFrecuenciaControl() {
        return this.frecuenciaControl;
    }

    public Riesgo frecuenciaControl(FrecuenciaControl frecuenciaControl) {
        this.setFrecuenciaControl(frecuenciaControl);
        return this;
    }

    public void setFrecuenciaControl(FrecuenciaControl frecuenciaControl) {
        this.frecuenciaControl = frecuenciaControl;
    }

    public Boolean getEvidencia() {
        return this.evidencia;
    }

    public Riesgo evidencia(Boolean evidencia) {
        this.setEvidencia(evidencia);
        return this;
    }

    public void setEvidencia(Boolean evidencia) {
        this.evidencia = evidencia;
    }

    public Integer getProbabilidad() {
        return this.probabilidad;
    }

    public Riesgo probabilidad(Integer probabilidad) {
        this.setProbabilidad(probabilidad);
        return this;
    }

    public void setProbabilidad(Integer probabilidad) {
        this.probabilidad = probabilidad;
    }

    public Integer getImpacto() {
        return this.impacto;
    }

    public Riesgo impacto(Integer impacto) {
        this.setImpacto(impacto);
        return this;
    }

    public void setImpacto(Integer impacto) {
        this.impacto = impacto;
    }

    public ProbabilidadResidualFinal getProbabilidadResidualFinal() {
        return this.probabilidadResidualFinal;
    }

    public Riesgo probabilidadResidualFinal(ProbabilidadResidualFinal probabilidadResidualFinal) {
        this.setProbabilidadResidualFinal(probabilidadResidualFinal);
        return this;
    }

    public void setProbabilidadResidualFinal(ProbabilidadResidualFinal probabilidadResidualFinal) {
        this.probabilidadResidualFinal = probabilidadResidualFinal;
    }

    public ImpactoResidualFinal getImpactoResidualFinal() {
        return this.impactoResidualFinal;
    }

    public Riesgo impactoResidualFinal(ImpactoResidualFinal impactoResidualFinal) {
        this.setImpactoResidualFinal(impactoResidualFinal);
        return this;
    }

    public void setImpactoResidualFinal(ImpactoResidualFinal impactoResidualFinal) {
        this.impactoResidualFinal = impactoResidualFinal;
    }

    public ZonaRiesgoFinalControl getZonaDeRiesgoFinal() {
        return this.zonaDeRiesgoFinal;
    }

    public Riesgo zonaDeRiesgoFinal(ZonaRiesgoFinalControl zonaDeRiesgoFinal) {
        this.setZonaDeRiesgoFinal(zonaDeRiesgoFinal);
        return this;
    }

    public void setZonaDeRiesgoFinal(ZonaRiesgoFinalControl zonaDeRiesgoFinal) {
        this.zonaDeRiesgoFinal = zonaDeRiesgoFinal;
    }

    public String getRiesgoResidual() {
        return this.riesgoResidual;
    }

    public Riesgo riesgoResidual(String riesgoResidual) {
        this.setRiesgoResidual(riesgoResidual);
        return this;
    }

    public void setRiesgoResidual(String riesgoResidual) {
        this.riesgoResidual = riesgoResidual;
    }

    public TratamientoRiesgo getTratamiento() {
        return this.tratamiento;
    }

    public Riesgo tratamiento(TratamientoRiesgo tratamiento) {
        this.setTratamiento(tratamiento);
        return this;
    }

    public void setTratamiento(TratamientoRiesgo tratamiento) {
        this.tratamiento = tratamiento;
    }

    public String getPlanAccion() {
        return this.planAccion;
    }

    public Riesgo planAccion(String planAccion) {
        this.setPlanAccion(planAccion);
        return this;
    }

    public void setPlanAccion(String planAccion) {
        this.planAccion = planAccion;
    }

    public String getResponsable() {
        return this.responsable;
    }

    public Riesgo responsable(String responsable) {
        this.setResponsable(responsable);
        return this;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public LocalDate getFechaImplementacion() {
        return this.fechaImplementacion;
    }

    public Riesgo fechaImplementacion(LocalDate fechaImplementacion) {
        this.setFechaImplementacion(fechaImplementacion);
        return this;
    }

    public void setFechaImplementacion(LocalDate fechaImplementacion) {
        this.fechaImplementacion = fechaImplementacion;
    }

    public String getSeguimientoControlExistente() {
        return this.seguimientoControlExistente;
    }

    public Riesgo seguimientoControlExistente(String seguimientoControlExistente) {
        this.setSeguimientoControlExistente(seguimientoControlExistente);
        return this;
    }

    public void setSeguimientoControlExistente(String seguimientoControlExistente) {
        this.seguimientoControlExistente = seguimientoControlExistente;
    }

    public String getEstado() {
        return this.estado;
    }

    public Riesgo estado(String estado) {
        this.setEstado(estado);
        return this;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getObservaciones() {
        return this.observaciones;
    }

    public Riesgo observaciones(String observaciones) {
        this.setObservaciones(observaciones);
        return this;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public LocalDate getFechaMonitoreo() {
        return this.fechaMonitoreo;
    }

    public Riesgo fechaMonitoreo(LocalDate fechaMonitoreo) {
        this.setFechaMonitoreo(fechaMonitoreo);
        return this;
    }

    public void setFechaMonitoreo(LocalDate fechaMonitoreo) {
        this.fechaMonitoreo = fechaMonitoreo;
    }

    public Set<GrupoActivos> getActivos() {
        return this.activos;
    }

    public void setActivos(Set<GrupoActivos> grupoActivos) {
        this.activos = grupoActivos;
    }

    public Riesgo activos(Set<GrupoActivos> grupoActivos) {
        this.setActivos(grupoActivos);
        return this;
    }

    public Riesgo addActivos(GrupoActivos grupoActivos) {
        this.activos.add(grupoActivos);
        return this;
    }

    public Riesgo removeActivos(GrupoActivos grupoActivos) {
        this.activos.remove(grupoActivos);
        return this;
    }

    public Set<Controles> getControles() {
        return this.controles;
    }

    public void setControles(Set<Controles> controles) {
        this.controles = controles;
    }

    public Riesgo controles(Set<Controles> controles) {
        this.setControles(controles);
        return this;
    }

    public Riesgo addControles(Controles controles) {
        this.controles.add(controles);
        return this;
    }

    public Riesgo removeControles(Controles controles) {
        this.controles.remove(controles);
        return this;
    }

    public Amenazas getAmenaza() {
        return this.amenaza;
    }

    public void setAmenaza(Amenazas amenazas) {
        this.amenaza = amenazas;
    }

    public Riesgo amenaza(Amenazas amenazas) {
        this.setAmenaza(amenazas);
        return this;
    }

    public Vulnerabilidades getVulnerabilidad() {
        return this.vulnerabilidad;
    }

    public void setVulnerabilidad(Vulnerabilidades vulnerabilidades) {
        this.vulnerabilidad = vulnerabilidades;
    }

    public Riesgo vulnerabilidad(Vulnerabilidades vulnerabilidades) {
        this.setVulnerabilidad(vulnerabilidades);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Riesgo)) {
            return false;
        }
        return getId() != null && getId().equals(((Riesgo) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Riesgo{" +
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
            "}";
    }
}
