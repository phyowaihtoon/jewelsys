package com.devgroup.jewelsys.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopInfoMapperTest {

    private ShopInfoMapper shopInfoMapper;

    @BeforeEach
    public void setUp() {
        shopInfoMapper = new ShopInfoMapperImpl();
    }
}
