package com.devgroup.jewelsys.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GemsPriceRateMapperTest {

    private GemsPriceRateMapper gemsPriceRateMapper;

    @BeforeEach
    public void setUp() {
        gemsPriceRateMapper = new GemsPriceRateMapperImpl();
    }
}
