package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.GrupoActivos;
import com.mycompany.myapp.repository.GrupoActivosRepository;
import com.mycompany.myapp.service.dto.GrupoActivosDTO;
import com.mycompany.myapp.service.mapper.GrupoActivosMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.GrupoActivos}.
 */
@Service
@Transactional
public class GrupoActivosService {

    private static final Logger LOG = LoggerFactory.getLogger(GrupoActivosService.class);

    private final GrupoActivosRepository grupoActivosRepository;

    private final GrupoActivosMapper grupoActivosMapper;

    public GrupoActivosService(GrupoActivosRepository grupoActivosRepository, GrupoActivosMapper grupoActivosMapper) {
        this.grupoActivosRepository = grupoActivosRepository;
        this.grupoActivosMapper = grupoActivosMapper;
    }

    /**
     * Save a grupoActivos.
     *
     * @param grupoActivosDTO the entity to save.
     * @return the persisted entity.
     */
    public GrupoActivosDTO save(GrupoActivosDTO grupoActivosDTO) {
        LOG.debug("Request to save GrupoActivos : {}", grupoActivosDTO);
        GrupoActivos grupoActivos = grupoActivosMapper.toEntity(grupoActivosDTO);
        grupoActivos = grupoActivosRepository.save(grupoActivos);
        return grupoActivosMapper.toDto(grupoActivos);
    }

    /**
     * Update a grupoActivos.
     *
     * @param grupoActivosDTO the entity to save.
     * @return the persisted entity.
     */
    public GrupoActivosDTO update(GrupoActivosDTO grupoActivosDTO) {
        LOG.debug("Request to update GrupoActivos : {}", grupoActivosDTO);
        GrupoActivos grupoActivos = grupoActivosMapper.toEntity(grupoActivosDTO);
        grupoActivos = grupoActivosRepository.save(grupoActivos);
        return grupoActivosMapper.toDto(grupoActivos);
    }

    /**
     * Partially update a grupoActivos.
     *
     * @param grupoActivosDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<GrupoActivosDTO> partialUpdate(GrupoActivosDTO grupoActivosDTO) {
        LOG.debug("Request to partially update GrupoActivos : {}", grupoActivosDTO);

        return grupoActivosRepository
            .findById(grupoActivosDTO.getId())
            .map(existingGrupoActivos -> {
                grupoActivosMapper.partialUpdate(existingGrupoActivos, grupoActivosDTO);

                return existingGrupoActivos;
            })
            .map(grupoActivosRepository::save)
            .map(grupoActivosMapper::toDto);
    }

    /**
     * Get all the grupoActivos.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<GrupoActivosDTO> findAll() {
        LOG.debug("Request to get all GrupoActivos");
        return grupoActivosRepository.findAll().stream().map(grupoActivosMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one grupoActivos by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<GrupoActivosDTO> findOne(Long id) {
        LOG.debug("Request to get GrupoActivos : {}", id);
        return grupoActivosRepository.findById(id).map(grupoActivosMapper::toDto);
    }

    /**
     * Delete the grupoActivos by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete GrupoActivos : {}", id);
        grupoActivosRepository.deleteById(id);
    }
}
