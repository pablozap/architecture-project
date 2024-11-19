package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.RiesgoRepository;
import com.mycompany.myapp.service.RiesgoService;
import com.mycompany.myapp.service.dto.RiesgoDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Riesgo}.
 */
@RestController
@RequestMapping("/api/riesgos")
public class RiesgoResource {

    private static final Logger LOG = LoggerFactory.getLogger(RiesgoResource.class);

    private static final String ENTITY_NAME = "riesgo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RiesgoService riesgoService;

    private final RiesgoRepository riesgoRepository;

    public RiesgoResource(RiesgoService riesgoService, RiesgoRepository riesgoRepository) {
        this.riesgoService = riesgoService;
        this.riesgoRepository = riesgoRepository;
    }

    /**
     * {@code POST  /riesgos} : Create a new riesgo.
     *
     * @param riesgoDTO the riesgoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new riesgoDTO, or with status {@code 400 (Bad Request)} if the riesgo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<RiesgoDTO> createRiesgo(@Valid @RequestBody RiesgoDTO riesgoDTO) throws URISyntaxException {
        LOG.debug("REST request to save Riesgo : {}", riesgoDTO);
        if (riesgoDTO.getId() != null) {
            throw new BadRequestAlertException("A new riesgo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        riesgoDTO = riesgoService.save(riesgoDTO);
        return ResponseEntity.created(new URI("/api/riesgos/" + riesgoDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, riesgoDTO.getId().toString()))
            .body(riesgoDTO);
    }

    /**
     * {@code PUT  /riesgos/:id} : Updates an existing riesgo.
     *
     * @param id the id of the riesgoDTO to save.
     * @param riesgoDTO the riesgoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated riesgoDTO,
     * or with status {@code 400 (Bad Request)} if the riesgoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the riesgoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<RiesgoDTO> updateRiesgo(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RiesgoDTO riesgoDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Riesgo : {}, {}", id, riesgoDTO);
        if (riesgoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, riesgoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!riesgoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        riesgoDTO = riesgoService.update(riesgoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, riesgoDTO.getId().toString()))
            .body(riesgoDTO);
    }

    /**
     * {@code PATCH  /riesgos/:id} : Partial updates given fields of an existing riesgo, field will ignore if it is null
     *
     * @param id the id of the riesgoDTO to save.
     * @param riesgoDTO the riesgoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated riesgoDTO,
     * or with status {@code 400 (Bad Request)} if the riesgoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the riesgoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the riesgoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RiesgoDTO> partialUpdateRiesgo(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RiesgoDTO riesgoDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Riesgo partially : {}, {}", id, riesgoDTO);
        if (riesgoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, riesgoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!riesgoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RiesgoDTO> result = riesgoService.partialUpdate(riesgoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, riesgoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /riesgos} : get all the riesgos.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of riesgos in body.
     */
    @GetMapping("")
    public List<RiesgoDTO> getAllRiesgos(@RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload) {
        LOG.debug("REST request to get all Riesgos");
        return riesgoService.findAll();
    }

    /**
     * {@code GET  /riesgos/:id} : get the "id" riesgo.
     *
     * @param id the id of the riesgoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the riesgoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<RiesgoDTO> getRiesgo(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Riesgo : {}", id);
        Optional<RiesgoDTO> riesgoDTO = riesgoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(riesgoDTO);
    }

    /**
     * {@code DELETE  /riesgos/:id} : delete the "id" riesgo.
     *
     * @param id the id of the riesgoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRiesgo(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Riesgo : {}", id);
        riesgoService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
