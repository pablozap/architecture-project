package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.ActivoInformacionTestSamples.*;
import static com.mycompany.myapp.domain.GrupoActivosTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ActivoInformacionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ActivoInformacion.class);
        ActivoInformacion activoInformacion1 = getActivoInformacionSample1();
        ActivoInformacion activoInformacion2 = new ActivoInformacion();
        assertThat(activoInformacion1).isNotEqualTo(activoInformacion2);

        activoInformacion2.setId(activoInformacion1.getId());
        assertThat(activoInformacion1).isEqualTo(activoInformacion2);

        activoInformacion2 = getActivoInformacionSample2();
        assertThat(activoInformacion1).isNotEqualTo(activoInformacion2);
    }

    @Test
    void grupoActivoTest() {
        ActivoInformacion activoInformacion = getActivoInformacionRandomSampleGenerator();
        GrupoActivos grupoActivosBack = getGrupoActivosRandomSampleGenerator();

        activoInformacion.setGrupoActivo(grupoActivosBack);
        assertThat(activoInformacion.getGrupoActivo()).isEqualTo(grupoActivosBack);

        activoInformacion.grupoActivo(null);
        assertThat(activoInformacion.getGrupoActivo()).isNull();
    }
}
