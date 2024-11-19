package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.ClasificacionInformacion1712;
import com.mycompany.myapp.domain.enumeration.Formato;
import com.mycompany.myapp.domain.enumeration.Proceso;
import com.mycompany.myapp.domain.enumeration.TipoActivo;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ActivoInformacion.
 */
@Entity
@Table(name = "activo_informacion")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ActivoInformacion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "proceso")
    private Proceso proceso;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotNull
    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_activo", nullable = false)
    private TipoActivo tipoActivo;

    @NotNull
    @Column(name = "ley_1581", nullable = false)
    private Boolean ley1581;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "clasificacion_informacion_1712", nullable = false)
    private ClasificacionInformacion1712 clasificacionInformacion1712;

    @NotNull
    @Column(name = "ley_1266", nullable = false)
    private Boolean ley1266;

    @Enumerated(EnumType.STRING)
    @Column(name = "formato")
    private Formato formato;

    @Column(name = "propietario")
    private String propietario;

    @Column(name = "usuario")
    private String usuario;

    @Column(name = "custodio")
    private String custodio;

    @Column(name = "usuario_final")
    private String usuarioFinal;

    @Column(name = "fecha")
    private LocalDate fecha;

    @Column(name = "estado_activo")
    private String estadoActivo;

    @Column(name = "fecha_ingreso")
    private LocalDate fechaIngreso;

    @Column(name = "fecha_retiro")
    private LocalDate fechaRetiro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "activos", "riesgos" }, allowSetters = true)
    private GrupoActivos grupoActivo;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ActivoInformacion id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Proceso getProceso() {
        return this.proceso;
    }

    public ActivoInformacion proceso(Proceso proceso) {
        this.setProceso(proceso);
        return this;
    }

    public void setProceso(Proceso proceso) {
        this.proceso = proceso;
    }

    public String getNombre() {
        return this.nombre;
    }

    public ActivoInformacion nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public ActivoInformacion descripcion(String descripcion) {
        this.setDescripcion(descripcion);
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public TipoActivo getTipoActivo() {
        return this.tipoActivo;
    }

    public ActivoInformacion tipoActivo(TipoActivo tipoActivo) {
        this.setTipoActivo(tipoActivo);
        return this;
    }

    public void setTipoActivo(TipoActivo tipoActivo) {
        this.tipoActivo = tipoActivo;
    }

    public Boolean getLey1581() {
        return this.ley1581;
    }

    public ActivoInformacion ley1581(Boolean ley1581) {
        this.setLey1581(ley1581);
        return this;
    }

    public void setLey1581(Boolean ley1581) {
        this.ley1581 = ley1581;
    }

    public ClasificacionInformacion1712 getClasificacionInformacion1712() {
        return this.clasificacionInformacion1712;
    }

    public ActivoInformacion clasificacionInformacion1712(ClasificacionInformacion1712 clasificacionInformacion1712) {
        this.setClasificacionInformacion1712(clasificacionInformacion1712);
        return this;
    }

    public void setClasificacionInformacion1712(ClasificacionInformacion1712 clasificacionInformacion1712) {
        this.clasificacionInformacion1712 = clasificacionInformacion1712;
    }

    public Boolean getLey1266() {
        return this.ley1266;
    }

    public ActivoInformacion ley1266(Boolean ley1266) {
        this.setLey1266(ley1266);
        return this;
    }

    public void setLey1266(Boolean ley1266) {
        this.ley1266 = ley1266;
    }

    public Formato getFormato() {
        return this.formato;
    }

    public ActivoInformacion formato(Formato formato) {
        this.setFormato(formato);
        return this;
    }

    public void setFormato(Formato formato) {
        this.formato = formato;
    }

    public String getPropietario() {
        return this.propietario;
    }

    public ActivoInformacion propietario(String propietario) {
        this.setPropietario(propietario);
        return this;
    }

    public void setPropietario(String propietario) {
        this.propietario = propietario;
    }

    public String getUsuario() {
        return this.usuario;
    }

    public ActivoInformacion usuario(String usuario) {
        this.setUsuario(usuario);
        return this;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getCustodio() {
        return this.custodio;
    }

    public ActivoInformacion custodio(String custodio) {
        this.setCustodio(custodio);
        return this;
    }

    public void setCustodio(String custodio) {
        this.custodio = custodio;
    }

    public String getUsuarioFinal() {
        return this.usuarioFinal;
    }

    public ActivoInformacion usuarioFinal(String usuarioFinal) {
        this.setUsuarioFinal(usuarioFinal);
        return this;
    }

    public void setUsuarioFinal(String usuarioFinal) {
        this.usuarioFinal = usuarioFinal;
    }

    public LocalDate getFecha() {
        return this.fecha;
    }

    public ActivoInformacion fecha(LocalDate fecha) {
        this.setFecha(fecha);
        return this;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getEstadoActivo() {
        return this.estadoActivo;
    }

    public ActivoInformacion estadoActivo(String estadoActivo) {
        this.setEstadoActivo(estadoActivo);
        return this;
    }

    public void setEstadoActivo(String estadoActivo) {
        this.estadoActivo = estadoActivo;
    }

    public LocalDate getFechaIngreso() {
        return this.fechaIngreso;
    }

    public ActivoInformacion fechaIngreso(LocalDate fechaIngreso) {
        this.setFechaIngreso(fechaIngreso);
        return this;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public LocalDate getFechaRetiro() {
        return this.fechaRetiro;
    }

    public ActivoInformacion fechaRetiro(LocalDate fechaRetiro) {
        this.setFechaRetiro(fechaRetiro);
        return this;
    }

    public void setFechaRetiro(LocalDate fechaRetiro) {
        this.fechaRetiro = fechaRetiro;
    }

    public GrupoActivos getGrupoActivo() {
        return this.grupoActivo;
    }

    public void setGrupoActivo(GrupoActivos grupoActivos) {
        this.grupoActivo = grupoActivos;
    }

    public ActivoInformacion grupoActivo(GrupoActivos grupoActivos) {
        this.setGrupoActivo(grupoActivos);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ActivoInformacion)) {
            return false;
        }
        return getId() != null && getId().equals(((ActivoInformacion) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ActivoInformacion{" +
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
            "}";
    }
}
