package com.devgroup.jewelsys.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.devgroup.jewelsys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GemsPriceRateTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GemsPriceRate.class);
        GemsPriceRate gemsPriceRate1 = new GemsPriceRate();
        gemsPriceRate1.setId(1L);
        GemsPriceRate gemsPriceRate2 = new GemsPriceRate();
        gemsPriceRate2.setId(gemsPriceRate1.getId());
        assertThat(gemsPriceRate1).isEqualTo(gemsPriceRate2);
        gemsPriceRate2.setId(2L);
        assertThat(gemsPriceRate1).isNotEqualTo(gemsPriceRate2);
        gemsPriceRate1.setId(null);
        assertThat(gemsPriceRate1).isNotEqualTo(gemsPriceRate2);
    }
}
