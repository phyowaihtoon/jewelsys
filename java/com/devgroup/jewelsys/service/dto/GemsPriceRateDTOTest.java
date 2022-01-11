package com.devgroup.jewelsys.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.devgroup.jewelsys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GemsPriceRateDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GemsPriceRateDTO.class);
        GemsPriceRateDTO gemsPriceRateDTO1 = new GemsPriceRateDTO();
        gemsPriceRateDTO1.setId(1L);
        GemsPriceRateDTO gemsPriceRateDTO2 = new GemsPriceRateDTO();
        assertThat(gemsPriceRateDTO1).isNotEqualTo(gemsPriceRateDTO2);
        gemsPriceRateDTO2.setId(gemsPriceRateDTO1.getId());
        assertThat(gemsPriceRateDTO1).isEqualTo(gemsPriceRateDTO2);
        gemsPriceRateDTO2.setId(2L);
        assertThat(gemsPriceRateDTO1).isNotEqualTo(gemsPriceRateDTO2);
        gemsPriceRateDTO1.setId(null);
        assertThat(gemsPriceRateDTO1).isNotEqualTo(gemsPriceRateDTO2);
    }
}
