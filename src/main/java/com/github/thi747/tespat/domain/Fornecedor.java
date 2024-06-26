package com.github.thi747.tespat.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Fornecedor.
 */
@Entity
@Table(name = "fornecedor")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Fornecedor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false, unique = true)
    private String nome;

    @NotNull
    @Size(min = 11)
    @Column(name = "cpf_ou_cnpj", nullable = false, unique = true)
    private String cpfOuCnpj;

    @Column(name = "email")
    private String email;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "telefone")
    private String telefone;

    @Column(name = "endereco")
    private String endereco;

    @Column(name = "municipio")
    private String municipio;

    @Size(min = 2, max = 2)
    @Column(name = "uf", length = 2)
    private String uf;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Fornecedor id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getMunicipio() {
        return this.municipio;
    }

    public Fornecedor municipio(String municipio) {
        this.setMunicipio(municipio);
        return this;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getUf() {
        return this.uf;
    }

    public Fornecedor uf(String uf) {
        this.setUf(uf);
        return this;
    }

    public void setUf(String uf) {
        this.uf = uf;
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
        return getId() != null && getId().equals(((Fornecedor) o).getId());
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
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", cpfOuCnpj='" + getCpfOuCnpj() + "'" +
            ", email='" + getEmail() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", telefone='" + getTelefone() + "'" +
            ", endereco='" + getEndereco() + "'" +
            ", municipio='" + getMunicipio() + "'" +
            ", uf='" + getUf() + "'" +
            "}";
    }
}
