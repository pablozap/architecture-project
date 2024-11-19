package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.VulnerabilidadesAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Vulnerabilidades;
import com.mycompany.myapp.repository.VulnerabilidadesRepository;
import com.mycompany.myapp.service.dto.VulnerabilidadesDTO;
import com.mycompany.myapp.service.mapper.VulnerabilidadesMapper;
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
 * Integration tests for the {@link VulnerabilidadesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VulnerabilidadesResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/vulnerabilidades";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private VulnerabilidadesRepository vulnerabilidadesRepository;

    @Autowired
    private VulnerabilidadesMapper vulnerabilidadesMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVulnerabilidadesMockMvc;

    private Vulnerabilidades vulnerabilidades;

    private Vulnerabilidades insertedVulnerabilidades;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vulnerabilidades createEntity() {
        return new Vulnerabilidades().nombre(DEFAULT_NOMBRE).descripcion(DEFAULT_DESCRIPCION);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vulnerabilidades createUpdatedEntity() {
        return new Vulnerabilidades().nombre(UPDATED_NOMBRE).descripcion(UPDATED_DESCRIPCION);
    }

    @BeforeEach
    public void initTest() {
        vulnerabilidades = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedVulnerabilidades != null) {
            vulnerabilidadesRepository.delete(insertedVulnerabilidades);
            insertedVulnerabilidades = null;
        }
    }

    @Test
    @Transactional
    void createVulnerabilidades() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Vulnerabilidades
        VulnerabilidadesDTO vulnerabilidadesDTO = vulnerabilidadesMapper.toDto(vulnerabilidades);
        var returnedVulnerabilidadesDTO = om.readValue(
            restVulnerabilidadesMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vulnerabilidadesDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            VulnerabilidadesDTO.class
        );

        // Validate the Vulnerabilidades in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedVulnerabilidades = vulnerabilidadesMapper.toEntity(returnedVulnerabilidadesDTO);
        assertVulnerabilidadesUpdatableFieldsEquals(returnedVulnerabilidades, getPersistedVulnerabilidades(returnedVulnerabilidades));

        insertedVulnerabilidades = returnedVulnerabilidades;
    }

    @Test
    @Transactional
    void createVulnerabilidadesWithExistingId() throws Exception {
        // Create the Vulnerabilidades with an existing ID
        vulnerabilidades.setId(1L);
        VulnerabilidadesDTO vulnerabilidadesDTO = vulnerabilidadesMapper.toDto(vulnerabilidades);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVulnerabilidadesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vulnerabilidadesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Vulnerabilidades in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNombreIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        vulnerabilidades.setNombre(null);

        // Create the Vulnerabilidades, which fails.
        VulnerabilidadesDTO vulnerabilidadesDTO = vulnerabilidadesMapper.toDto(vulnerabilidades);

        restVulnerabilidadesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vulnerabilidadesDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDescripcionIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        vulnerabilidades.setDescripcion(null);

        // Create the Vulnerabilidades, which fails.
        VulnerabilidadesDTO vulnerabilidadesDTO = vulnerabilidadesMapper.toDto(vulnerabilidades);

        restVulnerabilidadesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vulnerabilidadesDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllVulnerabilidades() throws Exception {
        // Initialize the database
        insertedVulnerabilidades = vulnerabilidadesRepository.saveAndFlush(vulnerabilidades);

        // Get all the vulnerabilidadesList
        restVulnerabilidadesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vulnerabilidades.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)));
    }

    @Test
    @Transactional
    void getVulnerabilidades() throws Exception {
        // Initialize the database
        insertedVulnerabilidades = vulnerabilidadesRepository.saveAndFlush(vulnerabilidades);

        // Get the vulnerabilidades
        restVulnerabilidadesMockMvc
            .perform(get(ENTITY_API_URL_ID, vulnerabilidades.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(vulnerabilidades.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION));
    }

    @Test
    @Transactional
    void getNonExistingVulnerabilidades() throws Exception {
        // Get the vulnerabilidades
        restVulnerabilidadesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingVulnerabilidades() throws Exception {
        // Initialize the database
        insertedVulnerabilidades = vulnerabilidadesRepository.saveAndFlush(vulnerabilidades);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the vulnerabilidades
        Vulnerabilidades updatedVulnerabilidades = vulnerabilidadesRepository.findById(vulnerabilidades.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedVulnerabilidades are not directly saved in db
        em.detach(updatedVulnerabilidades);
        updatedVulnerabilidades.nombre(UPDATED_NOMBRE).descripcion(UPDATED_DESCRIPCION);
        VulnerabilidadesDTO vulnerabilidadesDTO = vulnerabilidadesMapper.toDto(updatedVulnerabilidades);

        restVulnerabilidadesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, vulnerabilidadesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(vulnerabilidadesDTO))
            )
            .andExpect(status().isOk());

        // Validate the Vulnerabilidades in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedVulnerabilidadesToMatchAllProperties(updatedVulnerabilidades);
    }

    @Test
    @Transactional
    void putNonExistingVulnerabilidades() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vulnerabilidades.setId(longCount.incrementAndGet());

        // Create the Vulnerabilidades
        VulnerabilidadesDTO vulnerabilidadesDTO = vulnerabilidadesMapper.toDto(vulnerabilidades);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVulnerabilidadesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, vulnerabilidadesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(vulnerabilidadesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vulnerabilidades in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVulnerabilidades() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vulnerabilidades.setId(longCount.incrementAndGet());

        // Create the Vulnerabilidades
        VulnerabilidadesDTO vulnerabilidadesDTO = vulnerabilidadesMapper.toDto(vulnerabilidades);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVulnerabilidadesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(vulnerabilidadesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vulnerabilidades in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVulnerabilidades() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vulnerabilidades.setId(longCount.incrementAndGet());

        // Create the Vulnerabilidades
        VulnerabilidadesDTO vulnerabilidadesDTO = vulnerabilidadesMapper.toDto(vulnerabilidades);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVulnerabilidadesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vulnerabilidadesDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Vulnerabilidades in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVulnerabilidadesWithPatch() throws Exception {
        // Initialize the database
        insertedVulnerabilidades = vulnerabilidadesRepository.saveAndFlush(vulnerabilidades);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the vulnerabilidades using partial update
        Vulnerabilidades partialUpdatedVulnerabilidades = new Vulnerabilidades();
        partialUpdatedVulnerabilidades.setId(vulnerabilidades.getId());

        partialUpdatedVulnerabilidades.nombre(UPDATED_NOMBRE).descripcion(UPDATED_DESCRIPCION);

        restVulnerabilidadesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVulnerabilidades.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVulnerabilidades))
            )
            .andExpect(status().isOk());

        // Validate the Vulnerabilidades in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVulnerabilidadesUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedVulnerabilidades, vulnerabilidades),
            getPersistedVulnerabilidades(vulnerabilidades)
        );
    }

    @Test
    @Transactional
    void fullUpdateVulnerabilidadesWithPatch() throws Exception {
        // Initialize the database
        insertedVulnerabilidades = vulnerabilidadesRepository.saveAndFlush(vulnerabilidades);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the vulnerabilidades using partial update
        Vulnerabilidades partialUpdatedVulnerabilidades = new Vulnerabilidades();
        partialUpdatedVulnerabilidades.setId(vulnerabilidades.getId());

        partialUpdatedVulnerabilidades.nombre(UPDATED_NOMBRE).descripcion(UPDATED_DESCRIPCION);

        restVulnerabilidadesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVulnerabilidades.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVulnerabilidades))
            )
            .andExpect(status().isOk());

        // Validate the Vulnerabilidades in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVulnerabilidadesUpdatableFieldsEquals(
            partialUpdatedVulnerabilidades,
            getPersistedVulnerabilidades(partialUpdatedVulnerabilidades)
        );
    }

    @Test
    @Transactional
    void patchNonExistingVulnerabilidades() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vulnerabilidades.setId(longCount.incrementAndGet());

        // Create the Vulnerabilidades
        VulnerabilidadesDTO vulnerabilidadesDTO = vulnerabilidadesMapper.toDto(vulnerabilidades);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVulnerabilidadesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, vulnerabilidadesDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(vulnerabilidadesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vulnerabilidades in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVulnerabilidades() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vulnerabilidades.setId(longCount.incrementAndGet());

        // Create the Vulnerabilidades
        VulnerabilidadesDTO vulnerabilidadesDTO = vulnerabilidadesMapper.toDto(vulnerabilidades);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVulnerabilidadesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(vulnerabilidadesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vulnerabilidades in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVulnerabilidades() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vulnerabilidades.setId(longCount.incrementAndGet());

        // Create the Vulnerabilidades
        VulnerabilidadesDTO vulnerabilidadesDTO = vulnerabilidadesMapper.toDto(vulnerabilidades);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVulnerabilidadesMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(vulnerabilidadesDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Vulnerabilidades in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVulnerabilidades() throws Exception {
        // Initialize the database
        insertedVulnerabilidades = vulnerabilidadesRepository.saveAndFlush(vulnerabilidades);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the vulnerabilidades
        restVulnerabilidadesMockMvc
            .perform(delete(ENTITY_API_URL_ID, vulnerabilidades.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return vulnerabilidadesRepository.count();
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

    protected Vulnerabilidades getPersistedVulnerabilidades(Vulnerabilidades vulnerabilidades) {
        return vulnerabilidadesRepository.findById(vulnerabilidades.getId()).orElseThrow();
    }

    protected void assertPersistedVulnerabilidadesToMatchAllProperties(Vulnerabilidades expectedVulnerabilidades) {
        assertVulnerabilidadesAllPropertiesEquals(expectedVulnerabilidades, getPersistedVulnerabilidades(expectedVulnerabilidades));
    }

    protected void assertPersistedVulnerabilidadesToMatchUpdatableProperties(Vulnerabilidades expectedVulnerabilidades) {
        assertVulnerabilidadesAllUpdatablePropertiesEquals(
            expectedVulnerabilidades,
            getPersistedVulnerabilidades(expectedVulnerabilidades)
        );
    }
}
