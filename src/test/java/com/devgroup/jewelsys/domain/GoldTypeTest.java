package com.devgroup.jewelsys.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.devgroup.jewelsys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GoldTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GoldType.class);
        GoldType goldType1 = new GoldType();
        goldType1.setId(1L);
        GoldType goldType2 = new GoldType();
        goldType2.setId(goldType1.getId());
        assertThat(goldType1).isEqualTo(goldType2);
        goldType2.setId(2L);
        assertThat(goldType1).isNotEqualTo(goldType2);
        goldType1.setId(null);
        assertThat(goldType1).isNotEqualTo(goldType2);
    }
}
