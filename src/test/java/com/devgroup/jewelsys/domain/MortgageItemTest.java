package com.devgroup.jewelsys.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.devgroup.jewelsys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MortgageItemTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MortgageItem.class);
        MortgageItem mortgageItem1 = new MortgageItem();
        mortgageItem1.setId(1L);
        MortgageItem mortgageItem2 = new MortgageItem();
        mortgageItem2.setId(mortgageItem1.getId());
        assertThat(mortgageItem1).isEqualTo(mortgageItem2);
        mortgageItem2.setId(2L);
        assertThat(mortgageItem1).isNotEqualTo(mortgageItem2);
        mortgageItem1.setId(null);
        assertThat(mortgageItem1).isNotEqualTo(mortgageItem2);
    }
}
