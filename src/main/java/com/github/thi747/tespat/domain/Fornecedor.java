package com.github.thi747.tespat.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.domain.Persistable;

/**
 * A Fornecedor.
 */
@Entity
@Table(name = "fornecedor")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "new", "id" })
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Fornecedor implements Serializable, Persistable<String> {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Id
    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @NotNull
    @Size(min = 11)
    @Column(name = "cpf_ou_cnpj", nullable = false)
    private String cpfOuCnpj;

    @Column(name = "email")
    private String email;

    @Column(name = "telefone")
    private String telefone;

    @Column(name = "endereco")
    private String endereco;

    @Column(name = "cidade")
    private String cidade;

    @Size(min = 2, max = 2)
    @Column(name = "estado", length = 2)
    private String estado;

    @Transient
    private boolean isPersisted;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "fornecedor")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "categoria", "fornecedor", "local", "movimentacaos" }, allowSetters = true)
    private Set<Bem> bems = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getNome() {
        return this.nome;
    }

    public Fornecedor nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public Fornecedor descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCpfOuCnpj() {
        return this.cpfOuCnpj;
    }

    public Fornecedor cpfOuCnpj(String cpfOuCnpj) {
        this.setCpfOuCnpj(cpfOuCnpj);
        return this;
    }

    public void setCpfOuCnpj(String cpfOuCnpj) {
        this.cpfOuCnpj = cpfOuCnpj;
    }

    public String getEmail() {
        return this.email;
    }

    public Fornecedor email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return this.telefone;
    }

    public Fornecedor telefone(String telefone) {
        this.setTelefone(telefone);
        return this;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return this.endereco;
    }

    public Fornecedor endereco(String endereco) {
        this.setEndereco(endereco);
        return this;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCidade() {
        return this.cidade;
    }

    public Fornecedor cidade(String cidade) {
        this.setCidade(cidade);
        return this;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return this.estado;
    }

    public Fornecedor estado(String estado) {
        this.setEstado(estado);
        return this;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @PostLoad
    @PostPersist
    public void updateEntityState() {
        this.setIsPersisted();
    }

    @Override
    public String getId() {
        return this.nome;
    }

    @Transient
    @Override
    public boolean isNew() {
        return !this.isPersisted;
    }

    public Fornecedor setIsPersisted() {
        this.isPersisted = true;
        return this;
    }

    public Set<Bem> getBems() {
        return this.bems;
    }

    public void setBems(Set<Bem> bems) {
        if (this.bems != null) {
            this.bems.forEach(i -> i.setFornecedor(null));
        }
        if (bems != null) {
            bems.forEach(i -> i.setFornecedor(this));
        }
        this.bems = bems;
    }

    public Fornecedor bems(Set<Bem> bems) {
        this.setBems(bems);
        return this;
    }

    public Fornecedor addBem(Bem bem) {
        this.bems.add(bem);
        bem.setFornecedor(this);
        return this;
    }

    public Fornecedor removeBem(Bem bem) {
        this.bems.remove(bem);
        bem.setFornecedor(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Fornecedor)) {
            return false;
        }
        return getNome() != null && getNome().equals(((Fornecedor) o).getNome());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Fornecedor{" +
            "nome=" + getNome() +
            ", descricao='" + getDescricao() + "'" +
            ", cpfOuCnpj='" + getCpfOuCnpj() + "'" +
            ", email='" + getEmail() + "'" +
            ", telefone='" + getTelefone() + "'" +
            ", endereco='" + getEndereco() + "'" +
            ", cidade='" + getCidade() + "'" +
            ", estado='" + getEstado() + "'" +
            "}";
    }
}
