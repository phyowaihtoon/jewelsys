package com.devgroup.jewelsys.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.devgroup.jewelsys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MortgageItemDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MortgageItemDTO.class);
        MortgageItemDTO mortgageItemDTO1 = new MortgageItemDTO();
        mortgageItemDTO1.setId(1L);
        MortgageItemDTO mortgageItemDTO2 = new MortgageItemDTO();
        assertThat(mortgageItemDTO1).isNotEqualTo(mortgageItemDTO2);
        mortgageItemDTO2.setId(mortgageItemDTO1.getId());
        assertThat(mortgageItemDTO1).isEqualTo(mortgageItemDTO2);
        mortgageItemDTO2.setId(2L);
        assertThat(mortgageItemDTO1).isNotEqualTo(mortgageItemDTO2);
        mortgageItemDTO1.setId(null);
        assertThat(mortgageItemDTO1).isNotEqualTo(mortgageItemDTO2);
    }
}
