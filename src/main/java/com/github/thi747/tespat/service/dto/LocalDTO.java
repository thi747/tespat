package com.github.thi747.tespat.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.github.thi747.tespat.domain.Local} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LocalDTO implements Serializable {

    private Long id;

    @NotNull
    private String nome;

    private String descricao;

    private String sala;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getSala() {
        return sala;
    }

    public void setSala(String sala) {
        this.sala = sala;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LocalDTO)) {
            return false;
        }

        LocalDTO localDTO = (LocalDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, localDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LocalDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", sala='" + getSala() + "'" +
            "}";
    }
}
