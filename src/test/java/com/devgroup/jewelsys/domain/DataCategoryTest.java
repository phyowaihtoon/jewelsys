package com.devgroup.jewelsys.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.devgroup.jewelsys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DataCategoryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DataCategory.class);
        DataCategory dataCategory1 = new DataCategory();
        dataCategory1.setId(1L);
        DataCategory dataCategory2 = new DataCategory();
        dataCategory2.setId(dataCategory1.getId());
        assertThat(dataCategory1).isEqualTo(dataCategory2);
        dataCategory2.setId(2L);
        assertThat(dataCategory1).isNotEqualTo(dataCategory2);
        dataCategory1.setId(null);
        assertThat(dataCategory1).isNotEqualTo(dataCategory2);
    }
}
