package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.RiesgoTestSamples.*;
import static com.mycompany.myapp.domain.VulnerabilidadesTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class VulnerabilidadesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Vulnerabilidades.class);
        Vulnerabilidades vulnerabilidades1 = getVulnerabilidadesSample1();
        Vulnerabilidades vulnerabilidades2 = new Vulnerabilidades();
        assertThat(vulnerabilidades1).isNotEqualTo(vulnerabilidades2);

        vulnerabilidades2.setId(vulnerabilidades1.getId());
        assertThat(vulnerabilidades1).isEqualTo(vulnerabilidades2);

        vulnerabilidades2 = getVulnerabilidadesSample2();
        assertThat(vulnerabilidades1).isNotEqualTo(vulnerabilidades2);
    }

    @Test
    void riesgoTest() {
        Vulnerabilidades vulnerabilidades = getVulnerabilidadesRandomSampleGenerator();
        Riesgo riesgoBack = getRiesgoRandomSampleGenerator();

        vulnerabilidades.addRiesgo(riesgoBack);
        assertThat(vulnerabilidades.getRiesgos()).containsOnly(riesgoBack);
        assertThat(riesgoBack.getVulnerabilidad()).isEqualTo(vulnerabilidades);

        vulnerabilidades.removeRiesgo(riesgoBack);
        assertThat(vulnerabilidades.getRiesgos()).doesNotContain(riesgoBack);
        assertThat(riesgoBack.getVulnerabilidad()).isNull();

        vulnerabilidades.riesgos(new HashSet<>(Set.of(riesgoBack)));
        assertThat(vulnerabilidades.getRiesgos()).containsOnly(riesgoBack);
        assertThat(riesgoBack.getVulnerabilidad()).isEqualTo(vulnerabilidades);

        vulnerabilidades.setRiesgos(new HashSet<>());
        assertThat(vulnerabilidades.getRiesgos()).doesNotContain(riesgoBack);
        assertThat(riesgoBack.getVulnerabilidad()).isNull();
    }
}
