package com.devgroup.jewelsys.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GoldItemGroupMapperTest {

    private GoldItemGroupMapper goldItemGroupMapper;

    @BeforeEach
    public void setUp() {
        goldItemGroupMapper = new GoldItemGroupMapperImpl();
    }
}
