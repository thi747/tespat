package com.github.thi747.tespat.domain;

import static com.github.thi747.tespat.domain.BemTestSamples.*;
import static com.github.thi747.tespat.domain.CategoriaTestSamples.*;
import static com.github.thi747.tespat.domain.FornecedorTestSamples.*;
import static com.github.thi747.tespat.domain.LocalTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.github.thi747.tespat.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BemTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bem.class);
        Bem bem1 = getBemSample1();
        Bem bem2 = new Bem();
        assertThat(bem1).isNotEqualTo(bem2);

        bem2.setId(bem1.getId());
        assertThat(bem1).isEqualTo(bem2);

        bem2 = getBemSample2();
        assertThat(bem1).isNotEqualTo(bem2);
    }

    @Test
    void categoriaTest() throws Exception {
        Bem bem = getBemRandomSampleGenerator();
        Categoria categoriaBack = getCategoriaRandomSampleGenerator();

        bem.setCategoria(categoriaBack);
        assertThat(bem.getCategoria()).isEqualTo(categoriaBack);

        bem.categoria(null);
        assertThat(bem.getCategoria()).isNull();
    }

    @Test
    void fornecedorTest() throws Exception {
        Bem bem = getBemRandomSampleGenerator();
        Fornecedor fornecedorBack = getFornecedorRandomSampleGenerator();

        bem.setFornecedor(fornecedorBack);
        assertThat(bem.getFornecedor()).isEqualTo(fornecedorBack);

        bem.fornecedor(null);
        assertThat(bem.getFornecedor()).isNull();
    }

    @Test
    void localTest() throws Exception {
        Bem bem = getBemRandomSampleGenerator();
        Local localBack = getLocalRandomSampleGenerator();

        bem.setLocal(localBack);
        assertThat(bem.getLocal()).isEqualTo(localBack);

        bem.local(null);
        assertThat(bem.getLocal()).isNull();
    }
}
