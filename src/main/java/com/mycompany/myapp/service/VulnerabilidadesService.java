package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Vulnerabilidades;
import com.mycompany.myapp.repository.VulnerabilidadesRepository;
import com.mycompany.myapp.service.dto.VulnerabilidadesDTO;
import com.mycompany.myapp.service.mapper.VulnerabilidadesMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.Vulnerabilidades}.
 */
@Service
@Transactional
public class VulnerabilidadesService {

    private static final Logger LOG = LoggerFactory.getLogger(VulnerabilidadesService.class);

    private final VulnerabilidadesRepository vulnerabilidadesRepository;

    private final VulnerabilidadesMapper vulnerabilidadesMapper;

    public VulnerabilidadesService(VulnerabilidadesRepository vulnerabilidadesRepository, VulnerabilidadesMapper vulnerabilidadesMapper) {
        this.vulnerabilidadesRepository = vulnerabilidadesRepository;
        this.vulnerabilidadesMapper = vulnerabilidadesMapper;
    }

    /**
     * Save a vulnerabilidades.
     *
     * @param vulnerabilidadesDTO the entity to save.
     * @return the persisted entity.
     */
    public VulnerabilidadesDTO save(VulnerabilidadesDTO vulnerabilidadesDTO) {
        LOG.debug("Request to save Vulnerabilidades : {}", vulnerabilidadesDTO);
        Vulnerabilidades vulnerabilidades = vulnerabilidadesMapper.toEntity(vulnerabilidadesDTO);
        vulnerabilidades = vulnerabilidadesRepository.save(vulnerabilidades);
        return vulnerabilidadesMapper.toDto(vulnerabilidades);
    }

    /**
     * Update a vulnerabilidades.
     *
     * @param vulnerabilidadesDTO the entity to save.
     * @return the persisted entity.
     */
    public VulnerabilidadesDTO update(VulnerabilidadesDTO vulnerabilidadesDTO) {
        LOG.debug("Request to update Vulnerabilidades : {}", vulnerabilidadesDTO);
        Vulnerabilidades vulnerabilidades = vulnerabilidadesMapper.toEntity(vulnerabilidadesDTO);
        vulnerabilidades = vulnerabilidadesRepository.save(vulnerabilidades);
        return vulnerabilidadesMapper.toDto(vulnerabilidades);
    }

    /**
     * Partially update a vulnerabilidades.
     *
     * @param vulnerabilidadesDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<VulnerabilidadesDTO> partialUpdate(VulnerabilidadesDTO vulnerabilidadesDTO) {
        LOG.debug("Request to partially update Vulnerabilidades : {}", vulnerabilidadesDTO);

        return vulnerabilidadesRepository
            .findById(vulnerabilidadesDTO.getId())
            .map(existingVulnerabilidades -> {
                vulnerabilidadesMapper.partialUpdate(existingVulnerabilidades, vulnerabilidadesDTO);

                return existingVulnerabilidades;
            })
            .map(vulnerabilidadesRepository::save)
            .map(vulnerabilidadesMapper::toDto);
    }

    /**
     * Get all the vulnerabilidades.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<VulnerabilidadesDTO> findAll() {
        LOG.debug("Request to get all Vulnerabilidades");
        return vulnerabilidadesRepository
            .findAll()
            .stream()
            .map(vulnerabilidadesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one vulnerabilidades by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<VulnerabilidadesDTO> findOne(Long id) {
        LOG.debug("Request to get Vulnerabilidades : {}", id);
        return vulnerabilidadesRepository.findById(id).map(vulnerabilidadesMapper::toDto);
    }

    /**
     * Delete the vulnerabilidades by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Vulnerabilidades : {}", id);
        vulnerabilidadesRepository.deleteById(id);
    }
}
