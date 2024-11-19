package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.ControlesTestSamples.*;
import static com.mycompany.myapp.domain.RiesgoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ControlesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Controles.class);
        Controles controles1 = getControlesSample1();
        Controles controles2 = new Controles();
        assertThat(controles1).isNotEqualTo(controles2);

        controles2.setId(controles1.getId());
        assertThat(controles1).isEqualTo(controles2);

        controles2 = getControlesSample2();
        assertThat(controles1).isNotEqualTo(controles2);
    }

    @Test
    void riesgoTest() {
        Controles controles = getControlesRandomSampleGenerator();
        Riesgo riesgoBack = getRiesgoRandomSampleGenerator();

        controles.addRiesgo(riesgoBack);
        assertThat(controles.getRiesgos()).containsOnly(riesgoBack);
        assertThat(riesgoBack.getControles()).containsOnly(controles);

        controles.removeRiesgo(riesgoBack);
        assertThat(controles.getRiesgos()).doesNotContain(riesgoBack);
        assertThat(riesgoBack.getControles()).doesNotContain(controles);

        controles.riesgos(new HashSet<>(Set.of(riesgoBack)));
        assertThat(controles.getRiesgos()).containsOnly(riesgoBack);
        assertThat(riesgoBack.getControles()).containsOnly(controles);

        controles.setRiesgos(new HashSet<>());
        assertThat(controles.getRiesgos()).doesNotContain(riesgoBack);
        assertThat(riesgoBack.getControles()).doesNotContain(controles);
    }
}
