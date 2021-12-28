package com.devgroup.jewelsys.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.devgroup.jewelsys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CounterInfoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CounterInfoDTO.class);
        CounterInfoDTO counterInfoDTO1 = new CounterInfoDTO();
        counterInfoDTO1.setId(1L);
        CounterInfoDTO counterInfoDTO2 = new CounterInfoDTO();
        assertThat(counterInfoDTO1).isNotEqualTo(counterInfoDTO2);
        counterInfoDTO2.setId(counterInfoDTO1.getId());
        assertThat(counterInfoDTO1).isEqualTo(counterInfoDTO2);
        counterInfoDTO2.setId(2L);
        assertThat(counterInfoDTO1).isNotEqualTo(counterInfoDTO2);
        counterInfoDTO1.setId(null);
        assertThat(counterInfoDTO1).isNotEqualTo(counterInfoDTO2);
    }
}
