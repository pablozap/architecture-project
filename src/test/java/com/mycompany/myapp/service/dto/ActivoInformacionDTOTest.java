package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ActivoInformacionDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ActivoInformacionDTO.class);
        ActivoInformacionDTO activoInformacionDTO1 = new ActivoInformacionDTO();
        activoInformacionDTO1.setId(1L);
        ActivoInformacionDTO activoInformacionDTO2 = new ActivoInformacionDTO();
        assertThat(activoInformacionDTO1).isNotEqualTo(activoInformacionDTO2);
        activoInformacionDTO2.setId(activoInformacionDTO1.getId());
        assertThat(activoInformacionDTO1).isEqualTo(activoInformacionDTO2);
        activoInformacionDTO2.setId(2L);
        assertThat(activoInformacionDTO1).isNotEqualTo(activoInformacionDTO2);
        activoInformacionDTO1.setId(null);
        assertThat(activoInformacionDTO1).isNotEqualTo(activoInformacionDTO2);
    }
}
