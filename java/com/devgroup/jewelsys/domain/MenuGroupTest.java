package com.devgroup.jewelsys.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.devgroup.jewelsys.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MenuGroupTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MenuGroup.class);
        MenuGroup menuGroup1 = new MenuGroup();
        menuGroup1.setId(1L);
        MenuGroup menuGroup2 = new MenuGroup();
        menuGroup2.setId(menuGroup1.getId());
        assertThat(menuGroup1).isEqualTo(menuGroup2);
        menuGroup2.setId(2L);
        assertThat(menuGroup1).isNotEqualTo(menuGroup2);
        menuGroup1.setId(null);
        assertThat(menuGroup1).isNotEqualTo(menuGroup2);
    }
}
