package com.github.thi747.tespat.service.dto;

import com.github.thi747.tespat.domain.enumeration.TipoConservacao;
import com.github.thi747.tespat.domain.enumeration.TipoStatus;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.github.thi747.tespat.domain.Bem} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BemDTO implements Serializable {

    private Long id;

    @NotNull
    private Long patrimonio;

    @NotNull
    private String nome;

    private String descricao;

    private String numeroDeSerie;

    private LocalDate dataAquisicao;

    private Double valorCompra;

    private Double valorAtual;

    private TipoConservacao estado;

    private TipoStatus status;

    private String observacoes;

    private CategoriaDTO categoria;

    private FornecedorDTO fornecedor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPatrimonio() {
        return patrimonio;
    }

    public void setPatrimonio(Long patrimonio) {
        this.patrimonio = patrimonio;
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

    public String getNumeroDeSerie() {
        return numeroDeSerie;
    }

    public void setNumeroDeSerie(String numeroDeSerie) {
        this.numeroDeSerie = numeroDeSerie;
    }

    public LocalDate getDataAquisicao() {
        return dataAquisicao;
    }

    public void setDataAquisicao(LocalDate dataAquisicao) {
        this.dataAquisicao = dataAquisicao;
    }

    public Double getValorCompra() {
        return valorCompra;
    }

    public void setValorCompra(Double valorCompra) {
        this.valorCompra = valorCompra;
    }

    public Double getValorAtual() {
        return valorAtual;
    }

    public void setValorAtual(Double valorAtual) {
        this.valorAtual = valorAtual;
    }

    public TipoConservacao getEstado() {
        return estado;
    }

    public void setEstado(TipoConservacao estado) {
        this.estado = estado;
    }

    public TipoStatus getStatus() {
        return status;
    }

    public void setStatus(TipoStatus status) {
        this.status = status;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public CategoriaDTO getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaDTO categoria) {
        this.categoria = categoria;
    }

    public FornecedorDTO getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(FornecedorDTO fornecedor) {
        this.fornecedor = fornecedor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BemDTO)) {
            return false;
        }

        BemDTO bemDTO = (BemDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, bemDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BemDTO{" +
            "id=" + getId() +
            ", patrimonio=" + getPatrimonio() +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", numeroDeSerie='" + getNumeroDeSerie() + "'" +
            ", dataAquisicao='" + getDataAquisicao() + "'" +
            ", valorCompra=" + getValorCompra() +
            ", valorAtual=" + getValorAtual() +
            ", estado='" + getEstado() + "'" +
            ", status='" + getStatus() + "'" +
            ", observacoes='" + getObservacoes() + "'" +
            ", categoria=" + getCategoria() +
            ", fornecedor=" + getFornecedor() +
            "}";
    }
}
