package com.devgroup.jewelsys.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.devgroup.jewelsys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GoldItemDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GoldItemDTO.class);
        GoldItemDTO goldItemDTO1 = new GoldItemDTO();
        goldItemDTO1.setId(1L);
        GoldItemDTO goldItemDTO2 = new GoldItemDTO();
        assertThat(goldItemDTO1).isNotEqualTo(goldItemDTO2);
        goldItemDTO2.setId(goldItemDTO1.getId());
        assertThat(goldItemDTO1).isEqualTo(goldItemDTO2);
        goldItemDTO2.setId(2L);
        assertThat(goldItemDTO1).isNotEqualTo(goldItemDTO2);
        goldItemDTO1.setId(null);
        assertThat(goldItemDTO1).isNotEqualTo(goldItemDTO2);
    }
}
