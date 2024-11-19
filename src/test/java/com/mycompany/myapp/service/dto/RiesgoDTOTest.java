package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RiesgoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RiesgoDTO.class);
        RiesgoDTO riesgoDTO1 = new RiesgoDTO();
        riesgoDTO1.setId(1L);
        RiesgoDTO riesgoDTO2 = new RiesgoDTO();
        assertThat(riesgoDTO1).isNotEqualTo(riesgoDTO2);
        riesgoDTO2.setId(riesgoDTO1.getId());
        assertThat(riesgoDTO1).isEqualTo(riesgoDTO2);
        riesgoDTO2.setId(2L);
        assertThat(riesgoDTO1).isNotEqualTo(riesgoDTO2);
        riesgoDTO1.setId(null);
        assertThat(riesgoDTO1).isNotEqualTo(riesgoDTO2);
    }
}
