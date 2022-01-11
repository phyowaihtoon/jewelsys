package com.devgroup.jewelsys.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GemsItemMapperTest {

    private GemsItemMapper gemsItemMapper;

    @BeforeEach
    public void setUp() {
        gemsItemMapper = new GemsItemMapperImpl();
    }
}
