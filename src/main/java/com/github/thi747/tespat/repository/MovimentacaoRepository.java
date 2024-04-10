package com.github.thi747.tespat.repository;

import com.github.thi747.tespat.domain.Movimentacao;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Movimentacao entity.
 */
@Repository
public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long> {
    default Optional<Movimentacao> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Movimentacao> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Movimentacao> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select movimentacao from Movimentacao movimentacao left join fetch movimentacao.bem left join fetch movimentacao.local left join fetch movimentacao.pessoa",
        countQuery = "select count(movimentacao) from Movimentacao movimentacao"
    )
    Page<Movimentacao> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select movimentacao from Movimentacao movimentacao left join fetch movimentacao.bem left join fetch movimentacao.local left join fetch movimentacao.pessoa"
    )
    List<Movimentacao> findAllWithToOneRelationships();

    @Query(
        "select movimentacao from Movimentacao movimentacao left join fetch movimentacao.bem left join fetch movimentacao.local left join fetch movimentacao.pessoa where movimentacao.id =:id"
    )
    Optional<Movimentacao> findOneWithToOneRelationships(@Param("id") Long id);
}
