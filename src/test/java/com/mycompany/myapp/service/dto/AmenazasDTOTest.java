package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AmenazasDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AmenazasDTO.class);
        AmenazasDTO amenazasDTO1 = new AmenazasDTO();
        amenazasDTO1.setId(1L);
        AmenazasDTO amenazasDTO2 = new AmenazasDTO();
        assertThat(amenazasDTO1).isNotEqualTo(amenazasDTO2);
        amenazasDTO2.setId(amenazasDTO1.getId());
        assertThat(amenazasDTO1).isEqualTo(amenazasDTO2);
        amenazasDTO2.setId(2L);
        assertThat(amenazasDTO1).isNotEqualTo(amenazasDTO2);
        amenazasDTO1.setId(null);
        assertThat(amenazasDTO1).isNotEqualTo(amenazasDTO2);
    }
}
