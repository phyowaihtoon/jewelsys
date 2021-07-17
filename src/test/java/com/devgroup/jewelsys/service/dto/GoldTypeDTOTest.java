package com.devgroup.jewelsys.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.devgroup.jewelsys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GoldTypeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GoldTypeDTO.class);
        GoldTypeDTO goldTypeDTO1 = new GoldTypeDTO();
        goldTypeDTO1.setId(1L);
        GoldTypeDTO goldTypeDTO2 = new GoldTypeDTO();
        assertThat(goldTypeDTO1).isNotEqualTo(goldTypeDTO2);
        goldTypeDTO2.setId(goldTypeDTO1.getId());
        assertThat(goldTypeDTO1).isEqualTo(goldTypeDTO2);
        goldTypeDTO2.setId(2L);
        assertThat(goldTypeDTO1).isNotEqualTo(goldTypeDTO2);
        goldTypeDTO1.setId(null);
        assertThat(goldTypeDTO1).isNotEqualTo(goldTypeDTO2);
    }
}
