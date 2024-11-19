package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.ActivoInformacion;
import com.mycompany.myapp.domain.GrupoActivos;
import com.mycompany.myapp.service.dto.ActivoInformacionDTO;
import com.mycompany.myapp.service.dto.GrupoActivosDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ActivoInformacion} and its DTO {@link ActivoInformacionDTO}.
 */
@Mapper(componentModel = "spring")
public interface ActivoInformacionMapper extends EntityMapper<ActivoInformacionDTO, ActivoInformacion> {
    @Mapping(target = "grupoActivo", source = "grupoActivo", qualifiedByName = "grupoActivosNombre")
    ActivoInformacionDTO toDto(ActivoInformacion s);

    @Named("grupoActivosNombre")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nombre", source = "nombre")
    GrupoActivosDTO toDtoGrupoActivosNombre(GrupoActivos grupoActivos);
}
