package com.github.thi747.tespat.domain;

import static com.github.thi747.tespat.domain.BemTestSamples.*;
import static com.github.thi747.tespat.domain.FornecedorTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.github.thi747.tespat.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class FornecedorTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Fornecedor.class);
        Fornecedor fornecedor1 = getFornecedorSample1();
        Fornecedor fornecedor2 = new Fornecedor();
        assertThat(fornecedor1).isNotEqualTo(fornecedor2);

        fornecedor2.setId(fornecedor1.getId());
        assertThat(fornecedor1).isEqualTo(fornecedor2);

        fornecedor2 = getFornecedorSample2();
        assertThat(fornecedor1).isNotEqualTo(fornecedor2);
    }

    @Test
    void idTest() throws Exception {
        Fornecedor fornecedor = getFornecedorRandomSampleGenerator();
        Bem bemBack = getBemRandomSampleGenerator();

        fornecedor.addId(bemBack);
        assertThat(fornecedor.getIds()).containsOnly(bemBack);
        assertThat(bemBack.getFornecedor()).isEqualTo(fornecedor);

        fornecedor.removeId(bemBack);
        assertThat(fornecedor.getIds()).doesNotContain(bemBack);
        assertThat(bemBack.getFornecedor()).isNull();

        fornecedor.ids(new HashSet<>(Set.of(bemBack)));
        assertThat(fornecedor.getIds()).containsOnly(bemBack);
        assertThat(bemBack.getFornecedor()).isEqualTo(fornecedor);

        fornecedor.setIds(new HashSet<>());
        assertThat(fornecedor.getIds()).doesNotContain(bemBack);
        assertThat(bemBack.getFornecedor()).isNull();
    }
}
