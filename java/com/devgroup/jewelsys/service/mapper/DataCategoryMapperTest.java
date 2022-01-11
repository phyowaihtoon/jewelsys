package com.devgroup.jewelsys.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataCategoryMapperTest {

    private DataCategoryMapper dataCategoryMapper;

    @BeforeEach
    public void setUp() {
        dataCategoryMapper = new DataCategoryMapperImpl();
    }
}
