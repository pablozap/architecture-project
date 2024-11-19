package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Amenazas;
import com.mycompany.myapp.service.dto.AmenazasDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Amenazas} and its DTO {@link AmenazasDTO}.
 */
@Mapper(componentModel = "spring")
public interface AmenazasMapper extends EntityMapper<AmenazasDTO, Amenazas> {}
