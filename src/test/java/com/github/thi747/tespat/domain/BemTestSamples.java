package com.github.thi747.tespat.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class BemTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Bem getBemSample1() {
        return new Bem()
            .id(1L)
            .patrimonio(1L)
            .nome("nome1")
            .descricao("descricao1")
            .numeroDeSerie("numeroDeSerie1")
            .observacoes("observacoes1");
    }

    public static Bem getBemSample2() {
        return new Bem()
            .id(2L)
            .patrimonio(2L)
            .nome("nome2")
            .descricao("descricao2")
            .numeroDeSerie("numeroDeSerie2")
            .observacoes("observacoes2");
    }

    public static Bem getBemRandomSampleGenerator() {
        return new Bem()
            .id(longCount.incrementAndGet())
            .patrimonio(longCount.incrementAndGet())
            .nome(UUID.randomUUID().toString())
            .descricao(UUID.randomUUID().toString())
            .numeroDeSerie(UUID.randomUUID().toString())
            .observacoes(UUID.randomUUID().toString());
    }
}
