package com.mycompany.myapp.service.mapper;

import static com.mycompany.myapp.domain.ActivoInformacionAsserts.*;
import static com.mycompany.myapp.domain.ActivoInformacionTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ActivoInformacionMapperTest {

    private ActivoInformacionMapper activoInformacionMapper;

    @BeforeEach
    void setUp() {
        activoInformacionMapper = new ActivoInformacionMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getActivoInformacionSample1();
        var actual = activoInformacionMapper.toEntity(activoInformacionMapper.toDto(expected));
        assertActivoInformacionAllPropertiesEquals(expected, actual);
    }
}
