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

        categoria2.setNome(categoria1.getNome());
        assertThat(categoria1).isEqualTo(categoria2);

        categoria2 = getCategoriaSample2();
        assertThat(categoria1).isNotEqualTo(categoria2);
    }

    @Test
    void hashCodeVerifier() throws Exception {
        Categoria categoria = new Categoria();
        assertThat(categoria.hashCode()).isZero();

        Categoria categoria1 = getCategoriaSample1();
        categoria.setNome(categoria1.getNome());
        assertThat(categoria).hasSameHashCodeAs(categoria1);
    }

    @Test
    void bemTest() throws Exception {
        Categoria categoria = getCategoriaRandomSampleGenerator();
        Bem bemBack = getBemRandomSampleGenerator();

        categoria.addBem(bemBack);
        assertThat(categoria.getBems()).containsOnly(bemBack);
        assertThat(bemBack.getCategoria()).isEqualTo(categoria);

        categoria.removeBem(bemBack);
        assertThat(categoria.getBems()).doesNotContain(bemBack);
        assertThat(bemBack.getCategoria()).isNull();

        categoria.bems(new HashSet<>(Set.of(bemBack)));
        assertThat(categoria.getBems()).containsOnly(bemBack);
        assertThat(bemBack.getCategoria()).isEqualTo(categoria);

        categoria.setBems(new HashSet<>());
        assertThat(categoria.getBems()).doesNotContain(bemBack);
        assertThat(bemBack.getCategoria()).isNull();
    }
}
