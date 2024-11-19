package com.mycompany.myapp.service.mapper;

import static com.mycompany.myapp.domain.GrupoActivosAsserts.*;
import static com.mycompany.myapp.domain.GrupoActivosTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GrupoActivosMapperTest {

    private GrupoActivosMapper grupoActivosMapper;

    @BeforeEach
    void setUp() {
        grupoActivosMapper = new GrupoActivosMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getGrupoActivosSample1();
        var actual = grupoActivosMapper.toEntity(grupoActivosMapper.toDto(expected));
        assertGrupoActivosAllPropertiesEquals(expected, actual);
    }
}
