package com.github.thi747.tespat.service.mapper;

import static com.github.thi747.tespat.domain.BemAsserts.*;
import static com.github.thi747.tespat.domain.BemTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BemMapperTest {

    private BemMapper bemMapper;

    @BeforeEach
    void setUp() {
        bemMapper = new BemMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getBemSample1();
        var actual = bemMapper.toEntity(bemMapper.toDto(expected));
        assertBemAllPropertiesEquals(expected, actual);
    }
}
