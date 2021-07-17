package com.devgroup.jewelsys.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GoldPriceRateMapperTest {

    private GoldPriceRateMapper goldPriceRateMapper;

    @BeforeEach
    public void setUp() {
        goldPriceRateMapper = new GoldPriceRateMapperImpl();
    }
}
