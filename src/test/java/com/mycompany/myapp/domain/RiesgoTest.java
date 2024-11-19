package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.AmenazasTestSamples.*;
import static com.mycompany.myapp.domain.ControlesTestSamples.*;
import static com.mycompany.myapp.domain.GrupoActivosTestSamples.*;
import static com.mycompany.myapp.domain.RiesgoTestSamples.*;
import static com.mycompany.myapp.domain.VulnerabilidadesTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class RiesgoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Riesgo.class);
        Riesgo riesgo1 = getRiesgoSample1();
        Riesgo riesgo2 = new Riesgo();
        assertThat(riesgo1).isNotEqualTo(riesgo2);

        riesgo2.setId(riesgo1.getId());
        assertThat(riesgo1).isEqualTo(riesgo2);

        riesgo2 = getRiesgoSample2();
        assertThat(riesgo1).isNotEqualTo(riesgo2);
    }

    @Test
    void activosTest() {
        Riesgo riesgo = getRiesgoRandomSampleGenerator();
        GrupoActivos grupoActivosBack = getGrupoActivosRandomSampleGenerator();

        riesgo.addActivos(grupoActivosBack);
        assertThat(riesgo.getActivos()).containsOnly(grupoActivosBack);

        riesgo.removeActivos(grupoActivosBack);
        assertThat(riesgo.getActivos()).doesNotContain(grupoActivosBack);

        riesgo.activos(new HashSet<>(Set.of(grupoActivosBack)));
        assertThat(riesgo.getActivos()).containsOnly(grupoActivosBack);

        riesgo.setActivos(new HashSet<>());
        assertThat(riesgo.getActivos()).doesNotContain(grupoActivosBack);
    }

    @Test
    void controlesTest() {
        Riesgo riesgo = getRiesgoRandomSampleGenerator();
        Controles controlesBack = getControlesRandomSampleGenerator();

        riesgo.addControles(controlesBack);
        assertThat(riesgo.getControles()).containsOnly(controlesBack);

        riesgo.removeControles(controlesBack);
        assertThat(riesgo.getControles()).doesNotContain(controlesBack);

        riesgo.controles(new HashSet<>(Set.of(controlesBack)));
        assertThat(riesgo.getControles()).containsOnly(controlesBack);

        riesgo.setControles(new HashSet<>());
        assertThat(riesgo.getControles()).doesNotContain(controlesBack);
    }

    @Test
    void amenazaTest() {
        Riesgo riesgo = getRiesgoRandomSampleGenerator();
        Amenazas amenazasBack = getAmenazasRandomSampleGenerator();

        riesgo.setAmenaza(amenazasBack);
        assertThat(riesgo.getAmenaza()).isEqualTo(amenazasBack);

        riesgo.amenaza(null);
        assertThat(riesgo.getAmenaza()).isNull();
    }

    @Test
    void vulnerabilidadTest() {
        Riesgo riesgo = getRiesgoRandomSampleGenerator();
        Vulnerabilidades vulnerabilidadesBack = getVulnerabilidadesRandomSampleGenerator();

        riesgo.setVulnerabilidad(vulnerabilidadesBack);
        assertThat(riesgo.getVulnerabilidad()).isEqualTo(vulnerabilidadesBack);

        riesgo.vulnerabilidad(null);
        assertThat(riesgo.getVulnerabilidad()).isNull();
    }
}
