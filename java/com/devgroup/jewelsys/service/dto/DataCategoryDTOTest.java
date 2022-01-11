package com.devgroup.jewelsys.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.devgroup.jewelsys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DataCategoryDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DataCategoryDTO.class);
        DataCategoryDTO dataCategoryDTO1 = new DataCategoryDTO();
        dataCategoryDTO1.setId(1L);
        DataCategoryDTO dataCategoryDTO2 = new DataCategoryDTO();
        assertThat(dataCategoryDTO1).isNotEqualTo(dataCategoryDTO2);
        dataCategoryDTO2.setId(dataCategoryDTO1.getId());
        assertThat(dataCategoryDTO1).isEqualTo(dataCategoryDTO2);
        dataCategoryDTO2.setId(2L);
        assertThat(dataCategoryDTO1).isNotEqualTo(dataCategoryDTO2);
        dataCategoryDTO1.setId(null);
        assertThat(dataCategoryDTO1).isNotEqualTo(dataCategoryDTO2);
    }
}
