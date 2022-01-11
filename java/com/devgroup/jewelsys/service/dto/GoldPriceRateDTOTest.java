package com.devgroup.jewelsys.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.devgroup.jewelsys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GoldPriceRateDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GoldPriceRateDTO.class);
        GoldPriceRateDTO goldPriceRateDTO1 = new GoldPriceRateDTO();
        goldPriceRateDTO1.setId(1L);
        GoldPriceRateDTO goldPriceRateDTO2 = new GoldPriceRateDTO();
        assertThat(goldPriceRateDTO1).isNotEqualTo(goldPriceRateDTO2);
        goldPriceRateDTO2.setId(goldPriceRateDTO1.getId());
        assertThat(goldPriceRateDTO1).isEqualTo(goldPriceRateDTO2);
        goldPriceRateDTO2.setId(2L);
        assertThat(goldPriceRateDTO1).isNotEqualTo(goldPriceRateDTO2);
        goldPriceRateDTO1.setId(null);
        assertThat(goldPriceRateDTO1).isNotEqualTo(goldPriceRateDTO2);
    }
}
