package com.devgroup.jewelsys.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GoldItemMapperTest {

    private GoldItemMapper goldItemMapper;

    @BeforeEach
    public void setUp() {
        goldItemMapper = new GoldItemMapperImpl();
    }
}
