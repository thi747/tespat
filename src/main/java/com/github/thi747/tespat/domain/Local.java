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
 * A Local.
 */
@Entity
@Table(name = "local")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "new", "id" })
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Local implements Serializable, Persistable<String> {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Id
    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "sala")
    private String sala;

    @Transient
    private boolean isPersisted;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "local")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "categoria", "fornecedor", "local", "movimentacaos" }, allowSetters = true)
    private Set<Bem> bems = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getNome() {
        return this.nome;
    }

    public Local nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public Local descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getSala() {
        return this.sala;
    }

    public Local sala(String sala) {
        this.setSala(sala);
        return this;
    }

    public void setSala(String sala) {
        this.sala = sala;
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

    public Local setIsPersisted() {
        this.isPersisted = true;
        return this;
    }

    public Set<Bem> getBems() {
        return this.bems;
    }

    public void setBems(Set<Bem> bems) {
        if (this.bems != null) {
            this.bems.forEach(i -> i.setLocal(null));
        }
        if (bems != null) {
            bems.forEach(i -> i.setLocal(this));
        }
        this.bems = bems;
    }

    public Local bems(Set<Bem> bems) {
        this.setBems(bems);
        return this;
    }

    public Local addBem(Bem bem) {
        this.bems.add(bem);
        bem.setLocal(this);
        return this;
    }

    public Local removeBem(Bem bem) {
        this.bems.remove(bem);
        bem.setLocal(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Local)) {
            return false;
        }
        return getNome() != null && getNome().equals(((Local) o).getNome());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Local{" +
            "nome=" + getNome() +
            ", descricao='" + getDescricao() + "'" +
            ", sala='" + getSala() + "'" +
            "}";
    }
}
