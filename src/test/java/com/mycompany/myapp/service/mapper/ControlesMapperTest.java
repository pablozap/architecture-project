package com.mycompany.myapp.service.mapper;

import static com.mycompany.myapp.domain.ControlesAsserts.*;
import static com.mycompany.myapp.domain.ControlesTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ControlesMapperTest {

    private ControlesMapper controlesMapper;

    @BeforeEach
    void setUp() {
        controlesMapper = new ControlesMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getControlesSample1();
        var actual = controlesMapper.toEntity(controlesMapper.toDto(expected));
        assertControlesAllPropertiesEquals(expected, actual);
    }
}
