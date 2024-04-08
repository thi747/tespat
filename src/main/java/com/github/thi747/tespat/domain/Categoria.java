package com.github.thi747.tespat.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.domain.Persistable;

/**
 * A Categoria.
 */
@Entity
@Table(name = "categoria")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "new", "id" })
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Categoria implements Serializable, Persistable<String> {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Id
    @Column(name = "nome", nullable = false)
    private String nome;

    @Transient
    private boolean isPersisted;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "categoria")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "categoria", "fornecedor", "local", "movimentacaos" }, allowSetters = true)
    private Set<Bem> bems = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getNome() {
        return this.nome;
    }

    public Categoria nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public Categoria setIsPersisted() {
        this.isPersisted = true;
        return this;
    }

    public Set<Bem> getBems() {
        return this.bems;
    }

    public void setBems(Set<Bem> bems) {
        if (this.bems != null) {
            this.bems.forEach(i -> i.setCategoria(null));
        }
        if (bems != null) {
            bems.forEach(i -> i.setCategoria(this));
        }
        this.bems = bems;
    }

    public Categoria bems(Set<Bem> bems) {
        this.setBems(bems);
        return this;
    }

    public Categoria addBem(Bem bem) {
        this.bems.add(bem);
        bem.setCategoria(this);
        return this;
    }

    public Categoria removeBem(Bem bem) {
        this.bems.remove(bem);
        bem.setCategoria(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Categoria)) {
            return false;
        }
        return getNome() != null && getNome().equals(((Categoria) o).getNome());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getNome());
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Categoria{" +
            "nome=" + getNome() +
            "}";
    }
}
