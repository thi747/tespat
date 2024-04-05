package com.github.thi747.tespat.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class MovimentacaoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Movimentacao getMovimentacaoSample1() {
        return new Movimentacao().id(1L).descricao("descricao1");
    }

    public static Movimentacao getMovimentacaoSample2() {
        return new Movimentacao().id(2L).descricao("descricao2");
    }

    public static Movimentacao getMovimentacaoRandomSampleGenerator() {
        return new Movimentacao().id(longCount.incrementAndGet()).descricao(UUID.randomUUID().toString());
    }
}
