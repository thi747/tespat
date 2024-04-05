package com.github.thi747.tespat.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class FornecedorTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Fornecedor getFornecedorSample1() {
        return new Fornecedor()
            .id(1L)
            .nome("nome1")
            .descricao("descricao1")
            .cpfOuCnpj("cpfOuCnpj1")
            .email("email1")
            .telefone("telefone1")
            .endereco("endereco1")
            .cidade("cidade1")
            .estado("estado1");
    }

    public static Fornecedor getFornecedorSample2() {
        return new Fornecedor()
            .id(2L)
            .nome("nome2")
            .descricao("descricao2")
            .cpfOuCnpj("cpfOuCnpj2")
            .email("email2")
            .telefone("telefone2")
            .endereco("endereco2")
            .cidade("cidade2")
            .estado("estado2");
    }

    public static Fornecedor getFornecedorRandomSampleGenerator() {
        return new Fornecedor()
            .id(longCount.incrementAndGet())
            .nome(UUID.randomUUID().toString())
            .descricao(UUID.randomUUID().toString())
            .cpfOuCnpj(UUID.randomUUID().toString())
            .email(UUID.randomUUID().toString())
            .telefone(UUID.randomUUID().toString())
            .endereco(UUID.randomUUID().toString())
            .cidade(UUID.randomUUID().toString())
            .estado(UUID.randomUUID().toString());
    }
}
