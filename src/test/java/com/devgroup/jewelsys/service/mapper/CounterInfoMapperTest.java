package com.devgroup.jewelsys.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CounterInfoMapperTest {

    private CounterInfoMapper counterInfoMapper;

    @BeforeEach
    public void setUp() {
        counterInfoMapper = new CounterInfoMapperImpl();
    }
}
