package com.github.thi747.tespat.service.mapper;

import static com.github.thi747.tespat.domain.FornecedorAsserts.*;
import static com.github.thi747.tespat.domain.FornecedorTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FornecedorMapperTest {

    private FornecedorMapper fornecedorMapper;

    @BeforeEach
    void setUp() {
        fornecedorMapper = new FornecedorMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getFornecedorSample1();
        var actual = fornecedorMapper.toEntity(fornecedorMapper.toDto(expected));
        assertFornecedorAllPropertiesEquals(expected, actual);
    }
}
