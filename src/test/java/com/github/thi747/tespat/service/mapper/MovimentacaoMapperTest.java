package com.github.thi747.tespat.service.mapper;

import static com.github.thi747.tespat.domain.MovimentacaoAsserts.*;
import static com.github.thi747.tespat.domain.MovimentacaoTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MovimentacaoMapperTest {

    private MovimentacaoMapper movimentacaoMapper;

    @BeforeEach
    void setUp() {
        movimentacaoMapper = new MovimentacaoMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getMovimentacaoSample1();
        var actual = movimentacaoMapper.toEntity(movimentacaoMapper.toDto(expected));
        assertMovimentacaoAllPropertiesEquals(expected, actual);
    }
}
