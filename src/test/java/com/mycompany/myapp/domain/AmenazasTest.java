package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.AmenazasTestSamples.*;
import static com.mycompany.myapp.domain.RiesgoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class AmenazasTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Amenazas.class);
        Amenazas amenazas1 = getAmenazasSample1();
        Amenazas amenazas2 = new Amenazas();
        assertThat(amenazas1).isNotEqualTo(amenazas2);

        amenazas2.setId(amenazas1.getId());
        assertThat(amenazas1).isEqualTo(amenazas2);

        amenazas2 = getAmenazasSample2();
        assertThat(amenazas1).isNotEqualTo(amenazas2);
    }

    @Test
    void riesgoTest() {
        Amenazas amenazas = getAmenazasRandomSampleGenerator();
        Riesgo riesgoBack = getRiesgoRandomSampleGenerator();

        amenazas.addRiesgo(riesgoBack);
        assertThat(amenazas.getRiesgos()).containsOnly(riesgoBack);
        assertThat(riesgoBack.getAmenaza()).isEqualTo(amenazas);

        amenazas.removeRiesgo(riesgoBack);
        assertThat(amenazas.getRiesgos()).doesNotContain(riesgoBack);
        assertThat(riesgoBack.getAmenaza()).isNull();

        amenazas.riesgos(new HashSet<>(Set.of(riesgoBack)));
        assertThat(amenazas.getRiesgos()).containsOnly(riesgoBack);
        assertThat(riesgoBack.getAmenaza()).isEqualTo(amenazas);

        amenazas.setRiesgos(new HashSet<>());
        assertThat(amenazas.getRiesgos()).doesNotContain(riesgoBack);
        assertThat(riesgoBack.getAmenaza()).isNull();
    }
}
