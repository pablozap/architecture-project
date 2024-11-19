package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.VulnerabilidadesRepository;
import com.mycompany.myapp.service.VulnerabilidadesService;
import com.mycompany.myapp.service.dto.VulnerabilidadesDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Vulnerabilidades}.
 */
@RestController
@RequestMapping("/api/vulnerabilidades")
public class VulnerabilidadesResource {

    private static final Logger LOG = LoggerFactory.getLogger(VulnerabilidadesResource.class);

    private static final String ENTITY_NAME = "vulnerabilidades";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VulnerabilidadesService vulnerabilidadesService;

    private final VulnerabilidadesRepository vulnerabilidadesRepository;

    public VulnerabilidadesResource(
        VulnerabilidadesService vulnerabilidadesService,
        VulnerabilidadesRepository vulnerabilidadesRepository
    ) {
        this.vulnerabilidadesService = vulnerabilidadesService;
        this.vulnerabilidadesRepository = vulnerabilidadesRepository;
    }

    /**
     * {@code POST  /vulnerabilidades} : Create a new vulnerabilidades.
     *
     * @param vulnerabilidadesDTO the vulnerabilidadesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vulnerabilidadesDTO, or with status {@code 400 (Bad Request)} if the vulnerabilidades has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<VulnerabilidadesDTO> createVulnerabilidades(@Valid @RequestBody VulnerabilidadesDTO vulnerabilidadesDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save Vulnerabilidades : {}", vulnerabilidadesDTO);
        if (vulnerabilidadesDTO.getId() != null) {
            throw new BadRequestAlertException("A new vulnerabilidades cannot already have an ID", ENTITY_NAME, "idexists");
        }
        vulnerabilidadesDTO = vulnerabilidadesService.save(vulnerabilidadesDTO);
        return ResponseEntity.created(new URI("/api/vulnerabilidades/" + vulnerabilidadesDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, vulnerabilidadesDTO.getId().toString()))
            .body(vulnerabilidadesDTO);
    }

    /**
     * {@code PUT  /vulnerabilidades/:id} : Updates an existing vulnerabilidades.
     *
     * @param id the id of the vulnerabilidadesDTO to save.
     * @param vulnerabilidadesDTO the vulnerabilidadesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vulnerabilidadesDTO,
     * or with status {@code 400 (Bad Request)} if the vulnerabilidadesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vulnerabilidadesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<VulnerabilidadesDTO> updateVulnerabilidades(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody VulnerabilidadesDTO vulnerabilidadesDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Vulnerabilidades : {}, {}", id, vulnerabilidadesDTO);
        if (vulnerabilidadesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vulnerabilidadesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vulnerabilidadesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        vulnerabilidadesDTO = vulnerabilidadesService.update(vulnerabilidadesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, vulnerabilidadesDTO.getId().toString()))
            .body(vulnerabilidadesDTO);
    }

    /**
     * {@code PATCH  /vulnerabilidades/:id} : Partial updates given fields of an existing vulnerabilidades, field will ignore if it is null
     *
     * @param id the id of the vulnerabilidadesDTO to save.
     * @param vulnerabilidadesDTO the vulnerabilidadesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vulnerabilidadesDTO,
     * or with status {@code 400 (Bad Request)} if the vulnerabilidadesDTO is not valid,
     * or with status {@code 404 (Not Found)} if the vulnerabilidadesDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the vulnerabilidadesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<VulnerabilidadesDTO> partialUpdateVulnerabilidades(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody VulnerabilidadesDTO vulnerabilidadesDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Vulnerabilidades partially : {}, {}", id, vulnerabilidadesDTO);
        if (vulnerabilidadesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, vulnerabilidadesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!vulnerabilidadesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<VulnerabilidadesDTO> result = vulnerabilidadesService.partialUpdate(vulnerabilidadesDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, vulnerabilidadesDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /vulnerabilidades} : get all the vulnerabilidades.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vulnerabilidades in body.
     */
    @GetMapping("")
    public List<VulnerabilidadesDTO> getAllVulnerabilidades() {
        LOG.debug("REST request to get all Vulnerabilidades");
        return vulnerabilidadesService.findAll();
    }

    /**
     * {@code GET  /vulnerabilidades/:id} : get the "id" vulnerabilidades.
     *
     * @param id the id of the vulnerabilidadesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vulnerabilidadesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<VulnerabilidadesDTO> getVulnerabilidades(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Vulnerabilidades : {}", id);
        Optional<VulnerabilidadesDTO> vulnerabilidadesDTO = vulnerabilidadesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(vulnerabilidadesDTO);
    }

    /**
     * {@code DELETE  /vulnerabilidades/:id} : delete the "id" vulnerabilidades.
     *
     * @param id the id of the vulnerabilidadesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVulnerabilidades(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Vulnerabilidades : {}", id);
        vulnerabilidadesService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
