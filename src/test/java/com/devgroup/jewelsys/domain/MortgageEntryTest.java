package com.devgroup.jewelsys.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.devgroup.jewelsys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MortgageEntryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MortgageEntry.class);
        MortgageEntry mortgageEntry1 = new MortgageEntry();
        mortgageEntry1.setId(1L);
        MortgageEntry mortgageEntry2 = new MortgageEntry();
        mortgageEntry2.setId(mortgageEntry1.getId());
        assertThat(mortgageEntry1).isEqualTo(mortgageEntry2);
        mortgageEntry2.setId(2L);
        assertThat(mortgageEntry1).isNotEqualTo(mortgageEntry2);
        mortgageEntry1.setId(null);
        assertThat(mortgageEntry1).isNotEqualTo(mortgageEntry2);
    }
}
