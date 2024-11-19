package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.ControlesAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Controles;
import com.mycompany.myapp.repository.ControlesRepository;
import com.mycompany.myapp.service.dto.ControlesDTO;
import com.mycompany.myapp.service.mapper.ControlesMapper;
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
 * Integration tests for the {@link ControlesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ControlesResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/controles";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ControlesRepository controlesRepository;

    @Autowired
    private ControlesMapper controlesMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restControlesMockMvc;

    private Controles controles;

    private Controles insertedControles;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Controles createEntity() {
        return new Controles().nombre(DEFAULT_NOMBRE).descripcion(DEFAULT_DESCRIPCION);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Controles createUpdatedEntity() {
        return new Controles().nombre(UPDATED_NOMBRE).descripcion(UPDATED_DESCRIPCION);
    }

    @BeforeEach
    public void initTest() {
        controles = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedControles != null) {
            controlesRepository.delete(insertedControles);
            insertedControles = null;
        }
    }

    @Test
    @Transactional
    void createControles() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Controles
        ControlesDTO controlesDTO = controlesMapper.toDto(controles);
        var returnedControlesDTO = om.readValue(
            restControlesMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(controlesDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ControlesDTO.class
        );

        // Validate the Controles in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedControles = controlesMapper.toEntity(returnedControlesDTO);
        assertControlesUpdatableFieldsEquals(returnedControles, getPersistedControles(returnedControles));

        insertedControles = returnedControles;
    }

    @Test
    @Transactional
    void createControlesWithExistingId() throws Exception {
        // Create the Controles with an existing ID
        controles.setId(1L);
        ControlesDTO controlesDTO = controlesMapper.toDto(controles);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restControlesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(controlesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Controles in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNombreIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        controles.setNombre(null);

        // Create the Controles, which fails.
        ControlesDTO controlesDTO = controlesMapper.toDto(controles);

        restControlesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(controlesDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDescripcionIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        controles.setDescripcion(null);

        // Create the Controles, which fails.
        ControlesDTO controlesDTO = controlesMapper.toDto(controles);

        restControlesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(controlesDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllControles() throws Exception {
        // Initialize the database
        insertedControles = controlesRepository.saveAndFlush(controles);

        // Get all the controlesList
        restControlesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(controles.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)));
    }

    @Test
    @Transactional
    void getControles() throws Exception {
        // Initialize the database
        insertedControles = controlesRepository.saveAndFlush(controles);

        // Get the controles
        restControlesMockMvc
            .perform(get(ENTITY_API_URL_ID, controles.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(controles.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION));
    }

    @Test
    @Transactional
    void getNonExistingControles() throws Exception {
        // Get the controles
        restControlesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingControles() throws Exception {
        // Initialize the database
        insertedControles = controlesRepository.saveAndFlush(controles);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the controles
        Controles updatedControles = controlesRepository.findById(controles.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedControles are not directly saved in db
        em.detach(updatedControles);
        updatedControles.nombre(UPDATED_NOMBRE).descripcion(UPDATED_DESCRIPCION);
        ControlesDTO controlesDTO = controlesMapper.toDto(updatedControles);

        restControlesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, controlesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(controlesDTO))
            )
            .andExpect(status().isOk());

        // Validate the Controles in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedControlesToMatchAllProperties(updatedControles);
    }

    @Test
    @Transactional
    void putNonExistingControles() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        controles.setId(longCount.incrementAndGet());

        // Create the Controles
        ControlesDTO controlesDTO = controlesMapper.toDto(controles);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restControlesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, controlesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(controlesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Controles in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchControles() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        controles.setId(longCount.incrementAndGet());

        // Create the Controles
        ControlesDTO controlesDTO = controlesMapper.toDto(controles);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restControlesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(controlesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Controles in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamControles() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        controles.setId(longCount.incrementAndGet());

        // Create the Controles
        ControlesDTO controlesDTO = controlesMapper.toDto(controles);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restControlesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(controlesDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Controles in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateControlesWithPatch() throws Exception {
        // Initialize the database
        insertedControles = controlesRepository.saveAndFlush(controles);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the controles using partial update
        Controles partialUpdatedControles = new Controles();
        partialUpdatedControles.setId(controles.getId());

        partialUpdatedControles.descripcion(UPDATED_DESCRIPCION);

        restControlesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedControles.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedControles))
            )
            .andExpect(status().isOk());

        // Validate the Controles in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertControlesUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedControles, controles),
            getPersistedControles(controles)
        );
    }

    @Test
    @Transactional
    void fullUpdateControlesWithPatch() throws Exception {
        // Initialize the database
        insertedControles = controlesRepository.saveAndFlush(controles);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the controles using partial update
        Controles partialUpdatedControles = new Controles();
        partialUpdatedControles.setId(controles.getId());

        partialUpdatedControles.nombre(UPDATED_NOMBRE).descripcion(UPDATED_DESCRIPCION);

        restControlesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedControles.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedControles))
            )
            .andExpect(status().isOk());

        // Validate the Controles in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertControlesUpdatableFieldsEquals(partialUpdatedControles, getPersistedControles(partialUpdatedControles));
    }

    @Test
    @Transactional
    void patchNonExistingControles() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        controles.setId(longCount.incrementAndGet());

        // Create the Controles
        ControlesDTO controlesDTO = controlesMapper.toDto(controles);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restControlesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, controlesDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(controlesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Controles in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchControles() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        controles.setId(longCount.incrementAndGet());

        // Create the Controles
        ControlesDTO controlesDTO = controlesMapper.toDto(controles);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restControlesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(controlesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Controles in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamControles() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        controles.setId(longCount.incrementAndGet());

        // Create the Controles
        ControlesDTO controlesDTO = controlesMapper.toDto(controles);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restControlesMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(controlesDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Controles in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteControles() throws Exception {
        // Initialize the database
        insertedControles = controlesRepository.saveAndFlush(controles);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the controles
        restControlesMockMvc
            .perform(delete(ENTITY_API_URL_ID, controles.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return controlesRepository.count();
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

    protected Controles getPersistedControles(Controles controles) {
        return controlesRepository.findById(controles.getId()).orElseThrow();
    }

    protected void assertPersistedControlesToMatchAllProperties(Controles expectedControles) {
        assertControlesAllPropertiesEquals(expectedControles, getPersistedControles(expectedControles));
    }

    protected void assertPersistedControlesToMatchUpdatableProperties(Controles expectedControles) {
        assertControlesAllUpdatablePropertiesEquals(expectedControles, getPersistedControles(expectedControles));
    }
}
