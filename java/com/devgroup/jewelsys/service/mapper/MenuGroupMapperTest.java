package com.devgroup.jewelsys.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MenuGroupMapperTest {

    private MenuGroupMapper menuGroupMapper;

    @BeforeEach
    public void setUp() {
        menuGroupMapper = new MenuGroupMapperImpl();
    }
}
