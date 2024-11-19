package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Riesgo;
import com.mycompany.myapp.repository.RiesgoRepository;
import com.mycompany.myapp.service.dto.RiesgoDTO;
import com.mycompany.myapp.service.mapper.RiesgoMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.Riesgo}.
 */
@Service
@Transactional
public class RiesgoService {

    private static final Logger LOG = LoggerFactory.getLogger(RiesgoService.class);

    private final RiesgoRepository riesgoRepository;

    private final RiesgoMapper riesgoMapper;

    public RiesgoService(RiesgoRepository riesgoRepository, RiesgoMapper riesgoMapper) {
        this.riesgoRepository = riesgoRepository;
        this.riesgoMapper = riesgoMapper;
    }

    /**
     * Save a riesgo.
     *
     * @param riesgoDTO the entity to save.
     * @return the persisted entity.
     */
    public RiesgoDTO save(RiesgoDTO riesgoDTO) {
        LOG.debug("Request to save Riesgo : {}", riesgoDTO);
        Riesgo riesgo = riesgoMapper.toEntity(riesgoDTO);
        riesgo = riesgoRepository.save(riesgo);
        return riesgoMapper.toDto(riesgo);
    }

    /**
     * Update a riesgo.
     *
     * @param riesgoDTO the entity to save.
     * @return the persisted entity.
     */
    public RiesgoDTO update(RiesgoDTO riesgoDTO) {
        LOG.debug("Request to update Riesgo : {}", riesgoDTO);
        Riesgo riesgo = riesgoMapper.toEntity(riesgoDTO);
        riesgo = riesgoRepository.save(riesgo);
        return riesgoMapper.toDto(riesgo);
    }

    /**
     * Partially update a riesgo.
     *
     * @param riesgoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<RiesgoDTO> partialUpdate(RiesgoDTO riesgoDTO) {
        LOG.debug("Request to partially update Riesgo : {}", riesgoDTO);

        return riesgoRepository
            .findById(riesgoDTO.getId())
            .map(existingRiesgo -> {
                riesgoMapper.partialUpdate(existingRiesgo, riesgoDTO);

                return existingRiesgo;
            })
            .map(riesgoRepository::save)
            .map(riesgoMapper::toDto);
    }

    /**
     * Get all the riesgos.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<RiesgoDTO> findAll() {
        LOG.debug("Request to get all Riesgos");
        return riesgoRepository.findAll().stream().map(riesgoMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the riesgos with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<RiesgoDTO> findAllWithEagerRelationships(Pageable pageable) {
        return riesgoRepository.findAllWithEagerRelationships(pageable).map(riesgoMapper::toDto);
    }

    /**
     * Get one riesgo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RiesgoDTO> findOne(Long id) {
        LOG.debug("Request to get Riesgo : {}", id);
        return riesgoRepository.findOneWithEagerRelationships(id).map(riesgoMapper::toDto);
    }

    /**
     * Delete the riesgo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Riesgo : {}", id);
        riesgoRepository.deleteById(id);
    }
}
