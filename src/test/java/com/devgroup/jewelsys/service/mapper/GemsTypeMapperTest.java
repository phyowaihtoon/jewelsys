package com.devgroup.jewelsys.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GemsTypeMapperTest {

    private GemsTypeMapper gemsTypeMapper;

    @BeforeEach
    public void setUp() {
        gemsTypeMapper = new GemsTypeMapperImpl();
    }
}
