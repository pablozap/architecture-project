package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.ActivoInformacion;
import com.mycompany.myapp.repository.ActivoInformacionRepository;
import com.mycompany.myapp.service.dto.ActivoInformacionDTO;
import com.mycompany.myapp.service.mapper.ActivoInformacionMapper;
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
 * Service Implementation for managing {@link com.mycompany.myapp.domain.ActivoInformacion}.
 */
@Service
@Transactional
public class ActivoInformacionService {

    private static final Logger LOG = LoggerFactory.getLogger(ActivoInformacionService.class);

    private final ActivoInformacionRepository activoInformacionRepository;

    private final ActivoInformacionMapper activoInformacionMapper;

    public ActivoInformacionService(
        ActivoInformacionRepository activoInformacionRepository,
        ActivoInformacionMapper activoInformacionMapper
    ) {
        this.activoInformacionRepository = activoInformacionRepository;
        this.activoInformacionMapper = activoInformacionMapper;
    }

    /**
     * Save a activoInformacion.
     *
     * @param activoInformacionDTO the entity to save.
     * @return the persisted entity.
     */
    public ActivoInformacionDTO save(ActivoInformacionDTO activoInformacionDTO) {
        LOG.debug("Request to save ActivoInformacion : {}", activoInformacionDTO);
        ActivoInformacion activoInformacion = activoInformacionMapper.toEntity(activoInformacionDTO);
        activoInformacion = activoInformacionRepository.save(activoInformacion);
        return activoInformacionMapper.toDto(activoInformacion);
    }

    /**
     * Update a activoInformacion.
     *
     * @param activoInformacionDTO the entity to save.
     * @return the persisted entity.
     */
    public ActivoInformacionDTO update(ActivoInformacionDTO activoInformacionDTO) {
        LOG.debug("Request to update ActivoInformacion : {}", activoInformacionDTO);
        ActivoInformacion activoInformacion = activoInformacionMapper.toEntity(activoInformacionDTO);
        activoInformacion = activoInformacionRepository.save(activoInformacion);
        return activoInformacionMapper.toDto(activoInformacion);
    }

    /**
     * Partially update a activoInformacion.
     *
     * @param activoInformacionDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ActivoInformacionDTO> partialUpdate(ActivoInformacionDTO activoInformacionDTO) {
        LOG.debug("Request to partially update ActivoInformacion : {}", activoInformacionDTO);

        return activoInformacionRepository
            .findById(activoInformacionDTO.getId())
            .map(existingActivoInformacion -> {
                activoInformacionMapper.partialUpdate(existingActivoInformacion, activoInformacionDTO);

                return existingActivoInformacion;
            })
            .map(activoInformacionRepository::save)
            .map(activoInformacionMapper::toDto);
    }

    /**
     * Get all the activoInformacions.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ActivoInformacionDTO> findAll() {
        LOG.debug("Request to get all ActivoInformacions");
        return activoInformacionRepository
            .findAll()
            .stream()
            .map(activoInformacionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the activoInformacions with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<ActivoInformacionDTO> findAllWithEagerRelationships(Pageable pageable) {
        return activoInformacionRepository.findAllWithEagerRelationships(pageable).map(activoInformacionMapper::toDto);
    }

    /**
     * Get one activoInformacion by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ActivoInformacionDTO> findOne(Long id) {
        LOG.debug("Request to get ActivoInformacion : {}", id);
        return activoInformacionRepository.findOneWithEagerRelationships(id).map(activoInformacionMapper::toDto);
    }

    /**
     * Delete the activoInformacion by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete ActivoInformacion : {}", id);
        activoInformacionRepository.deleteById(id);
    }
}
