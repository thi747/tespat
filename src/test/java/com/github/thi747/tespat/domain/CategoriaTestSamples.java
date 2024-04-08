package com.github.thi747.tespat.domain;

import java.util.UUID;

public class CategoriaTestSamples {

    public static Categoria getCategoriaSample1() {
        return new Categoria().nome("nome1");
    }

    public static Categoria getCategoriaSample2() {
        return new Categoria().nome("nome2");
    }

    public static Categoria getCategoriaRandomSampleGenerator() {
        return new Categoria().nome(UUID.randomUUID().toString());
    }
}
