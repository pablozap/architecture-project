package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GrupoActivosDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GrupoActivosDTO.class);
        GrupoActivosDTO grupoActivosDTO1 = new GrupoActivosDTO();
        grupoActivosDTO1.setId(1L);
        GrupoActivosDTO grupoActivosDTO2 = new GrupoActivosDTO();
        assertThat(grupoActivosDTO1).isNotEqualTo(grupoActivosDTO2);
        grupoActivosDTO2.setId(grupoActivosDTO1.getId());
        assertThat(grupoActivosDTO1).isEqualTo(grupoActivosDTO2);
        grupoActivosDTO2.setId(2L);
        assertThat(grupoActivosDTO1).isNotEqualTo(grupoActivosDTO2);
        grupoActivosDTO1.setId(null);
        assertThat(grupoActivosDTO1).isNotEqualTo(grupoActivosDTO2);
    }
}
