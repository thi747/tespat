package com.github.thi747.tespat.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class LocalTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Local getLocalSample1() {
        return new Local().id(1L).nome("nome1").descricao("descricao1").sala("sala1");
    }

    public static Local getLocalSample2() {
        return new Local().id(2L).nome("nome2").descricao("descricao2").sala("sala2");
    }

    public static Local getLocalRandomSampleGenerator() {
        return new Local()
            .id(longCount.incrementAndGet())
            .nome(UUID.randomUUID().toString())
            .descricao(UUID.randomUUID().toString())
            .sala(UUID.randomUUID().toString());
    }
}
