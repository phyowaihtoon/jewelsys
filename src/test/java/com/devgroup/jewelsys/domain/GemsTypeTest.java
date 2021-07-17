package com.devgroup.jewelsys.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.devgroup.jewelsys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GemsTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GemsType.class);
        GemsType gemsType1 = new GemsType();
        gemsType1.setId(1L);
        GemsType gemsType2 = new GemsType();
        gemsType2.setId(gemsType1.getId());
        assertThat(gemsType1).isEqualTo(gemsType2);
        gemsType2.setId(2L);
        assertThat(gemsType1).isNotEqualTo(gemsType2);
        gemsType1.setId(null);
        assertThat(gemsType1).isNotEqualTo(gemsType2);
    }
}
