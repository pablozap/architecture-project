package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Amenazas;
import com.mycompany.myapp.domain.Controles;
import com.mycompany.myapp.domain.GrupoActivos;
import com.mycompany.myapp.domain.Riesgo;
import com.mycompany.myapp.domain.Vulnerabilidades;
import com.mycompany.myapp.service.dto.AmenazasDTO;
import com.mycompany.myapp.service.dto.ControlesDTO;
import com.mycompany.myapp.service.dto.GrupoActivosDTO;
import com.mycompany.myapp.service.dto.RiesgoDTO;
import com.mycompany.myapp.service.dto.VulnerabilidadesDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Riesgo} and its DTO {@link RiesgoDTO}.
 */
@Mapper(componentModel = "spring")
public interface RiesgoMapper extends EntityMapper<RiesgoDTO, Riesgo> {
    @Mapping(target = "activos", source = "activos", qualifiedByName = "grupoActivosNombreSet")
    @Mapping(target = "controles", source = "controles", qualifiedByName = "controlesNombreSet")
    @Mapping(target = "amenaza", source = "amenaza", qualifiedByName = "amenazasNombre")
    @Mapping(target = "vulnerabilidad", source = "vulnerabilidad", qualifiedByName = "vulnerabilidadesNombre")
    RiesgoDTO toDto(Riesgo s);

    @Mapping(target = "removeActivos", ignore = true)
    @Mapping(target = "removeControles", ignore = true)
    Riesgo toEntity(RiesgoDTO riesgoDTO);

    @Named("grupoActivosNombre")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nombre", source = "nombre")
    GrupoActivosDTO toDtoGrupoActivosNombre(GrupoActivos grupoActivos);

    @Named("grupoActivosNombreSet")
    default Set<GrupoActivosDTO> toDtoGrupoActivosNombreSet(Set<GrupoActivos> grupoActivos) {
        return grupoActivos.stream().map(this::toDtoGrupoActivosNombre).collect(Collectors.toSet());
    }

    @Named("controlesNombre")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nombre", source = "nombre")
    ControlesDTO toDtoControlesNombre(Controles controles);

    @Named("controlesNombreSet")
    default Set<ControlesDTO> toDtoControlesNombreSet(Set<Controles> controles) {
        return controles.stream().map(this::toDtoControlesNombre).collect(Collectors.toSet());
    }

    @Named("amenazasNombre")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nombre", source = "nombre")
    AmenazasDTO toDtoAmenazasNombre(Amenazas amenazas);

    @Named("vulnerabilidadesNombre")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nombre", source = "nombre")
    VulnerabilidadesDTO toDtoVulnerabilidadesNombre(Vulnerabilidades vulnerabilidades);
}
