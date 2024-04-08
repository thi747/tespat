package com.github.thi747.tespat.domain;

import static com.github.thi747.tespat.domain.BemTestSamples.*;
import static com.github.thi747.tespat.domain.CategoriaTestSamples.*;
import static com.github.thi747.tespat.domain.FornecedorTestSamples.*;
import static com.github.thi747.tespat.domain.LocalTestSamples.*;
import static com.github.thi747.tespat.domain.MovimentacaoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.github.thi747.tespat.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class BemTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bem.class);
        Bem bem1 = getBemSample1();
        Bem bem2 = new Bem();
        assertThat(bem1).isNotEqualTo(bem2);

        bem2.setPatrimonio(bem1.getPatrimonio());
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

    @Test
    void movimentacaoTest() throws Exception {
        Bem bem = getBemRandomSampleGenerator();
        Movimentacao movimentacaoBack = getMovimentacaoRandomSampleGenerator();

        bem.addMovimentacao(movimentacaoBack);
        assertThat(bem.getMovimentacaos()).containsOnly(movimentacaoBack);
        assertThat(movimentacaoBack.getBem()).isEqualTo(bem);

        bem.removeMovimentacao(movimentacaoBack);
        assertThat(bem.getMovimentacaos()).doesNotContain(movimentacaoBack);
        assertThat(movimentacaoBack.getBem()).isNull();

        bem.movimentacaos(new HashSet<>(Set.of(movimentacaoBack)));
        assertThat(bem.getMovimentacaos()).containsOnly(movimentacaoBack);
        assertThat(movimentacaoBack.getBem()).isEqualTo(bem);

        bem.setMovimentacaos(new HashSet<>());
        assertThat(bem.getMovimentacaos()).doesNotContain(movimentacaoBack);
        assertThat(movimentacaoBack.getBem()).isNull();
    }
}
