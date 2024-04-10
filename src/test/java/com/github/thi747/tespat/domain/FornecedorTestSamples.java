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
            .cpfOuCnpj("cpfOuCnpj1")
            .email("email1")
            .descricao("descricao1")
            .telefone("telefone1")
            .endereco("endereco1")
            .municipio("municipio1")
            .uf("uf1");
    }

    public static Fornecedor getFornecedorSample2() {
        return new Fornecedor()
            .id(2L)
            .nome("nome2")
            .cpfOuCnpj("cpfOuCnpj2")
            .email("email2")
            .descricao("descricao2")
            .telefone("telefone2")
            .endereco("endereco2")
            .municipio("municipio2")
            .uf("uf2");
    }

    public static Fornecedor getFornecedorRandomSampleGenerator() {
        return new Fornecedor()
            .id(longCount.incrementAndGet())
            .nome(UUID.randomUUID().toString())
            .cpfOuCnpj(UUID.randomUUID().toString())
            .email(UUID.randomUUID().toString())
            .descricao(UUID.randomUUID().toString())
            .telefone(UUID.randomUUID().toString())
            .endereco(UUID.randomUUID().toString())
            .municipio(UUID.randomUUID().toString())
            .uf(UUID.randomUUID().toString());
    }
}
