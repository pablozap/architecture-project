package com.mycompany.myapp.service.mapper;

import static com.mycompany.myapp.domain.AmenazasAsserts.*;
import static com.mycompany.myapp.domain.AmenazasTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AmenazasMapperTest {

    private AmenazasMapper amenazasMapper;

    @BeforeEach
    void setUp() {
        amenazasMapper = new AmenazasMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getAmenazasSample1();
        var actual = amenazasMapper.toEntity(amenazasMapper.toDto(expected));
        assertAmenazasAllPropertiesEquals(expected, actual);
    }
}
