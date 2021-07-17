package com.devgroup.jewelsys.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GoldTypeMapperTest {

    private GoldTypeMapper goldTypeMapper;

    @BeforeEach
    public void setUp() {
        goldTypeMapper = new GoldTypeMapperImpl();
    }
}
