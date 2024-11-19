package com.mycompany.myapp.service.mapper;

import static com.mycompany.myapp.domain.VulnerabilidadesAsserts.*;
import static com.mycompany.myapp.domain.VulnerabilidadesTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VulnerabilidadesMapperTest {

    private VulnerabilidadesMapper vulnerabilidadesMapper;

    @BeforeEach
    void setUp() {
        vulnerabilidadesMapper = new VulnerabilidadesMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getVulnerabilidadesSample1();
        var actual = vulnerabilidadesMapper.toEntity(vulnerabilidadesMapper.toDto(expected));
        assertVulnerabilidadesAllPropertiesEquals(expected, actual);
    }
}
