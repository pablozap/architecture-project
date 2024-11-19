package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.ActivoInformacionRepository;
import com.mycompany.myapp.service.ActivoInformacionService;
import com.mycompany.myapp.service.dto.ActivoInformacionDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.ActivoInformacion}.
 */
@RestController
@RequestMapping("/api/activo-informacions")
public class ActivoInformacionResource {

    private static final Logger LOG = LoggerFactory.getLogger(ActivoInformacionResource.class);

    private static final String ENTITY_NAME = "activoInformacion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ActivoInformacionService activoInformacionService;

    private final ActivoInformacionRepository activoInformacionRepository;

    public ActivoInformacionResource(
        ActivoInformacionService activoInformacionService,
        ActivoInformacionRepository activoInformacionRepository
    ) {
        this.activoInformacionService = activoInformacionService;
        this.activoInformacionRepository = activoInformacionRepository;
    }

    /**
     * {@code POST  /activo-informacions} : Create a new activoInformacion.
     *
     * @param activoInformacionDTO the activoInformacionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new activoInformacionDTO, or with status {@code 400 (Bad Request)} if the activoInformacion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ActivoInformacionDTO> createActivoInformacion(@Valid @RequestBody ActivoInformacionDTO activoInformacionDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save ActivoInformacion : {}", activoInformacionDTO);
        if (activoInformacionDTO.getId() != null) {
            throw new BadRequestAlertException("A new activoInformacion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        activoInformacionDTO = activoInformacionService.save(activoInformacionDTO);
        return ResponseEntity.created(new URI("/api/activo-informacions/" + activoInformacionDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, activoInformacionDTO.getId().toString()))
            .body(activoInformacionDTO);
    }

    /**
     * {@code PUT  /activo-informacions/:id} : Updates an existing activoInformacion.
     *
     * @param id the id of the activoInformacionDTO to save.
     * @param activoInformacionDTO the activoInformacionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated activoInformacionDTO,
     * or with status {@code 400 (Bad Request)} if the activoInformacionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the activoInformacionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ActivoInformacionDTO> updateActivoInformacion(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ActivoInformacionDTO activoInformacionDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update ActivoInformacion : {}, {}", id, activoInformacionDTO);
        if (activoInformacionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, activoInformacionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!activoInformacionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        activoInformacionDTO = activoInformacionService.update(activoInformacionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, activoInformacionDTO.getId().toString()))
            .body(activoInformacionDTO);
    }

    /**
     * {@code PATCH  /activo-informacions/:id} : Partial updates given fields of an existing activoInformacion, field will ignore if it is null
     *
     * @param id the id of the activoInformacionDTO to save.
     * @param activoInformacionDTO the activoInformacionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated activoInformacionDTO,
     * or with status {@code 400 (Bad Request)} if the activoInformacionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the activoInformacionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the activoInformacionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ActivoInformacionDTO> partialUpdateActivoInformacion(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ActivoInformacionDTO activoInformacionDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update ActivoInformacion partially : {}, {}", id, activoInformacionDTO);
        if (activoInformacionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, activoInformacionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!activoInformacionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ActivoInformacionDTO> result = activoInformacionService.partialUpdate(activoInformacionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, activoInformacionDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /activo-informacions} : get all the activoInformacions.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of activoInformacions in body.
     */
    @GetMapping("")
    public List<ActivoInformacionDTO> getAllActivoInformacions(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        LOG.debug("REST request to get all ActivoInformacions");
        return activoInformacionService.findAll();
    }

    /**
     * {@code GET  /activo-informacions/:id} : get the "id" activoInformacion.
     *
     * @param id the id of the activoInformacionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the activoInformacionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ActivoInformacionDTO> getActivoInformacion(@PathVariable("id") Long id) {
        LOG.debug("REST request to get ActivoInformacion : {}", id);
        Optional<ActivoInformacionDTO> activoInformacionDTO = activoInformacionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(activoInformacionDTO);
    }

    /**
     * {@code DELETE  /activo-informacions/:id} : delete the "id" activoInformacion.
     *
     * @param id the id of the activoInformacionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActivoInformacion(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete ActivoInformacion : {}", id);
        activoInformacionService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
