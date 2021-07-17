package com.devgroup.jewelsys.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.devgroup.jewelsys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GoldPriceRateTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GoldPriceRate.class);
        GoldPriceRate goldPriceRate1 = new GoldPriceRate();
        goldPriceRate1.setId(1L);
        GoldPriceRate goldPriceRate2 = new GoldPriceRate();
        goldPriceRate2.setId(goldPriceRate1.getId());
        assertThat(goldPriceRate1).isEqualTo(goldPriceRate2);
        goldPriceRate2.setId(2L);
        assertThat(goldPriceRate1).isNotEqualTo(goldPriceRate2);
        goldPriceRate1.setId(null);
        assertThat(goldPriceRate1).isNotEqualTo(goldPriceRate2);
    }
}
