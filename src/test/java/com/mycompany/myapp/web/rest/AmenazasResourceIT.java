package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.AmenazasAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Amenazas;
import com.mycompany.myapp.repository.AmenazasRepository;
import com.mycompany.myapp.service.dto.AmenazasDTO;
import com.mycompany.myapp.service.mapper.AmenazasMapper;
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
 * Integration tests for the {@link AmenazasResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AmenazasResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/amenazas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AmenazasRepository amenazasRepository;

    @Autowired
    private AmenazasMapper amenazasMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAmenazasMockMvc;

    private Amenazas amenazas;

    private Amenazas insertedAmenazas;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Amenazas createEntity() {
        return new Amenazas().nombre(DEFAULT_NOMBRE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Amenazas createUpdatedEntity() {
        return new Amenazas().nombre(UPDATED_NOMBRE);
    }

    @BeforeEach
    public void initTest() {
        amenazas = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedAmenazas != null) {
            amenazasRepository.delete(insertedAmenazas);
            insertedAmenazas = null;
        }
    }

    @Test
    @Transactional
    void createAmenazas() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Amenazas
        AmenazasDTO amenazasDTO = amenazasMapper.toDto(amenazas);
        var returnedAmenazasDTO = om.readValue(
            restAmenazasMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(amenazasDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            AmenazasDTO.class
        );

        // Validate the Amenazas in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedAmenazas = amenazasMapper.toEntity(returnedAmenazasDTO);
        assertAmenazasUpdatableFieldsEquals(returnedAmenazas, getPersistedAmenazas(returnedAmenazas));

        insertedAmenazas = returnedAmenazas;
    }

    @Test
    @Transactional
    void createAmenazasWithExistingId() throws Exception {
        // Create the Amenazas with an existing ID
        amenazas.setId(1L);
        AmenazasDTO amenazasDTO = amenazasMapper.toDto(amenazas);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAmenazasMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(amenazasDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Amenazas in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNombreIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        amenazas.setNombre(null);

        // Create the Amenazas, which fails.
        AmenazasDTO amenazasDTO = amenazasMapper.toDto(amenazas);

        restAmenazasMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(amenazasDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAmenazas() throws Exception {
        // Initialize the database
        insertedAmenazas = amenazasRepository.saveAndFlush(amenazas);

        // Get all the amenazasList
        restAmenazasMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(amenazas.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)));
    }

    @Test
    @Transactional
    void getAmenazas() throws Exception {
        // Initialize the database
        insertedAmenazas = amenazasRepository.saveAndFlush(amenazas);

        // Get the amenazas
        restAmenazasMockMvc
            .perform(get(ENTITY_API_URL_ID, amenazas.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(amenazas.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE));
    }

    @Test
    @Transactional
    void getNonExistingAmenazas() throws Exception {
        // Get the amenazas
        restAmenazasMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAmenazas() throws Exception {
        // Initialize the database
        insertedAmenazas = amenazasRepository.saveAndFlush(amenazas);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the amenazas
        Amenazas updatedAmenazas = amenazasRepository.findById(amenazas.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAmenazas are not directly saved in db
        em.detach(updatedAmenazas);
        updatedAmenazas.nombre(UPDATED_NOMBRE);
        AmenazasDTO amenazasDTO = amenazasMapper.toDto(updatedAmenazas);

        restAmenazasMockMvc
            .perform(
                put(ENTITY_API_URL_ID, amenazasDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(amenazasDTO))
            )
            .andExpect(status().isOk());

        // Validate the Amenazas in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAmenazasToMatchAllProperties(updatedAmenazas);
    }

    @Test
    @Transactional
    void putNonExistingAmenazas() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        amenazas.setId(longCount.incrementAndGet());

        // Create the Amenazas
        AmenazasDTO amenazasDTO = amenazasMapper.toDto(amenazas);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAmenazasMockMvc
            .perform(
                put(ENTITY_API_URL_ID, amenazasDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(amenazasDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Amenazas in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAmenazas() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        amenazas.setId(longCount.incrementAndGet());

        // Create the Amenazas
        AmenazasDTO amenazasDTO = amenazasMapper.toDto(amenazas);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAmenazasMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(amenazasDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Amenazas in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAmenazas() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        amenazas.setId(longCount.incrementAndGet());

        // Create the Amenazas
        AmenazasDTO amenazasDTO = amenazasMapper.toDto(amenazas);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAmenazasMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(amenazasDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Amenazas in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAmenazasWithPatch() throws Exception {
        // Initialize the database
        insertedAmenazas = amenazasRepository.saveAndFlush(amenazas);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the amenazas using partial update
        Amenazas partialUpdatedAmenazas = new Amenazas();
        partialUpdatedAmenazas.setId(amenazas.getId());

        restAmenazasMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAmenazas.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAmenazas))
            )
            .andExpect(status().isOk());

        // Validate the Amenazas in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAmenazasUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedAmenazas, amenazas), getPersistedAmenazas(amenazas));
    }

    @Test
    @Transactional
    void fullUpdateAmenazasWithPatch() throws Exception {
        // Initialize the database
        insertedAmenazas = amenazasRepository.saveAndFlush(amenazas);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the amenazas using partial update
        Amenazas partialUpdatedAmenazas = new Amenazas();
        partialUpdatedAmenazas.setId(amenazas.getId());

        partialUpdatedAmenazas.nombre(UPDATED_NOMBRE);

        restAmenazasMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAmenazas.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAmenazas))
            )
            .andExpect(status().isOk());

        // Validate the Amenazas in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAmenazasUpdatableFieldsEquals(partialUpdatedAmenazas, getPersistedAmenazas(partialUpdatedAmenazas));
    }

    @Test
    @Transactional
    void patchNonExistingAmenazas() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        amenazas.setId(longCount.incrementAndGet());

        // Create the Amenazas
        AmenazasDTO amenazasDTO = amenazasMapper.toDto(amenazas);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAmenazasMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, amenazasDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(amenazasDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Amenazas in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAmenazas() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        amenazas.setId(longCount.incrementAndGet());

        // Create the Amenazas
        AmenazasDTO amenazasDTO = amenazasMapper.toDto(amenazas);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAmenazasMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(amenazasDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Amenazas in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAmenazas() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        amenazas.setId(longCount.incrementAndGet());

        // Create the Amenazas
        AmenazasDTO amenazasDTO = amenazasMapper.toDto(amenazas);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAmenazasMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(amenazasDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Amenazas in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAmenazas() throws Exception {
        // Initialize the database
        insertedAmenazas = amenazasRepository.saveAndFlush(amenazas);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the amenazas
        restAmenazasMockMvc
            .perform(delete(ENTITY_API_URL_ID, amenazas.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return amenazasRepository.count();
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

    protected Amenazas getPersistedAmenazas(Amenazas amenazas) {
        return amenazasRepository.findById(amenazas.getId()).orElseThrow();
    }

    protected void assertPersistedAmenazasToMatchAllProperties(Amenazas expectedAmenazas) {
        assertAmenazasAllPropertiesEquals(expectedAmenazas, getPersistedAmenazas(expectedAmenazas));
    }

    protected void assertPersistedAmenazasToMatchUpdatableProperties(Amenazas expectedAmenazas) {
        assertAmenazasAllUpdatablePropertiesEquals(expectedAmenazas, getPersistedAmenazas(expectedAmenazas));
    }
}
