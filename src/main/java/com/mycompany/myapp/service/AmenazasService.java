package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Amenazas;
import com.mycompany.myapp.repository.AmenazasRepository;
import com.mycompany.myapp.service.dto.AmenazasDTO;
import com.mycompany.myapp.service.mapper.AmenazasMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.Amenazas}.
 */
@Service
@Transactional
public class AmenazasService {

    private static final Logger LOG = LoggerFactory.getLogger(AmenazasService.class);

    private final AmenazasRepository amenazasRepository;

    private final AmenazasMapper amenazasMapper;

    public AmenazasService(AmenazasRepository amenazasRepository, AmenazasMapper amenazasMapper) {
        this.amenazasRepository = amenazasRepository;
        this.amenazasMapper = amenazasMapper;
    }

    /**
     * Save a amenazas.
     *
     * @param amenazasDTO the entity to save.
     * @return the persisted entity.
     */
    public AmenazasDTO save(AmenazasDTO amenazasDTO) {
        LOG.debug("Request to save Amenazas : {}", amenazasDTO);
        Amenazas amenazas = amenazasMapper.toEntity(amenazasDTO);
        amenazas = amenazasRepository.save(amenazas);
        return amenazasMapper.toDto(amenazas);
    }

    /**
     * Update a amenazas.
     *
     * @param amenazasDTO the entity to save.
     * @return the persisted entity.
     */
    public AmenazasDTO update(AmenazasDTO amenazasDTO) {
        LOG.debug("Request to update Amenazas : {}", amenazasDTO);
        Amenazas amenazas = amenazasMapper.toEntity(amenazasDTO);
        amenazas = amenazasRepository.save(amenazas);
        return amenazasMapper.toDto(amenazas);
    }

    /**
     * Partially update a amenazas.
     *
     * @param amenazasDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AmenazasDTO> partialUpdate(AmenazasDTO amenazasDTO) {
        LOG.debug("Request to partially update Amenazas : {}", amenazasDTO);

        return amenazasRepository
            .findById(amenazasDTO.getId())
            .map(existingAmenazas -> {
                amenazasMapper.partialUpdate(existingAmenazas, amenazasDTO);

                return existingAmenazas;
            })
            .map(amenazasRepository::save)
            .map(amenazasMapper::toDto);
    }

    /**
     * Get all the amenazas.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AmenazasDTO> findAll() {
        LOG.debug("Request to get all Amenazas");
        return amenazasRepository.findAll().stream().map(amenazasMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one amenazas by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AmenazasDTO> findOne(Long id) {
        LOG.debug("Request to get Amenazas : {}", id);
        return amenazasRepository.findById(id).map(amenazasMapper::toDto);
    }

    /**
     * Delete the amenazas by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Amenazas : {}", id);
        amenazasRepository.deleteById(id);
    }
}
