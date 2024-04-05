package com.github.thi747.tespat.domain;

import java.util.UUID;

public class PessoaTestSamples {

    public static Pessoa getPessoaSample1() {
        return new Pessoa()
            .usuario("usuario1")
            .nome("nome1")
            .cpf("cpf1")
            .email("email1")
            .endereco("endereco1")
            .cidade("cidade1")
            .estado("estado1");
    }

    public static Pessoa getPessoaSample2() {
        return new Pessoa()
            .usuario("usuario2")
            .nome("nome2")
            .cpf("cpf2")
            .email("email2")
            .endereco("endereco2")
            .cidade("cidade2")
            .estado("estado2");
    }

    public static Pessoa getPessoaRandomSampleGenerator() {
        return new Pessoa()
            .usuario(UUID.randomUUID().toString())
            .nome(UUID.randomUUID().toString())
            .cpf(UUID.randomUUID().toString())
            .email(UUID.randomUUID().toString())
            .endereco(UUID.randomUUID().toString())
            .cidade(UUID.randomUUID().toString())
            .estado(UUID.randomUUID().toString());
    }
}
