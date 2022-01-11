package com.devgroup.jewelsys.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.devgroup.jewelsys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GemsItemTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GemsItem.class);
        GemsItem gemsItem1 = new GemsItem();
        gemsItem1.setId(1L);
        GemsItem gemsItem2 = new GemsItem();
        gemsItem2.setId(gemsItem1.getId());
        assertThat(gemsItem1).isEqualTo(gemsItem2);
        gemsItem2.setId(2L);
        assertThat(gemsItem1).isNotEqualTo(gemsItem2);
        gemsItem1.setId(null);
        assertThat(gemsItem1).isNotEqualTo(gemsItem2);
    }
}
