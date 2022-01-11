package com.devgroup.jewelsys.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.devgroup.jewelsys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GoldItemTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GoldItem.class);
        GoldItem goldItem1 = new GoldItem();
        goldItem1.setId(1L);
        GoldItem goldItem2 = new GoldItem();
        goldItem2.setId(goldItem1.getId());
        assertThat(goldItem1).isEqualTo(goldItem2);
        goldItem2.setId(2L);
        assertThat(goldItem1).isNotEqualTo(goldItem2);
        goldItem1.setId(null);
        assertThat(goldItem1).isNotEqualTo(goldItem2);
    }
}
