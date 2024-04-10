package com.github.thi747.tespat.domain;

import static com.github.thi747.tespat.domain.BemTestSamples.*;
import static com.github.thi747.tespat.domain.LocalTestSamples.*;
import static com.github.thi747.tespat.domain.MovimentacaoTestSamples.*;
import static com.github.thi747.tespat.domain.PessoaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.github.thi747.tespat.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MovimentacaoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Movimentacao.class);
        Movimentacao movimentacao1 = getMovimentacaoSample1();
        Movimentacao movimentacao2 = new Movimentacao();
        assertThat(movimentacao1).isNotEqualTo(movimentacao2);

        movimentacao2.setId(movimentacao1.getId());
        assertThat(movimentacao1).isEqualTo(movimentacao2);

        movimentacao2 = getMovimentacaoSample2();
        assertThat(movimentacao1).isNotEqualTo(movimentacao2);
    }

    @Test
    void bemTest() throws Exception {
        Movimentacao movimentacao = getMovimentacaoRandomSampleGenerator();
        Bem bemBack = getBemRandomSampleGenerator();

        movimentacao.setBem(bemBack);
        assertThat(movimentacao.getBem()).isEqualTo(bemBack);

        movimentacao.bem(null);
        assertThat(movimentacao.getBem()).isNull();
    }

    @Test
    void localTest() throws Exception {
        Movimentacao movimentacao = getMovimentacaoRandomSampleGenerator();
        Local localBack = getLocalRandomSampleGenerator();

        movimentacao.setLocal(localBack);
        assertThat(movimentacao.getLocal()).isEqualTo(localBack);

        movimentacao.local(null);
        assertThat(movimentacao.getLocal()).isNull();
    }

    @Test
    void pessoaTest() throws Exception {
        Movimentacao movimentacao = getMovimentacaoRandomSampleGenerator();
        Pessoa pessoaBack = getPessoaRandomSampleGenerator();

        movimentacao.setPessoa(pessoaBack);
        assertThat(movimentacao.getPessoa()).isEqualTo(pessoaBack);

        movimentacao.pessoa(null);
        assertThat(movimentacao.getPessoa()).isNull();
    }
}
