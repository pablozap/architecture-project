package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ControlesDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ControlesDTO.class);
        ControlesDTO controlesDTO1 = new ControlesDTO();
        controlesDTO1.setId(1L);
        ControlesDTO controlesDTO2 = new ControlesDTO();
        assertThat(controlesDTO1).isNotEqualTo(controlesDTO2);
        controlesDTO2.setId(controlesDTO1.getId());
        assertThat(controlesDTO1).isEqualTo(controlesDTO2);
        controlesDTO2.setId(2L);
        assertThat(controlesDTO1).isNotEqualTo(controlesDTO2);
        controlesDTO1.setId(null);
        assertThat(controlesDTO1).isNotEqualTo(controlesDTO2);
    }
}
