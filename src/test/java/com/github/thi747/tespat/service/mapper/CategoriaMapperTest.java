package com.github.thi747.tespat.service.mapper;

import static com.github.thi747.tespat.domain.CategoriaAsserts.*;
import static com.github.thi747.tespat.domain.CategoriaTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CategoriaMapperTest {

    private CategoriaMapper categoriaMapper;

    @BeforeEach
    void setUp() {
        categoriaMapper = new CategoriaMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getCategoriaSample1();
        var actual = categoriaMapper.toEntity(categoriaMapper.toDto(expected));
        assertCategoriaAllPropertiesEquals(expected, actual);
    }
}
