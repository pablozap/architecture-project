package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.GrupoActivosRepository;
import com.mycompany.myapp.service.GrupoActivosService;
import com.mycompany.myapp.service.dto.GrupoActivosDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.GrupoActivos}.
 */
@RestController
@RequestMapping("/api/grupo-activos")
public class GrupoActivosResource {

    private static final Logger LOG = LoggerFactory.getLogger(GrupoActivosResource.class);

    private static final String ENTITY_NAME = "grupoActivos";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GrupoActivosService grupoActivosService;

    private final GrupoActivosRepository grupoActivosRepository;

    public GrupoActivosResource(GrupoActivosService grupoActivosService, GrupoActivosRepository grupoActivosRepository) {
        this.grupoActivosService = grupoActivosService;
        this.grupoActivosRepository = grupoActivosRepository;
    }

    /**
     * {@code POST  /grupo-activos} : Create a new grupoActivos.
     *
     * @param grupoActivosDTO the grupoActivosDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new grupoActivosDTO, or with status {@code 400 (Bad Request)} if the grupoActivos has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<GrupoActivosDTO> createGrupoActivos(@Valid @RequestBody GrupoActivosDTO grupoActivosDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save GrupoActivos : {}", grupoActivosDTO);
        if (grupoActivosDTO.getId() != null) {
            throw new BadRequestAlertException("A new grupoActivos cannot already have an ID", ENTITY_NAME, "idexists");
        }
        grupoActivosDTO = grupoActivosService.save(grupoActivosDTO);
        return ResponseEntity.created(new URI("/api/grupo-activos/" + grupoActivosDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, grupoActivosDTO.getId().toString()))
            .body(grupoActivosDTO);
    }

    /**
     * {@code PUT  /grupo-activos/:id} : Updates an existing grupoActivos.
     *
     * @param id the id of the grupoActivosDTO to save.
     * @param grupoActivosDTO the grupoActivosDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated grupoActivosDTO,
     * or with status {@code 400 (Bad Request)} if the grupoActivosDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the grupoActivosDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<GrupoActivosDTO> updateGrupoActivos(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody GrupoActivosDTO grupoActivosDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update GrupoActivos : {}, {}", id, grupoActivosDTO);
        if (grupoActivosDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, grupoActivosDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!grupoActivosRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        grupoActivosDTO = grupoActivosService.update(grupoActivosDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, grupoActivosDTO.getId().toString()))
            .body(grupoActivosDTO);
    }

    /**
     * {@code PATCH  /grupo-activos/:id} : Partial updates given fields of an existing grupoActivos, field will ignore if it is null
     *
     * @param id the id of the grupoActivosDTO to save.
     * @param grupoActivosDTO the grupoActivosDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated grupoActivosDTO,
     * or with status {@code 400 (Bad Request)} if the grupoActivosDTO is not valid,
     * or with status {@code 404 (Not Found)} if the grupoActivosDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the grupoActivosDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<GrupoActivosDTO> partialUpdateGrupoActivos(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody GrupoActivosDTO grupoActivosDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update GrupoActivos partially : {}, {}", id, grupoActivosDTO);
        if (grupoActivosDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, grupoActivosDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!grupoActivosRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<GrupoActivosDTO> result = grupoActivosService.partialUpdate(grupoActivosDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, grupoActivosDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /grupo-activos} : get all the grupoActivos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of grupoActivos in body.
     */
    @GetMapping("")
    public List<GrupoActivosDTO> getAllGrupoActivos() {
        LOG.debug("REST request to get all GrupoActivos");
        return grupoActivosService.findAll();
    }

    /**
     * {@code GET  /grupo-activos/:id} : get the "id" grupoActivos.
     *
     * @param id the id of the grupoActivosDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the grupoActivosDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<GrupoActivosDTO> getGrupoActivos(@PathVariable("id") Long id) {
        LOG.debug("REST request to get GrupoActivos : {}", id);
        Optional<GrupoActivosDTO> grupoActivosDTO = grupoActivosService.findOne(id);
        return ResponseUtil.wrapOrNotFound(grupoActivosDTO);
    }

    /**
     * {@code DELETE  /grupo-activos/:id} : delete the "id" grupoActivos.
     *
     * @param id the id of the grupoActivosDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGrupoActivos(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete GrupoActivos : {}", id);
        grupoActivosService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
