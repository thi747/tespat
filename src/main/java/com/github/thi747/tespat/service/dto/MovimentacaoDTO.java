package com.github.thi747.tespat.service.dto;

import com.github.thi747.tespat.domain.enumeration.TipoMovimentacao;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.github.thi747.tespat.domain.Movimentacao} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MovimentacaoDTO implements Serializable {

    private Long id;

    private LocalDate data;

    private String descricao;

    @NotNull
    private TipoMovimentacao tipo;

    @NotNull
    private BemDTO bem;

    @NotNull
    private PessoaDTO pessoa;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public TipoMovimentacao getTipo() {
        return tipo;
    }

    public void setTipo(TipoMovimentacao tipo) {
        this.tipo = tipo;
    }

    public BemDTO getBem() {
        return bem;
    }

    public void setBem(BemDTO bem) {
        this.bem = bem;
    }

    public PessoaDTO getPessoa() {
        return pessoa;
    }

    public void setPessoa(PessoaDTO pessoa) {
        this.pessoa = pessoa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MovimentacaoDTO)) {
            return false;
        }

        MovimentacaoDTO movimentacaoDTO = (MovimentacaoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, movimentacaoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MovimentacaoDTO{" +
            "id=" + getId() +
            ", data='" + getData() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", tipo='" + getTipo() + "'" +
            ", bem=" + getBem() +
            ", pessoa=" + getPessoa() +
            "}";
    }
}
