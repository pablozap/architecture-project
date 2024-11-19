package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Controles;
import com.mycompany.myapp.repository.ControlesRepository;
import com.mycompany.myapp.service.dto.ControlesDTO;
import com.mycompany.myapp.service.mapper.ControlesMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.Controles}.
 */
@Service
@Transactional
public class ControlesService {

    private static final Logger LOG = LoggerFactory.getLogger(ControlesService.class);

    private final ControlesRepository controlesRepository;

    private final ControlesMapper controlesMapper;

    public ControlesService(ControlesRepository controlesRepository, ControlesMapper controlesMapper) {
        this.controlesRepository = controlesRepository;
        this.controlesMapper = controlesMapper;
    }

    /**
     * Save a controles.
     *
     * @param controlesDTO the entity to save.
     * @return the persisted entity.
     */
    public ControlesDTO save(ControlesDTO controlesDTO) {
        LOG.debug("Request to save Controles : {}", controlesDTO);
        Controles controles = controlesMapper.toEntity(controlesDTO);
        controles = controlesRepository.save(controles);
        return controlesMapper.toDto(controles);
    }

    /**
     * Update a controles.
     *
     * @param controlesDTO the entity to save.
     * @return the persisted entity.
     */
    public ControlesDTO update(ControlesDTO controlesDTO) {
        LOG.debug("Request to update Controles : {}", controlesDTO);
        Controles controles = controlesMapper.toEntity(controlesDTO);
        controles = controlesRepository.save(controles);
        return controlesMapper.toDto(controles);
    }

    /**
     * Partially update a controles.
     *
     * @param controlesDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ControlesDTO> partialUpdate(ControlesDTO controlesDTO) {
        LOG.debug("Request to partially update Controles : {}", controlesDTO);

        return controlesRepository
            .findById(controlesDTO.getId())
            .map(existingControles -> {
                controlesMapper.partialUpdate(existingControles, controlesDTO);

                return existingControles;
            })
            .map(controlesRepository::save)
            .map(controlesMapper::toDto);
    }

    /**
     * Get all the controles.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ControlesDTO> findAll() {
        LOG.debug("Request to get all Controles");
        return controlesRepository.findAll().stream().map(controlesMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one controles by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ControlesDTO> findOne(Long id) {
        LOG.debug("Request to get Controles : {}", id);
        return controlesRepository.findById(id).map(controlesMapper::toDto);
    }

    /**
     * Delete the controles by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Controles : {}", id);
        controlesRepository.deleteById(id);
    }
}
