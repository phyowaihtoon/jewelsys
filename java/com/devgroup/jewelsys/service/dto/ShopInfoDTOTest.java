package com.devgroup.jewelsys.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.devgroup.jewelsys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ShopInfoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShopInfoDTO.class);
        ShopInfoDTO shopInfoDTO1 = new ShopInfoDTO();
        shopInfoDTO1.setId(1L);
        ShopInfoDTO shopInfoDTO2 = new ShopInfoDTO();
        assertThat(shopInfoDTO1).isNotEqualTo(shopInfoDTO2);
        shopInfoDTO2.setId(shopInfoDTO1.getId());
        assertThat(shopInfoDTO1).isEqualTo(shopInfoDTO2);
        shopInfoDTO2.setId(2L);
        assertThat(shopInfoDTO1).isNotEqualTo(shopInfoDTO2);
        shopInfoDTO1.setId(null);
        assertThat(shopInfoDTO1).isNotEqualTo(shopInfoDTO2);
    }
}
