package com.devgroup.jewelsys.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.devgroup.jewelsys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GoldItemGroupDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GoldItemGroupDTO.class);
        GoldItemGroupDTO goldItemGroupDTO1 = new GoldItemGroupDTO();
        goldItemGroupDTO1.setId(1L);
        GoldItemGroupDTO goldItemGroupDTO2 = new GoldItemGroupDTO();
        assertThat(goldItemGroupDTO1).isNotEqualTo(goldItemGroupDTO2);
        goldItemGroupDTO2.setId(goldItemGroupDTO1.getId());
        assertThat(goldItemGroupDTO1).isEqualTo(goldItemGroupDTO2);
        goldItemGroupDTO2.setId(2L);
        assertThat(goldItemGroupDTO1).isNotEqualTo(goldItemGroupDTO2);
        goldItemGroupDTO1.setId(null);
        assertThat(goldItemGroupDTO1).isNotEqualTo(goldItemGroupDTO2);
    }
}
