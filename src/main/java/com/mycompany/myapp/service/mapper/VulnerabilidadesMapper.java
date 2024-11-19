package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Vulnerabilidades;
import com.mycompany.myapp.service.dto.VulnerabilidadesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Vulnerabilidades} and its DTO {@link VulnerabilidadesDTO}.
 */
@Mapper(componentModel = "spring")
public interface VulnerabilidadesMapper extends EntityMapper<VulnerabilidadesDTO, Vulnerabilidades> {}
