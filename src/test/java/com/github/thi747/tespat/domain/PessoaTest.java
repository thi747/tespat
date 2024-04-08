package com.github.thi747.tespat.domain;

import static com.github.thi747.tespat.domain.PessoaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.github.thi747.tespat.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PessoaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pessoa.class);
        Pessoa pessoa1 = getPessoaSample1();
        Pessoa pessoa2 = new Pessoa();
        assertThat(pessoa1).isNotEqualTo(pessoa2);

        pessoa2.setId(pessoa1.getId());
        assertThat(pessoa1).isEqualTo(pessoa2);

        pessoa2 = getPessoaSample2();
        assertThat(pessoa1).isNotEqualTo(pessoa2);
    }
}
