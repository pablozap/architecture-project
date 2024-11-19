package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.AmenazasRepository;
import com.mycompany.myapp.service.AmenazasService;
import com.mycompany.myapp.service.dto.AmenazasDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Amenazas}.
 */
@RestController
@RequestMapping("/api/amenazas")
public class AmenazasResource {

    private static final Logger LOG = LoggerFactory.getLogger(AmenazasResource.class);

    private static final String ENTITY_NAME = "amenazas";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AmenazasService amenazasService;

    private final AmenazasRepository amenazasRepository;

    public AmenazasResource(AmenazasService amenazasService, AmenazasRepository amenazasRepository) {
        this.amenazasService = amenazasService;
        this.amenazasRepository = amenazasRepository;
    }

    /**
     * {@code POST  /amenazas} : Create a new amenazas.
     *
     * @param amenazasDTO the amenazasDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new amenazasDTO, or with status {@code 400 (Bad Request)} if the amenazas has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AmenazasDTO> createAmenazas(@Valid @RequestBody AmenazasDTO amenazasDTO) throws URISyntaxException {
        LOG.debug("REST request to save Amenazas : {}", amenazasDTO);
        if (amenazasDTO.getId() != null) {
            throw new BadRequestAlertException("A new amenazas cannot already have an ID", ENTITY_NAME, "idexists");
        }
        amenazasDTO = amenazasService.save(amenazasDTO);
        return ResponseEntity.created(new URI("/api/amenazas/" + amenazasDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, amenazasDTO.getId().toString()))
            .body(amenazasDTO);
    }

    /**
     * {@code PUT  /amenazas/:id} : Updates an existing amenazas.
     *
     * @param id the id of the amenazasDTO to save.
     * @param amenazasDTO the amenazasDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated amenazasDTO,
     * or with status {@code 400 (Bad Request)} if the amenazasDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the amenazasDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AmenazasDTO> updateAmenazas(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AmenazasDTO amenazasDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Amenazas : {}, {}", id, amenazasDTO);
        if (amenazasDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, amenazasDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!amenazasRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        amenazasDTO = amenazasService.update(amenazasDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, amenazasDTO.getId().toString()))
            .body(amenazasDTO);
    }

    /**
     * {@code PATCH  /amenazas/:id} : Partial updates given fields of an existing amenazas, field will ignore if it is null
     *
     * @param id the id of the amenazasDTO to save.
     * @param amenazasDTO the amenazasDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated amenazasDTO,
     * or with status {@code 400 (Bad Request)} if the amenazasDTO is not valid,
     * or with status {@code 404 (Not Found)} if the amenazasDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the amenazasDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AmenazasDTO> partialUpdateAmenazas(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AmenazasDTO amenazasDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Amenazas partially : {}, {}", id, amenazasDTO);
        if (amenazasDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, amenazasDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!amenazasRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AmenazasDTO> result = amenazasService.partialUpdate(amenazasDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, amenazasDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /amenazas} : get all the amenazas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of amenazas in body.
     */
    @GetMapping("")
    public List<AmenazasDTO> getAllAmenazas() {
        LOG.debug("REST request to get all Amenazas");
        return amenazasService.findAll();
    }

    /**
     * {@code GET  /amenazas/:id} : get the "id" amenazas.
     *
     * @param id the id of the amenazasDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the amenazasDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AmenazasDTO> getAmenazas(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Amenazas : {}", id);
        Optional<AmenazasDTO> amenazasDTO = amenazasService.findOne(id);
        return ResponseUtil.wrapOrNotFound(amenazasDTO);
    }

    /**
     * {@code DELETE  /amenazas/:id} : delete the "id" amenazas.
     *
     * @param id the id of the amenazasDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAmenazas(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Amenazas : {}", id);
        amenazasService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
