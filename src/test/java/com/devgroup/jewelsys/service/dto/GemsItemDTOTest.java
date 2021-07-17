package com.devgroup.jewelsys.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.devgroup.jewelsys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GemsItemDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GemsItemDTO.class);
        GemsItemDTO gemsItemDTO1 = new GemsItemDTO();
        gemsItemDTO1.setId(1L);
        GemsItemDTO gemsItemDTO2 = new GemsItemDTO();
        assertThat(gemsItemDTO1).isNotEqualTo(gemsItemDTO2);
        gemsItemDTO2.setId(gemsItemDTO1.getId());
        assertThat(gemsItemDTO1).isEqualTo(gemsItemDTO2);
        gemsItemDTO2.setId(2L);
        assertThat(gemsItemDTO1).isNotEqualTo(gemsItemDTO2);
        gemsItemDTO1.setId(null);
        assertThat(gemsItemDTO1).isNotEqualTo(gemsItemDTO2);
    }
}
