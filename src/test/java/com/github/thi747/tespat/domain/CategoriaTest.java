package com.github.thi747.tespat.domain;

import static com.github.thi747.tespat.domain.BemTestSamples.*;
import static com.github.thi747.tespat.domain.CategoriaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.github.thi747.tespat.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class CategoriaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Categoria.class);
        Categoria categoria1 = getCategoriaSample1();
        Categoria categoria2 = new Categoria();
        assertThat(categoria1).isNotEqualTo(categoria2);

        categoria2.setId(categoria1.getId());
        assertThat(categoria1).isEqualTo(categoria2);

        categoria2 = getCategoriaSample2();
        assertThat(categoria1).isNotEqualTo(categoria2);
    }

    @Test
    void nomeTest() throws Exception {
        Categoria categoria = getCategoriaRandomSampleGenerator();
        Bem bemBack = getBemRandomSampleGenerator();

        categoria.addNome(bemBack);
        assertThat(categoria.getNomes()).containsOnly(bemBack);
        assertThat(bemBack.getCategoria()).isEqualTo(categoria);

        categoria.removeNome(bemBack);
        assertThat(categoria.getNomes()).doesNotContain(bemBack);
        assertThat(bemBack.getCategoria()).isNull();

        categoria.nomes(new HashSet<>(Set.of(bemBack)));
        assertThat(categoria.getNomes()).containsOnly(bemBack);
        assertThat(bemBack.getCategoria()).isEqualTo(categoria);

        categoria.setNomes(new HashSet<>());
        assertThat(categoria.getNomes()).doesNotContain(bemBack);
        assertThat(bemBack.getCategoria()).isNull();
    }
}
