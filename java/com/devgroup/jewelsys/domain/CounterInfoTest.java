package com.devgroup.jewelsys.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.devgroup.jewelsys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CounterInfoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CounterInfo.class);
        CounterInfo counterInfo1 = new CounterInfo();
        counterInfo1.setId(1L);
        CounterInfo counterInfo2 = new CounterInfo();
        counterInfo2.setId(counterInfo1.getId());
        assertThat(counterInfo1).isEqualTo(counterInfo2);
        counterInfo2.setId(2L);
        assertThat(counterInfo1).isNotEqualTo(counterInfo2);
        counterInfo1.setId(null);
        assertThat(counterInfo1).isNotEqualTo(counterInfo2);
    }
}
