package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.GrupoActivos;
import com.mycompany.myapp.domain.Riesgo;
import com.mycompany.myapp.service.dto.GrupoActivosDTO;
import com.mycompany.myapp.service.dto.RiesgoDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link GrupoActivos} and its DTO {@link GrupoActivosDTO}.
 */
@Mapper(componentModel = "spring")
public interface GrupoActivosMapper extends EntityMapper<GrupoActivosDTO, GrupoActivos> {
    @Mapping(target = "riesgos", source = "riesgos", qualifiedByName = "riesgoTipoRiesgoSet")
    GrupoActivosDTO toDto(GrupoActivos s);

    @Mapping(target = "riesgos", ignore = true)
    @Mapping(target = "removeRiesgos", ignore = true)
    GrupoActivos toEntity(GrupoActivosDTO grupoActivosDTO);

    @Named("riesgoTipoRiesgo")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "tipoRiesgo", source = "tipoRiesgo")
    RiesgoDTO toDtoRiesgoTipoRiesgo(Riesgo riesgo);

    @Named("riesgoTipoRiesgoSet")
    default Set<RiesgoDTO> toDtoRiesgoTipoRiesgoSet(Set<Riesgo> riesgo) {
        return riesgo.stream().map(this::toDtoRiesgoTipoRiesgo).collect(Collectors.toSet());
    }
}
