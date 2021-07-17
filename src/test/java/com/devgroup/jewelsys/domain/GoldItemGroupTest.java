package com.devgroup.jewelsys.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.devgroup.jewelsys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GoldItemGroupTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GoldItemGroup.class);
        GoldItemGroup goldItemGroup1 = new GoldItemGroup();
        goldItemGroup1.setId(1L);
        GoldItemGroup goldItemGroup2 = new GoldItemGroup();
        goldItemGroup2.setId(goldItemGroup1.getId());
        assertThat(goldItemGroup1).isEqualTo(goldItemGroup2);
        goldItemGroup2.setId(2L);
        assertThat(goldItemGroup1).isNotEqualTo(goldItemGroup2);
        goldItemGroup1.setId(null);
        assertThat(goldItemGroup1).isNotEqualTo(goldItemGroup2);
    }
}
