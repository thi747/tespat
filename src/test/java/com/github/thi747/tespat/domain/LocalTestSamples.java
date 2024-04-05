package com.github.thi747.tespat.domain;

import java.util.UUID;

public class LocalTestSamples {

    public static Local getLocalSample1() {
        return new Local().nome("nome1").descricao("descricao1").sala("sala1");
    }

    public static Local getLocalSample2() {
        return new Local().nome("nome2").descricao("descricao2").sala("sala2");
    }

    public static Local getLocalRandomSampleGenerator() {
        return new Local().nome(UUID.randomUUID().toString()).descricao(UUID.randomUUID().toString()).sala(UUID.randomUUID().toString());
    }
}
