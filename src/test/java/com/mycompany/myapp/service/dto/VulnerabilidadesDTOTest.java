package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VulnerabilidadesDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VulnerabilidadesDTO.class);
        VulnerabilidadesDTO vulnerabilidadesDTO1 = new VulnerabilidadesDTO();
        vulnerabilidadesDTO1.setId(1L);
        VulnerabilidadesDTO vulnerabilidadesDTO2 = new VulnerabilidadesDTO();
        assertThat(vulnerabilidadesDTO1).isNotEqualTo(vulnerabilidadesDTO2);
        vulnerabilidadesDTO2.setId(vulnerabilidadesDTO1.getId());
        assertThat(vulnerabilidadesDTO1).isEqualTo(vulnerabilidadesDTO2);
        vulnerabilidadesDTO2.setId(2L);
        assertThat(vulnerabilidadesDTO1).isNotEqualTo(vulnerabilidadesDTO2);
        vulnerabilidadesDTO1.setId(null);
        assertThat(vulnerabilidadesDTO1).isNotEqualTo(vulnerabilidadesDTO2);
    }
}
