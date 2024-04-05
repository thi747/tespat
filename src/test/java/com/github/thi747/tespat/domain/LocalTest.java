package com.github.thi747.tespat.domain;

import static com.github.thi747.tespat.domain.BemTestSamples.*;
import static com.github.thi747.tespat.domain.LocalTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.github.thi747.tespat.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class LocalTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Local.class);
        Local local1 = getLocalSample1();
        Local local2 = new Local();
        assertThat(local1).isNotEqualTo(local2);

        local2.setNome(local1.getNome());
        assertThat(local1).isEqualTo(local2);

        local2 = getLocalSample2();
        assertThat(local1).isNotEqualTo(local2);
    }

    @Test
    void nomeTest() throws Exception {
        Local local = getLocalRandomSampleGenerator();
        Bem bemBack = getBemRandomSampleGenerator();

        local.addNome(bemBack);
        assertThat(local.getNomes()).containsOnly(bemBack);
        assertThat(bemBack.getLocal()).isEqualTo(local);

        local.removeNome(bemBack);
        assertThat(local.getNomes()).doesNotContain(bemBack);
        assertThat(bemBack.getLocal()).isNull();

        local.nomes(new HashSet<>(Set.of(bemBack)));
        assertThat(local.getNomes()).containsOnly(bemBack);
        assertThat(bemBack.getLocal()).isEqualTo(local);

        local.setNomes(new HashSet<>());
        assertThat(local.getNomes()).doesNotContain(bemBack);
        assertThat(bemBack.getLocal()).isNull();
    }
}
