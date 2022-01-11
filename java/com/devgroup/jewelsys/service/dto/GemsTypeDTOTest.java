package com.devgroup.jewelsys.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.devgroup.jewelsys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GemsTypeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GemsTypeDTO.class);
        GemsTypeDTO gemsTypeDTO1 = new GemsTypeDTO();
        gemsTypeDTO1.setId(1L);
        GemsTypeDTO gemsTypeDTO2 = new GemsTypeDTO();
        assertThat(gemsTypeDTO1).isNotEqualTo(gemsTypeDTO2);
        gemsTypeDTO2.setId(gemsTypeDTO1.getId());
        assertThat(gemsTypeDTO1).isEqualTo(gemsTypeDTO2);
        gemsTypeDTO2.setId(2L);
        assertThat(gemsTypeDTO1).isNotEqualTo(gemsTypeDTO2);
        gemsTypeDTO1.setId(null);
        assertThat(gemsTypeDTO1).isNotEqualTo(gemsTypeDTO2);
    }
}
