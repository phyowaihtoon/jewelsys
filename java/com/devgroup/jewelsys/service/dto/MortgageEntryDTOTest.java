package com.devgroup.jewelsys.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.devgroup.jewelsys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MortgageEntryDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MortgageEntryDTO.class);
        MortgageEntryDTO mortgageEntryDTO1 = new MortgageEntryDTO();
        mortgageEntryDTO1.setId(1L);
        MortgageEntryDTO mortgageEntryDTO2 = new MortgageEntryDTO();
        assertThat(mortgageEntryDTO1).isNotEqualTo(mortgageEntryDTO2);
        mortgageEntryDTO2.setId(mortgageEntryDTO1.getId());
        assertThat(mortgageEntryDTO1).isEqualTo(mortgageEntryDTO2);
        mortgageEntryDTO2.setId(2L);
        assertThat(mortgageEntryDTO1).isNotEqualTo(mortgageEntryDTO2);
        mortgageEntryDTO1.setId(null);
        assertThat(mortgageEntryDTO1).isNotEqualTo(mortgageEntryDTO2);
    }
}
