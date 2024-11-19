package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.GrupoActivosAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.GrupoActivos;
import com.mycompany.myapp.domain.enumeration.Confidencialidad;
import com.mycompany.myapp.domain.enumeration.Criticidad;
import com.mycompany.myapp.domain.enumeration.Disponibilidad;
import com.mycompany.myapp.domain.enumeration.Integridad;
import com.mycompany.myapp.repository.GrupoActivosRepository;
import com.mycompany.myapp.service.dto.GrupoActivosDTO;
import com.mycompany.myapp.service.mapper.GrupoActivosMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link GrupoActivosResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GrupoActivosResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final Disponibilidad DEFAULT_DISPONIBILIDAD = Disponibilidad.HIGH;
    private static final Disponibilidad UPDATED_DISPONIBILIDAD = Disponibilidad.MEDIUM;

    private static final Integridad DEFAULT_INTEGRIDAD = Integridad.HIGH;
    private static final Integridad UPDATED_INTEGRIDAD = Integridad.MEDIUM;

    private static final Confidencialidad DEFAULT_CONFIDENCIALIDAD = Confidencialidad.HIGH;
    private static final Confidencialidad UPDATED_CONFIDENCIALIDAD = Confidencialidad.MEDIUM;

    private static final Criticidad DEFAULT_CRITICIDAD = Criticidad.HIGH;
    private static final Criticidad UPDATED_CRITICIDAD = Criticidad.MEDIUM;

    private static final String ENTITY_API_URL = "/api/grupo-activos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private GrupoActivosRepository grupoActivosRepository;

    @Autowired
    private GrupoActivosMapper grupoActivosMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGrupoActivosMockMvc;

    private GrupoActivos grupoActivos;

    private GrupoActivos insertedGrupoActivos;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GrupoActivos createEntity(EntityManager em) {
        GrupoActivos grupoActivos = new GrupoActivos()
            .nombre(DEFAULT_NOMBRE)
            .disponibilidad(DEFAULT_DISPONIBILIDAD)
            .integridad(DEFAULT_INTEGRIDAD)
            .confidencialidad(DEFAULT_CONFIDENCIALIDAD)
            .criticidad(DEFAULT_CRITICIDAD);
        return grupoActivos;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GrupoActivos createUpdatedEntity(EntityManager em) {
        GrupoActivos updatedGrupoActivos = new GrupoActivos()
            .nombre(UPDATED_NOMBRE)
            .disponibilidad(UPDATED_DISPONIBILIDAD)
            .integridad(UPDATED_INTEGRIDAD)
            .confidencialidad(UPDATED_CONFIDENCIALIDAD)
            .criticidad(UPDATED_CRITICIDAD);
        return updatedGrupoActivos;
    }

    @BeforeEach
    public void initTest() {
        grupoActivos = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedGrupoActivos != null) {
            grupoActivosRepository.delete(insertedGrupoActivos);
            insertedGrupoActivos = null;
        }
    }

    @Test
    @Transactional
    void createGrupoActivos() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the GrupoActivos
        GrupoActivosDTO grupoActivosDTO = grupoActivosMapper.toDto(grupoActivos);
        var returnedGrupoActivosDTO = om.readValue(
            restGrupoActivosMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(grupoActivosDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            GrupoActivosDTO.class
        );

        // Validate the GrupoActivos in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedGrupoActivos = grupoActivosMapper.toEntity(returnedGrupoActivosDTO);
        assertGrupoActivosUpdatableFieldsEquals(returnedGrupoActivos, getPersistedGrupoActivos(returnedGrupoActivos));

        insertedGrupoActivos = returnedGrupoActivos;
    }

    @Test
    @Transactional
    void createGrupoActivosWithExistingId() throws Exception {
        // Create the GrupoActivos with an existing ID
        grupoActivos.setId(1L);
        GrupoActivosDTO grupoActivosDTO = grupoActivosMapper.toDto(grupoActivos);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGrupoActivosMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(grupoActivosDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GrupoActivos in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNombreIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        grupoActivos.setNombre(null);

        // Create the GrupoActivos, which fails.
        GrupoActivosDTO grupoActivosDTO = grupoActivosMapper.toDto(grupoActivos);

        restGrupoActivosMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(grupoActivosDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDisponibilidadIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        grupoActivos.setDisponibilidad(null);

        // Create the GrupoActivos, which fails.
        GrupoActivosDTO grupoActivosDTO = grupoActivosMapper.toDto(grupoActivos);

        restGrupoActivosMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(grupoActivosDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIntegridadIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        grupoActivos.setIntegridad(null);

        // Create the GrupoActivos, which fails.
        GrupoActivosDTO grupoActivosDTO = grupoActivosMapper.toDto(grupoActivos);

        restGrupoActivosMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(grupoActivosDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkConfidencialidadIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        grupoActivos.setConfidencialidad(null);

        // Create the GrupoActivos, which fails.
        GrupoActivosDTO grupoActivosDTO = grupoActivosMapper.toDto(grupoActivos);

        restGrupoActivosMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(grupoActivosDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCriticidadIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        grupoActivos.setCriticidad(null);

        // Create the GrupoActivos, which fails.
        GrupoActivosDTO grupoActivosDTO = grupoActivosMapper.toDto(grupoActivos);

        restGrupoActivosMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(grupoActivosDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllGrupoActivos() throws Exception {
        // Initialize the database
        insertedGrupoActivos = grupoActivosRepository.saveAndFlush(grupoActivos);

        // Get all the grupoActivosList
        restGrupoActivosMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(grupoActivos.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].disponibilidad").value(hasItem(DEFAULT_DISPONIBILIDAD.toString())))
            .andExpect(jsonPath("$.[*].integridad").value(hasItem(DEFAULT_INTEGRIDAD.toString())))
            .andExpect(jsonPath("$.[*].confidencialidad").value(hasItem(DEFAULT_CONFIDENCIALIDAD.toString())))
            .andExpect(jsonPath("$.[*].criticidad").value(hasItem(DEFAULT_CRITICIDAD.toString())));
    }

    @Test
    @Transactional
    void getGrupoActivos() throws Exception {
        // Initialize the database
        insertedGrupoActivos = grupoActivosRepository.saveAndFlush(grupoActivos);

        // Get the grupoActivos
        restGrupoActivosMockMvc
            .perform(get(ENTITY_API_URL_ID, grupoActivos.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(grupoActivos.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.disponibilidad").value(DEFAULT_DISPONIBILIDAD.toString()))
            .andExpect(jsonPath("$.integridad").value(DEFAULT_INTEGRIDAD.toString()))
            .andExpect(jsonPath("$.confidencialidad").value(DEFAULT_CONFIDENCIALIDAD.toString()))
            .andExpect(jsonPath("$.criticidad").value(DEFAULT_CRITICIDAD.toString()));
    }

    @Test
    @Transactional
    void getNonExistingGrupoActivos() throws Exception {
        // Get the grupoActivos
        restGrupoActivosMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingGrupoActivos() throws Exception {
        // Initialize the database
        insertedGrupoActivos = grupoActivosRepository.saveAndFlush(grupoActivos);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the grupoActivos
        GrupoActivos updatedGrupoActivos = grupoActivosRepository.findById(grupoActivos.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedGrupoActivos are not directly saved in db
        em.detach(updatedGrupoActivos);
        updatedGrupoActivos
            .nombre(UPDATED_NOMBRE)
            .disponibilidad(UPDATED_DISPONIBILIDAD)
            .integridad(UPDATED_INTEGRIDAD)
            .confidencialidad(UPDATED_CONFIDENCIALIDAD)
            .criticidad(UPDATED_CRITICIDAD);
        GrupoActivosDTO grupoActivosDTO = grupoActivosMapper.toDto(updatedGrupoActivos);

        restGrupoActivosMockMvc
            .perform(
                put(ENTITY_API_URL_ID, grupoActivosDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(grupoActivosDTO))
            )
            .andExpect(status().isOk());

        // Validate the GrupoActivos in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedGrupoActivosToMatchAllProperties(updatedGrupoActivos);
    }

    @Test
    @Transactional
    void putNonExistingGrupoActivos() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        grupoActivos.setId(longCount.incrementAndGet());

        // Create the GrupoActivos
        GrupoActivosDTO grupoActivosDTO = grupoActivosMapper.toDto(grupoActivos);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGrupoActivosMockMvc
            .perform(
                put(ENTITY_API_URL_ID, grupoActivosDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(grupoActivosDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GrupoActivos in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGrupoActivos() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        grupoActivos.setId(longCount.incrementAndGet());

        // Create the GrupoActivos
        GrupoActivosDTO grupoActivosDTO = grupoActivosMapper.toDto(grupoActivos);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGrupoActivosMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(grupoActivosDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GrupoActivos in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGrupoActivos() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        grupoActivos.setId(longCount.incrementAndGet());

        // Create the GrupoActivos
        GrupoActivosDTO grupoActivosDTO = grupoActivosMapper.toDto(grupoActivos);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGrupoActivosMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(grupoActivosDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the GrupoActivos in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGrupoActivosWithPatch() throws Exception {
        // Initialize the database
        insertedGrupoActivos = grupoActivosRepository.saveAndFlush(grupoActivos);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the grupoActivos using partial update
        GrupoActivos partialUpdatedGrupoActivos = new GrupoActivos();
        partialUpdatedGrupoActivos.setId(grupoActivos.getId());

        partialUpdatedGrupoActivos
            .nombre(UPDATED_NOMBRE)
            .disponibilidad(UPDATED_DISPONIBILIDAD)
            .integridad(UPDATED_INTEGRIDAD)
            .confidencialidad(UPDATED_CONFIDENCIALIDAD);

        restGrupoActivosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGrupoActivos.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedGrupoActivos))
            )
            .andExpect(status().isOk());

        // Validate the GrupoActivos in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertGrupoActivosUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedGrupoActivos, grupoActivos),
            getPersistedGrupoActivos(grupoActivos)
        );
    }

    @Test
    @Transactional
    void fullUpdateGrupoActivosWithPatch() throws Exception {
        // Initialize the database
        insertedGrupoActivos = grupoActivosRepository.saveAndFlush(grupoActivos);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the grupoActivos using partial update
        GrupoActivos partialUpdatedGrupoActivos = new GrupoActivos();
        partialUpdatedGrupoActivos.setId(grupoActivos.getId());

        partialUpdatedGrupoActivos
            .nombre(UPDATED_NOMBRE)
            .disponibilidad(UPDATED_DISPONIBILIDAD)
            .integridad(UPDATED_INTEGRIDAD)
            .confidencialidad(UPDATED_CONFIDENCIALIDAD)
            .criticidad(UPDATED_CRITICIDAD);

        restGrupoActivosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGrupoActivos.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedGrupoActivos))
            )
            .andExpect(status().isOk());

        // Validate the GrupoActivos in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertGrupoActivosUpdatableFieldsEquals(partialUpdatedGrupoActivos, getPersistedGrupoActivos(partialUpdatedGrupoActivos));
    }

    @Test
    @Transactional
    void patchNonExistingGrupoActivos() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        grupoActivos.setId(longCount.incrementAndGet());

        // Create the GrupoActivos
        GrupoActivosDTO grupoActivosDTO = grupoActivosMapper.toDto(grupoActivos);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGrupoActivosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, grupoActivosDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(grupoActivosDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GrupoActivos in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGrupoActivos() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        grupoActivos.setId(longCount.incrementAndGet());

        // Create the GrupoActivos
        GrupoActivosDTO grupoActivosDTO = grupoActivosMapper.toDto(grupoActivos);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGrupoActivosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(grupoActivosDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GrupoActivos in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGrupoActivos() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        grupoActivos.setId(longCount.incrementAndGet());

        // Create the GrupoActivos
        GrupoActivosDTO grupoActivosDTO = grupoActivosMapper.toDto(grupoActivos);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGrupoActivosMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(grupoActivosDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the GrupoActivos in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGrupoActivos() throws Exception {
        // Initialize the database
        insertedGrupoActivos = grupoActivosRepository.saveAndFlush(grupoActivos);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the grupoActivos
        restGrupoActivosMockMvc
            .perform(delete(ENTITY_API_URL_ID, grupoActivos.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return grupoActivosRepository.count();
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

    protected GrupoActivos getPersistedGrupoActivos(GrupoActivos grupoActivos) {
        return grupoActivosRepository.findById(grupoActivos.getId()).orElseThrow();
    }

    protected void assertPersistedGrupoActivosToMatchAllProperties(GrupoActivos expectedGrupoActivos) {
        assertGrupoActivosAllPropertiesEquals(expectedGrupoActivos, getPersistedGrupoActivos(expectedGrupoActivos));
    }

    protected void assertPersistedGrupoActivosToMatchUpdatableProperties(GrupoActivos expectedGrupoActivos) {
        assertGrupoActivosAllUpdatablePropertiesEquals(expectedGrupoActivos, getPersistedGrupoActivos(expectedGrupoActivos));
    }
}
