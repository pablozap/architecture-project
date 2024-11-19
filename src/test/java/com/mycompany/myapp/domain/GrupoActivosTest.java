package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.ActivoInformacionTestSamples.*;
import static com.mycompany.myapp.domain.GrupoActivosTestSamples.*;
import static com.mycompany.myapp.domain.RiesgoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class GrupoActivosTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GrupoActivos.class);
        GrupoActivos grupoActivos1 = getGrupoActivosSample1();
        GrupoActivos grupoActivos2 = new GrupoActivos();
        assertThat(grupoActivos1).isNotEqualTo(grupoActivos2);

        grupoActivos2.setId(grupoActivos1.getId());
        assertThat(grupoActivos1).isEqualTo(grupoActivos2);

        grupoActivos2 = getGrupoActivosSample2();
        assertThat(grupoActivos1).isNotEqualTo(grupoActivos2);
    }

    @Test
    void activosTest() {
        GrupoActivos grupoActivos = getGrupoActivosRandomSampleGenerator();
        ActivoInformacion activoInformacionBack = getActivoInformacionRandomSampleGenerator();

        grupoActivos.addActivos(activoInformacionBack);
        assertThat(grupoActivos.getActivos()).containsOnly(activoInformacionBack);
        assertThat(activoInformacionBack.getGrupoActivo()).isEqualTo(grupoActivos);

        grupoActivos.removeActivos(activoInformacionBack);
        assertThat(grupoActivos.getActivos()).doesNotContain(activoInformacionBack);
        assertThat(activoInformacionBack.getGrupoActivo()).isNull();

        grupoActivos.activos(new HashSet<>(Set.of(activoInformacionBack)));
        assertThat(grupoActivos.getActivos()).containsOnly(activoInformacionBack);
        assertThat(activoInformacionBack.getGrupoActivo()).isEqualTo(grupoActivos);

        grupoActivos.setActivos(new HashSet<>());
        assertThat(grupoActivos.getActivos()).doesNotContain(activoInformacionBack);
        assertThat(activoInformacionBack.getGrupoActivo()).isNull();
    }

    @Test
    void riesgosTest() {
        GrupoActivos grupoActivos = getGrupoActivosRandomSampleGenerator();
        Riesgo riesgoBack = getRiesgoRandomSampleGenerator();

        grupoActivos.addRiesgos(riesgoBack);
        assertThat(grupoActivos.getRiesgos()).containsOnly(riesgoBack);
        assertThat(riesgoBack.getActivos()).containsOnly(grupoActivos);

        grupoActivos.removeRiesgos(riesgoBack);
        assertThat(grupoActivos.getRiesgos()).doesNotContain(riesgoBack);
        assertThat(riesgoBack.getActivos()).doesNotContain(grupoActivos);

        grupoActivos.riesgos(new HashSet<>(Set.of(riesgoBack)));
        assertThat(grupoActivos.getRiesgos()).containsOnly(riesgoBack);
        assertThat(riesgoBack.getActivos()).containsOnly(grupoActivos);

        grupoActivos.setRiesgos(new HashSet<>());
        assertThat(grupoActivos.getRiesgos()).doesNotContain(riesgoBack);
        assertThat(riesgoBack.getActivos()).doesNotContain(grupoActivos);
    }
}
