package com.github.thi747.tespat.domain;

import com.github.thi747.tespat.domain.enumeration.TipoConservacao;
import com.github.thi747.tespat.domain.enumeration.TipoStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Bem.
 */
@Entity
@Table(name = "bem")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Bem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "patrimonio", nullable = false, unique = true)
    private Long patrimonio;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "numero_de_serie")
    private String numeroDeSerie;

    @Column(name = "data_aquisicao")
    private LocalDate dataAquisicao;

    @Column(name = "valor_compra")
    private Double valorCompra;

    @Column(name = "valor_atual")
    private Double valorAtual;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private TipoConservacao estado;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TipoStatus status;

    @Column(name = "observacoes")
    private String observacoes;

    @ManyToOne(fetch = FetchType.LAZY)
    private Categoria categoria;

    @ManyToOne(fetch = FetchType.LAZY)
    private Fornecedor fornecedor;

    @ManyToOne(fetch = FetchType.LAZY)
    private Local local;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Bem id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPatrimonio() {
        return this.patrimonio;
    }

    public Bem patrimonio(Long patrimonio) {
        this.setPatrimonio(patrimonio);
        return this;
    }

    public void setPatrimonio(Long patrimonio) {
        this.patrimonio = patrimonio;
    }

    public String getNome() {
        return this.nome;
    }

    public Bem nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public Bem descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getNumeroDeSerie() {
        return this.numeroDeSerie;
    }

    public Bem numeroDeSerie(String numeroDeSerie) {
        this.setNumeroDeSerie(numeroDeSerie);
        return this;
    }

    public void setNumeroDeSerie(String numeroDeSerie) {
        this.numeroDeSerie = numeroDeSerie;
    }

    public LocalDate getDataAquisicao() {
        return this.dataAquisicao;
    }

    public Bem dataAquisicao(LocalDate dataAquisicao) {
        this.setDataAquisicao(dataAquisicao);
        return this;
    }

    public void setDataAquisicao(LocalDate dataAquisicao) {
        this.dataAquisicao = dataAquisicao;
    }

    public Double getValorCompra() {
        return this.valorCompra;
    }

    public Bem valorCompra(Double valorCompra) {
        this.setValorCompra(valorCompra);
        return this;
    }

    public void setValorCompra(Double valorCompra) {
        this.valorCompra = valorCompra;
    }

    public Double getValorAtual() {
        return this.valorAtual;
    }

    public Bem valorAtual(Double valorAtual) {
        this.setValorAtual(valorAtual);
        return this;
    }

    public void setValorAtual(Double valorAtual) {
        this.valorAtual = valorAtual;
    }

    public TipoConservacao getEstado() {
        return this.estado;
    }

    public Bem estado(TipoConservacao estado) {
        this.setEstado(estado);
        return this;
    }

    public void setEstado(TipoConservacao estado) {
        this.estado = estado;
    }

    public TipoStatus getStatus() {
        return this.status;
    }

    public Bem status(TipoStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(TipoStatus status) {
        this.status = status;
    }

    public String getObservacoes() {
        return this.observacoes;
    }

    public Bem observacoes(String observacoes) {
        this.setObservacoes(observacoes);
        return this;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Categoria getCategoria() {
        return this.categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Bem categoria(Categoria categoria) {
        this.setCategoria(categoria);
        return this;
    }

    public Fornecedor getFornecedor() {
        return this.fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public Bem fornecedor(Fornecedor fornecedor) {
        this.setFornecedor(fornecedor);
        return this;
    }

    public Local getLocal() {
        return this.local;
    }

    public void setLocal(Local local) {
        this.local = local;
    }

    public Bem local(Local local) {
        this.setLocal(local);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Bem)) {
            return false;
        }
        return getId() != null && getId().equals(((Bem) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Bem{" +
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
            "}";
    }
}
