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
    void usuarioTest() throws Exception {
        Pessoa pessoa = getPessoaRandomSampleGenerator();
        Movimentacao movimentacaoBack = getMovimentacaoRandomSampleGenerator();

        pessoa.addUsuario(movimentacaoBack);
        assertThat(pessoa.getUsuarios()).containsOnly(movimentacaoBack);
        assertThat(movimentacaoBack.getPessoa()).isEqualTo(pessoa);

        pessoa.removeUsuario(movimentacaoBack);
        assertThat(pessoa.getUsuarios()).doesNotContain(movimentacaoBack);
        assertThat(movimentacaoBack.getPessoa()).isNull();

        pessoa.usuarios(new HashSet<>(Set.of(movimentacaoBack)));
        assertThat(pessoa.getUsuarios()).containsOnly(movimentacaoBack);
        assertThat(movimentacaoBack.getPessoa()).isEqualTo(pessoa);

        pessoa.setUsuarios(new HashSet<>());
        assertThat(pessoa.getUsuarios()).doesNotContain(movimentacaoBack);
        assertThat(movimentacaoBack.getPessoa()).isNull();
    }
}
