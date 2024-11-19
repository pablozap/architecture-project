package com.mycompany.myapp.service.mapper;

import static com.mycompany.myapp.domain.RiesgoAsserts.*;
import static com.mycompany.myapp.domain.RiesgoTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RiesgoMapperTest {

    private RiesgoMapper riesgoMapper;

    @BeforeEach
    void setUp() {
        riesgoMapper = new RiesgoMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getRiesgoSample1();
        var actual = riesgoMapper.toEntity(riesgoMapper.toDto(expected));
        assertRiesgoAllPropertiesEquals(expected, actual);
    }
}
