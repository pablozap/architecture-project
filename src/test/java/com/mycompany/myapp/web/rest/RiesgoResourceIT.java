package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.RiesgoAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Amenazas;
import com.mycompany.myapp.domain.GrupoActivos;
import com.mycompany.myapp.domain.Riesgo;
import com.mycompany.myapp.domain.Vulnerabilidades;
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
import com.mycompany.myapp.repository.RiesgoRepository;
import com.mycompany.myapp.service.RiesgoService;
import com.mycompany.myapp.service.dto.RiesgoDTO;
import com.mycompany.myapp.service.mapper.RiesgoMapper;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link RiesgoResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class RiesgoResourceIT {

    private static final String DEFAULT_PROCESO = "AAAAAAAAAA";
    private static final String UPDATED_PROCESO = "BBBBBBBBBB";

    private static final TipoRiesgo DEFAULT_TIPO_RIESGO = TipoRiesgo.INTEGRITY;
    private static final TipoRiesgo UPDATED_TIPO_RIESGO = TipoRiesgo.AVAILABILITY;

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final Clasificacion DEFAULT_CLASIFICACION = Clasificacion.EXECUTION;
    private static final Clasificacion UPDATED_CLASIFICACION = Clasificacion.INTERN;

    private static final Integer DEFAULT_FRECUENCIA = 1;
    private static final Integer UPDATED_FRECUENCIA = 2;

    private static final AfectacionEconomica DEFAULT_AFECTACION_ECONOMICA = AfectacionEconomica.LOWEST;
    private static final AfectacionEconomica UPDATED_AFECTACION_ECONOMICA = AfectacionEconomica.LOW;

    private static final AfectacionReputacional DEFAULT_IMPACTO_REPUTACIONAL = AfectacionReputacional.LOWEST;
    private static final AfectacionReputacional UPDATED_IMPACTO_REPUTACIONAL = AfectacionReputacional.LOW;

    private static final ProbabilidadInherente DEFAULT_PROBABILIDAD_INHERENTE = ProbabilidadInherente.LOWEST;
    private static final ProbabilidadInherente UPDATED_PROBABILIDAD_INHERENTE = ProbabilidadInherente.LOW;

    private static final ImpactoInherente DEFAULT_IMPACTO_INHERENTE = ImpactoInherente.NA;
    private static final ImpactoInherente UPDATED_IMPACTO_INHERENTE = ImpactoInherente.LOWEST;

    private static final ZonaRiesgo DEFAULT_ZONA_RIESGO = ZonaRiesgo.LOWEST;
    private static final ZonaRiesgo UPDATED_ZONA_RIESGO = ZonaRiesgo.LOW;

    private static final Afectacion DEFAULT_AFECTACION = Afectacion.PROBABILITY;
    private static final Afectacion UPDATED_AFECTACION = Afectacion.IMPACT;

    private static final TipoControl DEFAULT_TIPO_CONTROL = TipoControl.PREVENTIVE;
    private static final TipoControl UPDATED_TIPO_CONTROL = TipoControl.DETECTIVE;

    private static final Implementacion DEFAULT_IMPLEMENTACION = Implementacion.AUTOMATIC;
    private static final Implementacion UPDATED_IMPLEMENTACION = Implementacion.MANUAL;

    private static final Integer DEFAULT_CALIFICACION_CONTROL = 100;
    private static final Integer UPDATED_CALIFICACION_CONTROL = 99;

    private static final Boolean DEFAULT_DOCUMENTADO = false;
    private static final Boolean UPDATED_DOCUMENTADO = true;

    private static final FrecuenciaControl DEFAULT_FRECUENCIA_CONTROL = FrecuenciaControl.CONTINUOUS;
    private static final FrecuenciaControl UPDATED_FRECUENCIA_CONTROL = FrecuenciaControl.RANDOM;

    private static final Boolean DEFAULT_EVIDENCIA = false;
    private static final Boolean UPDATED_EVIDENCIA = true;

    private static final Integer DEFAULT_PROBABILIDAD = 1;
    private static final Integer UPDATED_PROBABILIDAD = 2;

    private static final Integer DEFAULT_IMPACTO = 1;
    private static final Integer UPDATED_IMPACTO = 2;

    private static final ProbabilidadResidualFinal DEFAULT_PROBABILIDAD_RESIDUAL_FINAL = ProbabilidadResidualFinal.LOWEST;
    private static final ProbabilidadResidualFinal UPDATED_PROBABILIDAD_RESIDUAL_FINAL = ProbabilidadResidualFinal.LOW;

    private static final ImpactoResidualFinal DEFAULT_IMPACTO_RESIDUAL_FINAL = ImpactoResidualFinal.LOWEST;
    private static final ImpactoResidualFinal UPDATED_IMPACTO_RESIDUAL_FINAL = ImpactoResidualFinal.LOW;

    private static final ZonaRiesgoFinalControl DEFAULT_ZONA_DE_RIESGO_FINAL = ZonaRiesgoFinalControl.LOWEST;
    private static final ZonaRiesgoFinalControl UPDATED_ZONA_DE_RIESGO_FINAL = ZonaRiesgoFinalControl.LOW;

    private static final String DEFAULT_RIESGO_RESIDUAL = "AAAAAAAAAA";
    private static final String UPDATED_RIESGO_RESIDUAL = "BBBBBBBBBB";

    private static final TratamientoRiesgo DEFAULT_TRATAMIENTO = TratamientoRiesgo.REDUCE;
    private static final TratamientoRiesgo UPDATED_TRATAMIENTO = TratamientoRiesgo.TRANSFER;

    private static final String DEFAULT_PLAN_ACCION = "AAAAAAAAAA";
    private static final String UPDATED_PLAN_ACCION = "BBBBBBBBBB";

    private static final String DEFAULT_RESPONSABLE = "AAAAAAAAAA";
    private static final String UPDATED_RESPONSABLE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_IMPLEMENTACION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_IMPLEMENTACION = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_SEGUIMIENTO_CONTROL_EXISTENTE = "AAAAAAAAAA";
    private static final String UPDATED_SEGUIMIENTO_CONTROL_EXISTENTE = "BBBBBBBBBB";

    private static final String DEFAULT_ESTADO = "AAAAAAAAAA";
    private static final String UPDATED_ESTADO = "BBBBBBBBBB";

    private static final String DEFAULT_OBSERVACIONES = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACIONES = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_MONITOREO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_MONITOREO = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/riesgos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private RiesgoRepository riesgoRepository;

    @Mock
    private RiesgoRepository riesgoRepositoryMock;

    @Autowired
    private RiesgoMapper riesgoMapper;

    @Mock
    private RiesgoService riesgoServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRiesgoMockMvc;

    private Riesgo riesgo;

    private Riesgo insertedRiesgo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Riesgo createEntity(EntityManager em) {
        Riesgo riesgo = new Riesgo()
            .proceso(DEFAULT_PROCESO)
            .tipoRiesgo(DEFAULT_TIPO_RIESGO)
            .descripcion(DEFAULT_DESCRIPCION)
            .clasificacion(DEFAULT_CLASIFICACION)
            .frecuencia(DEFAULT_FRECUENCIA)
            .afectacionEconomica(DEFAULT_AFECTACION_ECONOMICA)
            .impactoReputacional(DEFAULT_IMPACTO_REPUTACIONAL)
            .probabilidadInherente(DEFAULT_PROBABILIDAD_INHERENTE)
            .impactoInherente(DEFAULT_IMPACTO_INHERENTE)
            .zonaRiesgo(DEFAULT_ZONA_RIESGO)
            .afectacion(DEFAULT_AFECTACION)
            .tipoControl(DEFAULT_TIPO_CONTROL)
            .implementacion(DEFAULT_IMPLEMENTACION)
            .calificacionControl(DEFAULT_CALIFICACION_CONTROL)
            .documentado(DEFAULT_DOCUMENTADO)
            .frecuenciaControl(DEFAULT_FRECUENCIA_CONTROL)
            .evidencia(DEFAULT_EVIDENCIA)
            .probabilidad(DEFAULT_PROBABILIDAD)
            .impacto(DEFAULT_IMPACTO)
            .probabilidadResidualFinal(DEFAULT_PROBABILIDAD_RESIDUAL_FINAL)
            .impactoResidualFinal(DEFAULT_IMPACTO_RESIDUAL_FINAL)
            .zonaDeRiesgoFinal(DEFAULT_ZONA_DE_RIESGO_FINAL)
            .riesgoResidual(DEFAULT_RIESGO_RESIDUAL)
            .tratamiento(DEFAULT_TRATAMIENTO)
            .planAccion(DEFAULT_PLAN_ACCION)
            .responsable(DEFAULT_RESPONSABLE)
            .fechaImplementacion(DEFAULT_FECHA_IMPLEMENTACION)
            .seguimientoControlExistente(DEFAULT_SEGUIMIENTO_CONTROL_EXISTENTE)
            .estado(DEFAULT_ESTADO)
            .observaciones(DEFAULT_OBSERVACIONES)
            .fechaMonitoreo(DEFAULT_FECHA_MONITOREO);
        // Add required entity
        GrupoActivos grupoActivos;
        if (TestUtil.findAll(em, GrupoActivos.class).isEmpty()) {
            grupoActivos = GrupoActivosResourceIT.createEntity(em);
            em.persist(grupoActivos);
            em.flush();
        } else {
            grupoActivos = TestUtil.findAll(em, GrupoActivos.class).get(0);
        }
        riesgo.getActivos().add(grupoActivos);
        // Add required entity
        Amenazas amenazas;
        if (TestUtil.findAll(em, Amenazas.class).isEmpty()) {
            amenazas = AmenazasResourceIT.createEntity();
            em.persist(amenazas);
            em.flush();
        } else {
            amenazas = TestUtil.findAll(em, Amenazas.class).get(0);
        }
        riesgo.setAmenaza(amenazas);
        // Add required entity
        Vulnerabilidades vulnerabilidades;
        if (TestUtil.findAll(em, Vulnerabilidades.class).isEmpty()) {
            vulnerabilidades = VulnerabilidadesResourceIT.createEntity();
            em.persist(vulnerabilidades);
            em.flush();
        } else {
            vulnerabilidades = TestUtil.findAll(em, Vulnerabilidades.class).get(0);
        }
        riesgo.setVulnerabilidad(vulnerabilidades);
        return riesgo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Riesgo createUpdatedEntity(EntityManager em) {
        Riesgo updatedRiesgo = new Riesgo()
            .proceso(UPDATED_PROCESO)
            .tipoRiesgo(UPDATED_TIPO_RIESGO)
            .descripcion(UPDATED_DESCRIPCION)
            .clasificacion(UPDATED_CLASIFICACION)
            .frecuencia(UPDATED_FRECUENCIA)
            .afectacionEconomica(UPDATED_AFECTACION_ECONOMICA)
            .impactoReputacional(UPDATED_IMPACTO_REPUTACIONAL)
            .probabilidadInherente(UPDATED_PROBABILIDAD_INHERENTE)
            .impactoInherente(UPDATED_IMPACTO_INHERENTE)
            .zonaRiesgo(UPDATED_ZONA_RIESGO)
            .afectacion(UPDATED_AFECTACION)
            .tipoControl(UPDATED_TIPO_CONTROL)
            .implementacion(UPDATED_IMPLEMENTACION)
            .calificacionControl(UPDATED_CALIFICACION_CONTROL)
            .documentado(UPDATED_DOCUMENTADO)
            .frecuenciaControl(UPDATED_FRECUENCIA_CONTROL)
            .evidencia(UPDATED_EVIDENCIA)
            .probabilidad(UPDATED_PROBABILIDAD)
            .impacto(UPDATED_IMPACTO)
            .probabilidadResidualFinal(UPDATED_PROBABILIDAD_RESIDUAL_FINAL)
            .impactoResidualFinal(UPDATED_IMPACTO_RESIDUAL_FINAL)
            .zonaDeRiesgoFinal(UPDATED_ZONA_DE_RIESGO_FINAL)
            .riesgoResidual(UPDATED_RIESGO_RESIDUAL)
            .tratamiento(UPDATED_TRATAMIENTO)
            .planAccion(UPDATED_PLAN_ACCION)
            .responsable(UPDATED_RESPONSABLE)
            .fechaImplementacion(UPDATED_FECHA_IMPLEMENTACION)
            .seguimientoControlExistente(UPDATED_SEGUIMIENTO_CONTROL_EXISTENTE)
            .estado(UPDATED_ESTADO)
            .observaciones(UPDATED_OBSERVACIONES)
            .fechaMonitoreo(UPDATED_FECHA_MONITOREO);
        // Add required entity
        GrupoActivos grupoActivos;
        if (TestUtil.findAll(em, GrupoActivos.class).isEmpty()) {
            grupoActivos = GrupoActivosResourceIT.createUpdatedEntity(em);
            em.persist(grupoActivos);
            em.flush();
        } else {
            grupoActivos = TestUtil.findAll(em, GrupoActivos.class).get(0);
        }
        updatedRiesgo.getActivos().add(grupoActivos);
        // Add required entity
        Amenazas amenazas;
        if (TestUtil.findAll(em, Amenazas.class).isEmpty()) {
            amenazas = AmenazasResourceIT.createUpdatedEntity();
            em.persist(amenazas);
            em.flush();
        } else {
            amenazas = TestUtil.findAll(em, Amenazas.class).get(0);
        }
        updatedRiesgo.setAmenaza(amenazas);
        // Add required entity
        Vulnerabilidades vulnerabilidades;
        if (TestUtil.findAll(em, Vulnerabilidades.class).isEmpty()) {
            vulnerabilidades = VulnerabilidadesResourceIT.createUpdatedEntity();
            em.persist(vulnerabilidades);
            em.flush();
        } else {
            vulnerabilidades = TestUtil.findAll(em, Vulnerabilidades.class).get(0);
        }
        updatedRiesgo.setVulnerabilidad(vulnerabilidades);
        return updatedRiesgo;
    }

    @BeforeEach
    public void initTest() {
        riesgo = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedRiesgo != null) {
            riesgoRepository.delete(insertedRiesgo);
            insertedRiesgo = null;
        }
    }

    @Test
    @Transactional
    void createRiesgo() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Riesgo
        RiesgoDTO riesgoDTO = riesgoMapper.toDto(riesgo);
        var returnedRiesgoDTO = om.readValue(
            restRiesgoMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(riesgoDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            RiesgoDTO.class
        );

        // Validate the Riesgo in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedRiesgo = riesgoMapper.toEntity(returnedRiesgoDTO);
        assertRiesgoUpdatableFieldsEquals(returnedRiesgo, getPersistedRiesgo(returnedRiesgo));

        insertedRiesgo = returnedRiesgo;
    }

    @Test
    @Transactional
    void createRiesgoWithExistingId() throws Exception {
        // Create the Riesgo with an existing ID
        riesgo.setId(1L);
        RiesgoDTO riesgoDTO = riesgoMapper.toDto(riesgo);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRiesgoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(riesgoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Riesgo in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkFrecuenciaIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        riesgo.setFrecuencia(null);

        // Create the Riesgo, which fails.
        RiesgoDTO riesgoDTO = riesgoMapper.toDto(riesgo);

        restRiesgoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(riesgoDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAfectacionEconomicaIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        riesgo.setAfectacionEconomica(null);

        // Create the Riesgo, which fails.
        RiesgoDTO riesgoDTO = riesgoMapper.toDto(riesgo);

        restRiesgoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(riesgoDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAfectacionIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        riesgo.setAfectacion(null);

        // Create the Riesgo, which fails.
        RiesgoDTO riesgoDTO = riesgoMapper.toDto(riesgo);

        restRiesgoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(riesgoDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTipoControlIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        riesgo.setTipoControl(null);

        // Create the Riesgo, which fails.
        RiesgoDTO riesgoDTO = riesgoMapper.toDto(riesgo);

        restRiesgoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(riesgoDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkImplementacionIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        riesgo.setImplementacion(null);

        // Create the Riesgo, which fails.
        RiesgoDTO riesgoDTO = riesgoMapper.toDto(riesgo);

        restRiesgoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(riesgoDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFrecuenciaControlIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        riesgo.setFrecuenciaControl(null);

        // Create the Riesgo, which fails.
        RiesgoDTO riesgoDTO = riesgoMapper.toDto(riesgo);

        restRiesgoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(riesgoDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTratamientoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        riesgo.setTratamiento(null);

        // Create the Riesgo, which fails.
        RiesgoDTO riesgoDTO = riesgoMapper.toDto(riesgo);

        restRiesgoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(riesgoDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRiesgos() throws Exception {
        // Initialize the database
        insertedRiesgo = riesgoRepository.saveAndFlush(riesgo);

        // Get all the riesgoList
        restRiesgoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(riesgo.getId().intValue())))
            .andExpect(jsonPath("$.[*].proceso").value(hasItem(DEFAULT_PROCESO)))
            .andExpect(jsonPath("$.[*].tipoRiesgo").value(hasItem(DEFAULT_TIPO_RIESGO.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].clasificacion").value(hasItem(DEFAULT_CLASIFICACION.toString())))
            .andExpect(jsonPath("$.[*].frecuencia").value(hasItem(DEFAULT_FRECUENCIA)))
            .andExpect(jsonPath("$.[*].afectacionEconomica").value(hasItem(DEFAULT_AFECTACION_ECONOMICA.toString())))
            .andExpect(jsonPath("$.[*].impactoReputacional").value(hasItem(DEFAULT_IMPACTO_REPUTACIONAL.toString())))
            .andExpect(jsonPath("$.[*].probabilidadInherente").value(hasItem(DEFAULT_PROBABILIDAD_INHERENTE.toString())))
            .andExpect(jsonPath("$.[*].impactoInherente").value(hasItem(DEFAULT_IMPACTO_INHERENTE.toString())))
            .andExpect(jsonPath("$.[*].zonaRiesgo").value(hasItem(DEFAULT_ZONA_RIESGO.toString())))
            .andExpect(jsonPath("$.[*].afectacion").value(hasItem(DEFAULT_AFECTACION.toString())))
            .andExpect(jsonPath("$.[*].tipoControl").value(hasItem(DEFAULT_TIPO_CONTROL.toString())))
            .andExpect(jsonPath("$.[*].implementacion").value(hasItem(DEFAULT_IMPLEMENTACION.toString())))
            .andExpect(jsonPath("$.[*].calificacionControl").value(hasItem(DEFAULT_CALIFICACION_CONTROL)))
            .andExpect(jsonPath("$.[*].documentado").value(hasItem(DEFAULT_DOCUMENTADO.booleanValue())))
            .andExpect(jsonPath("$.[*].frecuenciaControl").value(hasItem(DEFAULT_FRECUENCIA_CONTROL.toString())))
            .andExpect(jsonPath("$.[*].evidencia").value(hasItem(DEFAULT_EVIDENCIA.booleanValue())))
            .andExpect(jsonPath("$.[*].probabilidad").value(hasItem(DEFAULT_PROBABILIDAD)))
            .andExpect(jsonPath("$.[*].impacto").value(hasItem(DEFAULT_IMPACTO)))
            .andExpect(jsonPath("$.[*].probabilidadResidualFinal").value(hasItem(DEFAULT_PROBABILIDAD_RESIDUAL_FINAL.toString())))
            .andExpect(jsonPath("$.[*].impactoResidualFinal").value(hasItem(DEFAULT_IMPACTO_RESIDUAL_FINAL.toString())))
            .andExpect(jsonPath("$.[*].zonaDeRiesgoFinal").value(hasItem(DEFAULT_ZONA_DE_RIESGO_FINAL.toString())))
            .andExpect(jsonPath("$.[*].riesgoResidual").value(hasItem(DEFAULT_RIESGO_RESIDUAL)))
            .andExpect(jsonPath("$.[*].tratamiento").value(hasItem(DEFAULT_TRATAMIENTO.toString())))
            .andExpect(jsonPath("$.[*].planAccion").value(hasItem(DEFAULT_PLAN_ACCION)))
            .andExpect(jsonPath("$.[*].responsable").value(hasItem(DEFAULT_RESPONSABLE)))
            .andExpect(jsonPath("$.[*].fechaImplementacion").value(hasItem(DEFAULT_FECHA_IMPLEMENTACION.toString())))
            .andExpect(jsonPath("$.[*].seguimientoControlExistente").value(hasItem(DEFAULT_SEGUIMIENTO_CONTROL_EXISTENTE)))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO)))
            .andExpect(jsonPath("$.[*].observaciones").value(hasItem(DEFAULT_OBSERVACIONES)))
            .andExpect(jsonPath("$.[*].fechaMonitoreo").value(hasItem(DEFAULT_FECHA_MONITOREO.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllRiesgosWithEagerRelationshipsIsEnabled() throws Exception {
        when(riesgoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restRiesgoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(riesgoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllRiesgosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(riesgoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restRiesgoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(riesgoRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getRiesgo() throws Exception {
        // Initialize the database
        insertedRiesgo = riesgoRepository.saveAndFlush(riesgo);

        // Get the riesgo
        restRiesgoMockMvc
            .perform(get(ENTITY_API_URL_ID, riesgo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(riesgo.getId().intValue()))
            .andExpect(jsonPath("$.proceso").value(DEFAULT_PROCESO))
            .andExpect(jsonPath("$.tipoRiesgo").value(DEFAULT_TIPO_RIESGO.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION))
            .andExpect(jsonPath("$.clasificacion").value(DEFAULT_CLASIFICACION.toString()))
            .andExpect(jsonPath("$.frecuencia").value(DEFAULT_FRECUENCIA))
            .andExpect(jsonPath("$.afectacionEconomica").value(DEFAULT_AFECTACION_ECONOMICA.toString()))
            .andExpect(jsonPath("$.impactoReputacional").value(DEFAULT_IMPACTO_REPUTACIONAL.toString()))
            .andExpect(jsonPath("$.probabilidadInherente").value(DEFAULT_PROBABILIDAD_INHERENTE.toString()))
            .andExpect(jsonPath("$.impactoInherente").value(DEFAULT_IMPACTO_INHERENTE.toString()))
            .andExpect(jsonPath("$.zonaRiesgo").value(DEFAULT_ZONA_RIESGO.toString()))
            .andExpect(jsonPath("$.afectacion").value(DEFAULT_AFECTACION.toString()))
            .andExpect(jsonPath("$.tipoControl").value(DEFAULT_TIPO_CONTROL.toString()))
            .andExpect(jsonPath("$.implementacion").value(DEFAULT_IMPLEMENTACION.toString()))
            .andExpect(jsonPath("$.calificacionControl").value(DEFAULT_CALIFICACION_CONTROL))
            .andExpect(jsonPath("$.documentado").value(DEFAULT_DOCUMENTADO.booleanValue()))
            .andExpect(jsonPath("$.frecuenciaControl").value(DEFAULT_FRECUENCIA_CONTROL.toString()))
            .andExpect(jsonPath("$.evidencia").value(DEFAULT_EVIDENCIA.booleanValue()))
            .andExpect(jsonPath("$.probabilidad").value(DEFAULT_PROBABILIDAD))
            .andExpect(jsonPath("$.impacto").value(DEFAULT_IMPACTO))
            .andExpect(jsonPath("$.probabilidadResidualFinal").value(DEFAULT_PROBABILIDAD_RESIDUAL_FINAL.toString()))
            .andExpect(jsonPath("$.impactoResidualFinal").value(DEFAULT_IMPACTO_RESIDUAL_FINAL.toString()))
            .andExpect(jsonPath("$.zonaDeRiesgoFinal").value(DEFAULT_ZONA_DE_RIESGO_FINAL.toString()))
            .andExpect(jsonPath("$.riesgoResidual").value(DEFAULT_RIESGO_RESIDUAL))
            .andExpect(jsonPath("$.tratamiento").value(DEFAULT_TRATAMIENTO.toString()))
            .andExpect(jsonPath("$.planAccion").value(DEFAULT_PLAN_ACCION))
            .andExpect(jsonPath("$.responsable").value(DEFAULT_RESPONSABLE))
            .andExpect(jsonPath("$.fechaImplementacion").value(DEFAULT_FECHA_IMPLEMENTACION.toString()))
            .andExpect(jsonPath("$.seguimientoControlExistente").value(DEFAULT_SEGUIMIENTO_CONTROL_EXISTENTE))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO))
            .andExpect(jsonPath("$.observaciones").value(DEFAULT_OBSERVACIONES))
            .andExpect(jsonPath("$.fechaMonitoreo").value(DEFAULT_FECHA_MONITOREO.toString()));
    }

    @Test
    @Transactional
    void getNonExistingRiesgo() throws Exception {
        // Get the riesgo
        restRiesgoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRiesgo() throws Exception {
        // Initialize the database
        insertedRiesgo = riesgoRepository.saveAndFlush(riesgo);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the riesgo
        Riesgo updatedRiesgo = riesgoRepository.findById(riesgo.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedRiesgo are not directly saved in db
        em.detach(updatedRiesgo);
        updatedRiesgo
            .proceso(UPDATED_PROCESO)
            .tipoRiesgo(UPDATED_TIPO_RIESGO)
            .descripcion(UPDATED_DESCRIPCION)
            .clasificacion(UPDATED_CLASIFICACION)
            .frecuencia(UPDATED_FRECUENCIA)
            .afectacionEconomica(UPDATED_AFECTACION_ECONOMICA)
            .impactoReputacional(UPDATED_IMPACTO_REPUTACIONAL)
            .probabilidadInherente(UPDATED_PROBABILIDAD_INHERENTE)
            .impactoInherente(UPDATED_IMPACTO_INHERENTE)
            .zonaRiesgo(UPDATED_ZONA_RIESGO)
            .afectacion(UPDATED_AFECTACION)
            .tipoControl(UPDATED_TIPO_CONTROL)
            .implementacion(UPDATED_IMPLEMENTACION)
            .calificacionControl(UPDATED_CALIFICACION_CONTROL)
            .documentado(UPDATED_DOCUMENTADO)
            .frecuenciaControl(UPDATED_FRECUENCIA_CONTROL)
            .evidencia(UPDATED_EVIDENCIA)
            .probabilidad(UPDATED_PROBABILIDAD)
            .impacto(UPDATED_IMPACTO)
            .probabilidadResidualFinal(UPDATED_PROBABILIDAD_RESIDUAL_FINAL)
            .impactoResidualFinal(UPDATED_IMPACTO_RESIDUAL_FINAL)
            .zonaDeRiesgoFinal(UPDATED_ZONA_DE_RIESGO_FINAL)
            .riesgoResidual(UPDATED_RIESGO_RESIDUAL)
            .tratamiento(UPDATED_TRATAMIENTO)
            .planAccion(UPDATED_PLAN_ACCION)
            .responsable(UPDATED_RESPONSABLE)
            .fechaImplementacion(UPDATED_FECHA_IMPLEMENTACION)
            .seguimientoControlExistente(UPDATED_SEGUIMIENTO_CONTROL_EXISTENTE)
            .estado(UPDATED_ESTADO)
            .observaciones(UPDATED_OBSERVACIONES)
            .fechaMonitoreo(UPDATED_FECHA_MONITOREO);
        RiesgoDTO riesgoDTO = riesgoMapper.toDto(updatedRiesgo);

        restRiesgoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, riesgoDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(riesgoDTO))
            )
            .andExpect(status().isOk());

        // Validate the Riesgo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedRiesgoToMatchAllProperties(updatedRiesgo);
    }

    @Test
    @Transactional
    void putNonExistingRiesgo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        riesgo.setId(longCount.incrementAndGet());

        // Create the Riesgo
        RiesgoDTO riesgoDTO = riesgoMapper.toDto(riesgo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRiesgoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, riesgoDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(riesgoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Riesgo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRiesgo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        riesgo.setId(longCount.incrementAndGet());

        // Create the Riesgo
        RiesgoDTO riesgoDTO = riesgoMapper.toDto(riesgo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRiesgoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(riesgoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Riesgo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRiesgo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        riesgo.setId(longCount.incrementAndGet());

        // Create the Riesgo
        RiesgoDTO riesgoDTO = riesgoMapper.toDto(riesgo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRiesgoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(riesgoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Riesgo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRiesgoWithPatch() throws Exception {
        // Initialize the database
        insertedRiesgo = riesgoRepository.saveAndFlush(riesgo);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the riesgo using partial update
        Riesgo partialUpdatedRiesgo = new Riesgo();
        partialUpdatedRiesgo.setId(riesgo.getId());

        partialUpdatedRiesgo
            .proceso(UPDATED_PROCESO)
            .descripcion(UPDATED_DESCRIPCION)
            .afectacionEconomica(UPDATED_AFECTACION_ECONOMICA)
            .probabilidadInherente(UPDATED_PROBABILIDAD_INHERENTE)
            .implementacion(UPDATED_IMPLEMENTACION)
            .calificacionControl(UPDATED_CALIFICACION_CONTROL)
            .documentado(UPDATED_DOCUMENTADO)
            .frecuenciaControl(UPDATED_FRECUENCIA_CONTROL)
            .probabilidad(UPDATED_PROBABILIDAD)
            .zonaDeRiesgoFinal(UPDATED_ZONA_DE_RIESGO_FINAL)
            .riesgoResidual(UPDATED_RIESGO_RESIDUAL)
            .fechaImplementacion(UPDATED_FECHA_IMPLEMENTACION);

        restRiesgoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRiesgo.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRiesgo))
            )
            .andExpect(status().isOk());

        // Validate the Riesgo in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRiesgoUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedRiesgo, riesgo), getPersistedRiesgo(riesgo));
    }

    @Test
    @Transactional
    void fullUpdateRiesgoWithPatch() throws Exception {
        // Initialize the database
        insertedRiesgo = riesgoRepository.saveAndFlush(riesgo);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the riesgo using partial update
        Riesgo partialUpdatedRiesgo = new Riesgo();
        partialUpdatedRiesgo.setId(riesgo.getId());

        partialUpdatedRiesgo
            .proceso(UPDATED_PROCESO)
            .tipoRiesgo(UPDATED_TIPO_RIESGO)
            .descripcion(UPDATED_DESCRIPCION)
            .clasificacion(UPDATED_CLASIFICACION)
            .frecuencia(UPDATED_FRECUENCIA)
            .afectacionEconomica(UPDATED_AFECTACION_ECONOMICA)
            .impactoReputacional(UPDATED_IMPACTO_REPUTACIONAL)
            .probabilidadInherente(UPDATED_PROBABILIDAD_INHERENTE)
            .impactoInherente(UPDATED_IMPACTO_INHERENTE)
            .zonaRiesgo(UPDATED_ZONA_RIESGO)
            .afectacion(UPDATED_AFECTACION)
            .tipoControl(UPDATED_TIPO_CONTROL)
            .implementacion(UPDATED_IMPLEMENTACION)
            .calificacionControl(UPDATED_CALIFICACION_CONTROL)
            .documentado(UPDATED_DOCUMENTADO)
            .frecuenciaControl(UPDATED_FRECUENCIA_CONTROL)
            .evidencia(UPDATED_EVIDENCIA)
            .probabilidad(UPDATED_PROBABILIDAD)
            .impacto(UPDATED_IMPACTO)
            .probabilidadResidualFinal(UPDATED_PROBABILIDAD_RESIDUAL_FINAL)
            .impactoResidualFinal(UPDATED_IMPACTO_RESIDUAL_FINAL)
            .zonaDeRiesgoFinal(UPDATED_ZONA_DE_RIESGO_FINAL)
            .riesgoResidual(UPDATED_RIESGO_RESIDUAL)
            .tratamiento(UPDATED_TRATAMIENTO)
            .planAccion(UPDATED_PLAN_ACCION)
            .responsable(UPDATED_RESPONSABLE)
            .fechaImplementacion(UPDATED_FECHA_IMPLEMENTACION)
            .seguimientoControlExistente(UPDATED_SEGUIMIENTO_CONTROL_EXISTENTE)
            .estado(UPDATED_ESTADO)
            .observaciones(UPDATED_OBSERVACIONES)
            .fechaMonitoreo(UPDATED_FECHA_MONITOREO);

        restRiesgoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRiesgo.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRiesgo))
            )
            .andExpect(status().isOk());

        // Validate the Riesgo in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRiesgoUpdatableFieldsEquals(partialUpdatedRiesgo, getPersistedRiesgo(partialUpdatedRiesgo));
    }

    @Test
    @Transactional
    void patchNonExistingRiesgo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        riesgo.setId(longCount.incrementAndGet());

        // Create the Riesgo
        RiesgoDTO riesgoDTO = riesgoMapper.toDto(riesgo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRiesgoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, riesgoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(riesgoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Riesgo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRiesgo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        riesgo.setId(longCount.incrementAndGet());

        // Create the Riesgo
        RiesgoDTO riesgoDTO = riesgoMapper.toDto(riesgo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRiesgoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(riesgoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Riesgo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRiesgo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        riesgo.setId(longCount.incrementAndGet());

        // Create the Riesgo
        RiesgoDTO riesgoDTO = riesgoMapper.toDto(riesgo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRiesgoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(riesgoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Riesgo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRiesgo() throws Exception {
        // Initialize the database
        insertedRiesgo = riesgoRepository.saveAndFlush(riesgo);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the riesgo
        restRiesgoMockMvc
            .perform(delete(ENTITY_API_URL_ID, riesgo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return riesgoRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected Riesgo getPersistedRiesgo(Riesgo riesgo) {
        return riesgoRepository.findById(riesgo.getId()).orElseThrow();
    }

    protected void assertPersistedRiesgoToMatchAllProperties(Riesgo expectedRiesgo) {
        assertRiesgoAllPropertiesEquals(expectedRiesgo, getPersistedRiesgo(expectedRiesgo));
    }

    protected void assertPersistedRiesgoToMatchUpdatableProperties(Riesgo expectedRiesgo) {
        assertRiesgoAllUpdatablePropertiesEquals(expectedRiesgo, getPersistedRiesgo(expectedRiesgo));
    }
}
