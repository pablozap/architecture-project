package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.ActivoInformacionAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.ActivoInformacion;
import com.mycompany.myapp.domain.enumeration.ClasificacionInformacion1712;
import com.mycompany.myapp.domain.enumeration.Formato;
import com.mycompany.myapp.domain.enumeration.Proceso;
import com.mycompany.myapp.domain.enumeration.TipoActivo;
import com.mycompany.myapp.repository.ActivoInformacionRepository;
import com.mycompany.myapp.service.ActivoInformacionService;
import com.mycompany.myapp.service.dto.ActivoInformacionDTO;
import com.mycompany.myapp.service.mapper.ActivoInformacionMapper;
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
 * Integration tests for the {@link ActivoInformacionResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ActivoInformacionResourceIT {

    private static final Proceso DEFAULT_PROCESO = Proceso.MISSION;
    private static final Proceso UPDATED_PROCESO = Proceso.STRATEGIC;

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final TipoActivo DEFAULT_TIPO_ACTIVO = TipoActivo.STI;
    private static final TipoActivo UPDATED_TIPO_ACTIVO = TipoActivo.DAT;

    private static final Boolean DEFAULT_LEY_1581 = false;
    private static final Boolean UPDATED_LEY_1581 = true;

    private static final ClasificacionInformacion1712 DEFAULT_CLASIFICACION_INFORMACION_1712 = ClasificacionInformacion1712.RESTRICTED;
    private static final ClasificacionInformacion1712 UPDATED_CLASIFICACION_INFORMACION_1712 = ClasificacionInformacion1712.CLASSIFIED;

    private static final Boolean DEFAULT_LEY_1266 = false;
    private static final Boolean UPDATED_LEY_1266 = true;

    private static final Formato DEFAULT_FORMATO = Formato.EXCEL;
    private static final Formato UPDATED_FORMATO = Formato.TEXT;

    private static final String DEFAULT_PROPIETARIO = "AAAAAAAAAA";
    private static final String UPDATED_PROPIETARIO = "BBBBBBBBBB";

    private static final String DEFAULT_USUARIO = "AAAAAAAAAA";
    private static final String UPDATED_USUARIO = "BBBBBBBBBB";

    private static final String DEFAULT_CUSTODIO = "AAAAAAAAAA";
    private static final String UPDATED_CUSTODIO = "BBBBBBBBBB";

    private static final String DEFAULT_USUARIO_FINAL = "AAAAAAAAAA";
    private static final String UPDATED_USUARIO_FINAL = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_ESTADO_ACTIVO = "AAAAAAAAAA";
    private static final String UPDATED_ESTADO_ACTIVO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_INGRESO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_INGRESO = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_FECHA_RETIRO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_RETIRO = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/activo-informacions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ActivoInformacionRepository activoInformacionRepository;

    @Mock
    private ActivoInformacionRepository activoInformacionRepositoryMock;

    @Autowired
    private ActivoInformacionMapper activoInformacionMapper;

    @Mock
    private ActivoInformacionService activoInformacionServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restActivoInformacionMockMvc;

    private ActivoInformacion activoInformacion;

    private ActivoInformacion insertedActivoInformacion;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ActivoInformacion createEntity() {
        return new ActivoInformacion()
            .proceso(DEFAULT_PROCESO)
            .nombre(DEFAULT_NOMBRE)
            .descripcion(DEFAULT_DESCRIPCION)
            .tipoActivo(DEFAULT_TIPO_ACTIVO)
            .ley1581(DEFAULT_LEY_1581)
            .clasificacionInformacion1712(DEFAULT_CLASIFICACION_INFORMACION_1712)
            .ley1266(DEFAULT_LEY_1266)
            .formato(DEFAULT_FORMATO)
            .propietario(DEFAULT_PROPIETARIO)
            .usuario(DEFAULT_USUARIO)
            .custodio(DEFAULT_CUSTODIO)
            .usuarioFinal(DEFAULT_USUARIO_FINAL)
            .fecha(DEFAULT_FECHA)
            .estadoActivo(DEFAULT_ESTADO_ACTIVO)
            .fechaIngreso(DEFAULT_FECHA_INGRESO)
            .fechaRetiro(DEFAULT_FECHA_RETIRO);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ActivoInformacion createUpdatedEntity() {
        return new ActivoInformacion()
            .proceso(UPDATED_PROCESO)
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .tipoActivo(UPDATED_TIPO_ACTIVO)
            .ley1581(UPDATED_LEY_1581)
            .clasificacionInformacion1712(UPDATED_CLASIFICACION_INFORMACION_1712)
            .ley1266(UPDATED_LEY_1266)
            .formato(UPDATED_FORMATO)
            .propietario(UPDATED_PROPIETARIO)
            .usuario(UPDATED_USUARIO)
            .custodio(UPDATED_CUSTODIO)
            .usuarioFinal(UPDATED_USUARIO_FINAL)
            .fecha(UPDATED_FECHA)
            .estadoActivo(UPDATED_ESTADO_ACTIVO)
            .fechaIngreso(UPDATED_FECHA_INGRESO)
            .fechaRetiro(UPDATED_FECHA_RETIRO);
    }

    @BeforeEach
    public void initTest() {
        activoInformacion = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedActivoInformacion != null) {
            activoInformacionRepository.delete(insertedActivoInformacion);
            insertedActivoInformacion = null;
        }
    }

    @Test
    @Transactional
    void createActivoInformacion() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ActivoInformacion
        ActivoInformacionDTO activoInformacionDTO = activoInformacionMapper.toDto(activoInformacion);
        var returnedActivoInformacionDTO = om.readValue(
            restActivoInformacionMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(activoInformacionDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ActivoInformacionDTO.class
        );

        // Validate the ActivoInformacion in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedActivoInformacion = activoInformacionMapper.toEntity(returnedActivoInformacionDTO);
        assertActivoInformacionUpdatableFieldsEquals(returnedActivoInformacion, getPersistedActivoInformacion(returnedActivoInformacion));

        insertedActivoInformacion = returnedActivoInformacion;
    }

    @Test
    @Transactional
    void createActivoInformacionWithExistingId() throws Exception {
        // Create the ActivoInformacion with an existing ID
        activoInformacion.setId(1L);
        ActivoInformacionDTO activoInformacionDTO = activoInformacionMapper.toDto(activoInformacion);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restActivoInformacionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(activoInformacionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ActivoInformacion in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNombreIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        activoInformacion.setNombre(null);

        // Create the ActivoInformacion, which fails.
        ActivoInformacionDTO activoInformacionDTO = activoInformacionMapper.toDto(activoInformacion);

        restActivoInformacionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(activoInformacionDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDescripcionIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        activoInformacion.setDescripcion(null);

        // Create the ActivoInformacion, which fails.
        ActivoInformacionDTO activoInformacionDTO = activoInformacionMapper.toDto(activoInformacion);

        restActivoInformacionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(activoInformacionDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTipoActivoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        activoInformacion.setTipoActivo(null);

        // Create the ActivoInformacion, which fails.
        ActivoInformacionDTO activoInformacionDTO = activoInformacionMapper.toDto(activoInformacion);

        restActivoInformacionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(activoInformacionDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLey1581IsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        activoInformacion.setLey1581(null);

        // Create the ActivoInformacion, which fails.
        ActivoInformacionDTO activoInformacionDTO = activoInformacionMapper.toDto(activoInformacion);

        restActivoInformacionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(activoInformacionDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkClasificacionInformacion1712IsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        activoInformacion.setClasificacionInformacion1712(null);

        // Create the ActivoInformacion, which fails.
        ActivoInformacionDTO activoInformacionDTO = activoInformacionMapper.toDto(activoInformacion);

        restActivoInformacionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(activoInformacionDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLey1266IsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        activoInformacion.setLey1266(null);

        // Create the ActivoInformacion, which fails.
        ActivoInformacionDTO activoInformacionDTO = activoInformacionMapper.toDto(activoInformacion);

        restActivoInformacionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(activoInformacionDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllActivoInformacions() throws Exception {
        // Initialize the database
        insertedActivoInformacion = activoInformacionRepository.saveAndFlush(activoInformacion);

        // Get all the activoInformacionList
        restActivoInformacionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(activoInformacion.getId().intValue())))
            .andExpect(jsonPath("$.[*].proceso").value(hasItem(DEFAULT_PROCESO.toString())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].tipoActivo").value(hasItem(DEFAULT_TIPO_ACTIVO.toString())))
            .andExpect(jsonPath("$.[*].ley1581").value(hasItem(DEFAULT_LEY_1581.booleanValue())))
            .andExpect(jsonPath("$.[*].clasificacionInformacion1712").value(hasItem(DEFAULT_CLASIFICACION_INFORMACION_1712.toString())))
            .andExpect(jsonPath("$.[*].ley1266").value(hasItem(DEFAULT_LEY_1266.booleanValue())))
            .andExpect(jsonPath("$.[*].formato").value(hasItem(DEFAULT_FORMATO.toString())))
            .andExpect(jsonPath("$.[*].propietario").value(hasItem(DEFAULT_PROPIETARIO)))
            .andExpect(jsonPath("$.[*].usuario").value(hasItem(DEFAULT_USUARIO)))
            .andExpect(jsonPath("$.[*].custodio").value(hasItem(DEFAULT_CUSTODIO)))
            .andExpect(jsonPath("$.[*].usuarioFinal").value(hasItem(DEFAULT_USUARIO_FINAL)))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].estadoActivo").value(hasItem(DEFAULT_ESTADO_ACTIVO)))
            .andExpect(jsonPath("$.[*].fechaIngreso").value(hasItem(DEFAULT_FECHA_INGRESO.toString())))
            .andExpect(jsonPath("$.[*].fechaRetiro").value(hasItem(DEFAULT_FECHA_RETIRO.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllActivoInformacionsWithEagerRelationshipsIsEnabled() throws Exception {
        when(activoInformacionServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restActivoInformacionMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(activoInformacionServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllActivoInformacionsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(activoInformacionServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restActivoInformacionMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(activoInformacionRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getActivoInformacion() throws Exception {
        // Initialize the database
        insertedActivoInformacion = activoInformacionRepository.saveAndFlush(activoInformacion);

        // Get the activoInformacion
        restActivoInformacionMockMvc
            .perform(get(ENTITY_API_URL_ID, activoInformacion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(activoInformacion.getId().intValue()))
            .andExpect(jsonPath("$.proceso").value(DEFAULT_PROCESO.toString()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION))
            .andExpect(jsonPath("$.tipoActivo").value(DEFAULT_TIPO_ACTIVO.toString()))
            .andExpect(jsonPath("$.ley1581").value(DEFAULT_LEY_1581.booleanValue()))
            .andExpect(jsonPath("$.clasificacionInformacion1712").value(DEFAULT_CLASIFICACION_INFORMACION_1712.toString()))
            .andExpect(jsonPath("$.ley1266").value(DEFAULT_LEY_1266.booleanValue()))
            .andExpect(jsonPath("$.formato").value(DEFAULT_FORMATO.toString()))
            .andExpect(jsonPath("$.propietario").value(DEFAULT_PROPIETARIO))
            .andExpect(jsonPath("$.usuario").value(DEFAULT_USUARIO))
            .andExpect(jsonPath("$.custodio").value(DEFAULT_CUSTODIO))
            .andExpect(jsonPath("$.usuarioFinal").value(DEFAULT_USUARIO_FINAL))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.estadoActivo").value(DEFAULT_ESTADO_ACTIVO))
            .andExpect(jsonPath("$.fechaIngreso").value(DEFAULT_FECHA_INGRESO.toString()))
            .andExpect(jsonPath("$.fechaRetiro").value(DEFAULT_FECHA_RETIRO.toString()));
    }

    @Test
    @Transactional
    void getNonExistingActivoInformacion() throws Exception {
        // Get the activoInformacion
        restActivoInformacionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingActivoInformacion() throws Exception {
        // Initialize the database
        insertedActivoInformacion = activoInformacionRepository.saveAndFlush(activoInformacion);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the activoInformacion
        ActivoInformacion updatedActivoInformacion = activoInformacionRepository.findById(activoInformacion.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedActivoInformacion are not directly saved in db
        em.detach(updatedActivoInformacion);
        updatedActivoInformacion
            .proceso(UPDATED_PROCESO)
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .tipoActivo(UPDATED_TIPO_ACTIVO)
            .ley1581(UPDATED_LEY_1581)
            .clasificacionInformacion1712(UPDATED_CLASIFICACION_INFORMACION_1712)
            .ley1266(UPDATED_LEY_1266)
            .formato(UPDATED_FORMATO)
            .propietario(UPDATED_PROPIETARIO)
            .usuario(UPDATED_USUARIO)
            .custodio(UPDATED_CUSTODIO)
            .usuarioFinal(UPDATED_USUARIO_FINAL)
            .fecha(UPDATED_FECHA)
            .estadoActivo(UPDATED_ESTADO_ACTIVO)
            .fechaIngreso(UPDATED_FECHA_INGRESO)
            .fechaRetiro(UPDATED_FECHA_RETIRO);
        ActivoInformacionDTO activoInformacionDTO = activoInformacionMapper.toDto(updatedActivoInformacion);

        restActivoInformacionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, activoInformacionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(activoInformacionDTO))
            )
            .andExpect(status().isOk());

        // Validate the ActivoInformacion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedActivoInformacionToMatchAllProperties(updatedActivoInformacion);
    }

    @Test
    @Transactional
    void putNonExistingActivoInformacion() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        activoInformacion.setId(longCount.incrementAndGet());

        // Create the ActivoInformacion
        ActivoInformacionDTO activoInformacionDTO = activoInformacionMapper.toDto(activoInformacion);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restActivoInformacionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, activoInformacionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(activoInformacionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ActivoInformacion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchActivoInformacion() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        activoInformacion.setId(longCount.incrementAndGet());

        // Create the ActivoInformacion
        ActivoInformacionDTO activoInformacionDTO = activoInformacionMapper.toDto(activoInformacion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActivoInformacionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(activoInformacionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ActivoInformacion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamActivoInformacion() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        activoInformacion.setId(longCount.incrementAndGet());

        // Create the ActivoInformacion
        ActivoInformacionDTO activoInformacionDTO = activoInformacionMapper.toDto(activoInformacion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActivoInformacionMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(activoInformacionDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ActivoInformacion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateActivoInformacionWithPatch() throws Exception {
        // Initialize the database
        insertedActivoInformacion = activoInformacionRepository.saveAndFlush(activoInformacion);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the activoInformacion using partial update
        ActivoInformacion partialUpdatedActivoInformacion = new ActivoInformacion();
        partialUpdatedActivoInformacion.setId(activoInformacion.getId());

        partialUpdatedActivoInformacion
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .tipoActivo(UPDATED_TIPO_ACTIVO)
            .ley1581(UPDATED_LEY_1581)
            .clasificacionInformacion1712(UPDATED_CLASIFICACION_INFORMACION_1712)
            .ley1266(UPDATED_LEY_1266)
            .formato(UPDATED_FORMATO)
            .propietario(UPDATED_PROPIETARIO)
            .custodio(UPDATED_CUSTODIO);

        restActivoInformacionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedActivoInformacion.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedActivoInformacion))
            )
            .andExpect(status().isOk());

        // Validate the ActivoInformacion in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertActivoInformacionUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedActivoInformacion, activoInformacion),
            getPersistedActivoInformacion(activoInformacion)
        );
    }

    @Test
    @Transactional
    void fullUpdateActivoInformacionWithPatch() throws Exception {
        // Initialize the database
        insertedActivoInformacion = activoInformacionRepository.saveAndFlush(activoInformacion);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the activoInformacion using partial update
        ActivoInformacion partialUpdatedActivoInformacion = new ActivoInformacion();
        partialUpdatedActivoInformacion.setId(activoInformacion.getId());

        partialUpdatedActivoInformacion
            .proceso(UPDATED_PROCESO)
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .tipoActivo(UPDATED_TIPO_ACTIVO)
            .ley1581(UPDATED_LEY_1581)
            .clasificacionInformacion1712(UPDATED_CLASIFICACION_INFORMACION_1712)
            .ley1266(UPDATED_LEY_1266)
            .formato(UPDATED_FORMATO)
            .propietario(UPDATED_PROPIETARIO)
            .usuario(UPDATED_USUARIO)
            .custodio(UPDATED_CUSTODIO)
            .usuarioFinal(UPDATED_USUARIO_FINAL)
            .fecha(UPDATED_FECHA)
            .estadoActivo(UPDATED_ESTADO_ACTIVO)
            .fechaIngreso(UPDATED_FECHA_INGRESO)
            .fechaRetiro(UPDATED_FECHA_RETIRO);

        restActivoInformacionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedActivoInformacion.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedActivoInformacion))
            )
            .andExpect(status().isOk());

        // Validate the ActivoInformacion in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertActivoInformacionUpdatableFieldsEquals(
            partialUpdatedActivoInformacion,
            getPersistedActivoInformacion(partialUpdatedActivoInformacion)
        );
    }

    @Test
    @Transactional
    void patchNonExistingActivoInformacion() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        activoInformacion.setId(longCount.incrementAndGet());

        // Create the ActivoInformacion
        ActivoInformacionDTO activoInformacionDTO = activoInformacionMapper.toDto(activoInformacion);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restActivoInformacionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, activoInformacionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(activoInformacionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ActivoInformacion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchActivoInformacion() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        activoInformacion.setId(longCount.incrementAndGet());

        // Create the ActivoInformacion
        ActivoInformacionDTO activoInformacionDTO = activoInformacionMapper.toDto(activoInformacion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActivoInformacionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(activoInformacionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ActivoInformacion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamActivoInformacion() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        activoInformacion.setId(longCount.incrementAndGet());

        // Create the ActivoInformacion
        ActivoInformacionDTO activoInformacionDTO = activoInformacionMapper.toDto(activoInformacion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restActivoInformacionMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(activoInformacionDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ActivoInformacion in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteActivoInformacion() throws Exception {
        // Initialize the database
        insertedActivoInformacion = activoInformacionRepository.saveAndFlush(activoInformacion);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the activoInformacion
        restActivoInformacionMockMvc
            .perform(delete(ENTITY_API_URL_ID, activoInformacion.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return activoInformacionRepository.count();
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

    protected ActivoInformacion getPersistedActivoInformacion(ActivoInformacion activoInformacion) {
        return activoInformacionRepository.findById(activoInformacion.getId()).orElseThrow();
    }

    protected void assertPersistedActivoInformacionToMatchAllProperties(ActivoInformacion expectedActivoInformacion) {
        assertActivoInformacionAllPropertiesEquals(expectedActivoInformacion, getPersistedActivoInformacion(expectedActivoInformacion));
    }

    protected void assertPersistedActivoInformacionToMatchUpdatableProperties(ActivoInformacion expectedActivoInformacion) {
        assertActivoInformacionAllUpdatablePropertiesEquals(
            expectedActivoInformacion,
            getPersistedActivoInformacion(expectedActivoInformacion)
        );
    }
}
