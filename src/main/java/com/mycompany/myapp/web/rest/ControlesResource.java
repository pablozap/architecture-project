package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.ControlesRepository;
import com.mycompany.myapp.service.ControlesService;
import com.mycompany.myapp.service.dto.ControlesDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Controles}.
 */
@RestController
@RequestMapping("/api/controles")
public class ControlesResource {

    private static final Logger LOG = LoggerFactory.getLogger(ControlesResource.class);

    private static final String ENTITY_NAME = "controles";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ControlesService controlesService;

    private final ControlesRepository controlesRepository;

    public ControlesResource(ControlesService controlesService, ControlesRepository controlesRepository) {
        this.controlesService = controlesService;
        this.controlesRepository = controlesRepository;
    }

    /**
     * {@code POST  /controles} : Create a new controles.
     *
     * @param controlesDTO the controlesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new controlesDTO, or with status {@code 400 (Bad Request)} if the controles has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ControlesDTO> createControles(@Valid @RequestBody ControlesDTO controlesDTO) throws URISyntaxException {
        LOG.debug("REST request to save Controles : {}", controlesDTO);
        if (controlesDTO.getId() != null) {
            throw new BadRequestAlertException("A new controles cannot already have an ID", ENTITY_NAME, "idexists");
        }
        controlesDTO = controlesService.save(controlesDTO);
        return ResponseEntity.created(new URI("/api/controles/" + controlesDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, controlesDTO.getId().toString()))
            .body(controlesDTO);
    }

    /**
     * {@code PUT  /controles/:id} : Updates an existing controles.
     *
     * @param id the id of the controlesDTO to save.
     * @param controlesDTO the controlesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated controlesDTO,
     * or with status {@code 400 (Bad Request)} if the controlesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the controlesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ControlesDTO> updateControles(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ControlesDTO controlesDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Controles : {}, {}", id, controlesDTO);
        if (controlesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, controlesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!controlesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        controlesDTO = controlesService.update(controlesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, controlesDTO.getId().toString()))
            .body(controlesDTO);
    }

    /**
     * {@code PATCH  /controles/:id} : Partial updates given fields of an existing controles, field will ignore if it is null
     *
     * @param id the id of the controlesDTO to save.
     * @param controlesDTO the controlesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated controlesDTO,
     * or with status {@code 400 (Bad Request)} if the controlesDTO is not valid,
     * or with status {@code 404 (Not Found)} if the controlesDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the controlesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ControlesDTO> partialUpdateControles(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ControlesDTO controlesDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Controles partially : {}, {}", id, controlesDTO);
        if (controlesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, controlesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!controlesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ControlesDTO> result = controlesService.partialUpdate(controlesDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, controlesDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /controles} : get all the controles.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of controles in body.
     */
    @GetMapping("")
    public List<ControlesDTO> getAllControles() {
        LOG.debug("REST request to get all Controles");
        return controlesService.findAll();
    }

    /**
     * {@code GET  /controles/:id} : get the "id" controles.
     *
     * @param id the id of the controlesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the controlesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ControlesDTO> getControles(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Controles : {}", id);
        Optional<ControlesDTO> controlesDTO = controlesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(controlesDTO);
    }

    /**
     * {@code DELETE  /controles/:id} : delete the "id" controles.
     *
     * @param id the id of the controlesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteControles(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Controles : {}", id);
        controlesService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
