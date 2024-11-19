package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Controles;
import com.mycompany.myapp.domain.Riesgo;
import com.mycompany.myapp.service.dto.ControlesDTO;
import com.mycompany.myapp.service.dto.RiesgoDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Controles} and its DTO {@link ControlesDTO}.
 */
@Mapper(componentModel = "spring")
public interface ControlesMapper extends EntityMapper<ControlesDTO, Controles> {
    @Mapping(target = "riesgos", source = "riesgos", qualifiedByName = "riesgoIdSet")
    ControlesDTO toDto(Controles s);

    @Mapping(target = "riesgos", ignore = true)
    @Mapping(target = "removeRiesgo", ignore = true)
    Controles toEntity(ControlesDTO controlesDTO);

    @Named("riesgoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RiesgoDTO toDtoRiesgoId(Riesgo riesgo);

    @Named("riesgoIdSet")
    default Set<RiesgoDTO> toDtoRiesgoIdSet(Set<Riesgo> riesgo) {
        return riesgo.stream().map(this::toDtoRiesgoId).collect(Collectors.toSet());
    }
}
