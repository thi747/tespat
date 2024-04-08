package com.github.thi747.tespat.domain;

import static com.github.thi747.tespat.domain.MovimentacaoTestSamples.*;
import static com.github.thi747.tespat.domain.PessoaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.github.thi747.tespat.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class PessoaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pessoa.class);
        Pessoa pessoa1 = getPessoaSample1();
        Pessoa pessoa2 = new Pessoa();
        assertThat(pessoa1).isNotEqualTo(pessoa2);

        pessoa2.setUsuario(pessoa1.getUsuario());
        assertThat(pessoa1).isEqualTo(pessoa2);

        pessoa2 = getPessoaSample2();
        assertThat(pessoa1).isNotEqualTo(pessoa2);
    }

    @Test
    void movimentacaoTest() throws Exception {
        Pessoa pessoa = getPessoaRandomSampleGenerator();
        Movimentacao movimentacaoBack = getMovimentacaoRandomSampleGenerator();

        pessoa.addMovimentacao(movimentacaoBack);
        assertThat(pessoa.getMovimentacaos()).containsOnly(movimentacaoBack);
        assertThat(movimentacaoBack.getPessoa()).isEqualTo(pessoa);

        pessoa.removeMovimentacao(movimentacaoBack);
        assertThat(pessoa.getMovimentacaos()).doesNotContain(movimentacaoBack);
        assertThat(movimentacaoBack.getPessoa()).isNull();

        pessoa.movimentacaos(new HashSet<>(Set.of(movimentacaoBack)));
        assertThat(pessoa.getMovimentacaos()).containsOnly(movimentacaoBack);
        assertThat(movimentacaoBack.getPessoa()).isEqualTo(pessoa);

        pessoa.setMovimentacaos(new HashSet<>());
        assertThat(pessoa.getMovimentacaos()).doesNotContain(movimentacaoBack);
        assertThat(movimentacaoBack.getPessoa()).isNull();
    }
}
