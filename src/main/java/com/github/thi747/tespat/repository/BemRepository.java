package com.github.thi747.tespat.repository;

import com.github.thi747.tespat.domain.Bem;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Bem entity.
 */
@Repository
public interface BemRepository extends JpaRepository<Bem, Long> {
    default Optional<Bem> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Bem> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Bem> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select bem from Bem bem left join fetch bem.categoria left join fetch bem.fornecedor",
        countQuery = "select count(bem) from Bem bem"
    )
    Page<Bem> findAllWithToOneRelationships(Pageable pageable);

    @Query("select bem from Bem bem left join fetch bem.categoria left join fetch bem.fornecedor")
    List<Bem> findAllWithToOneRelationships();

    @Query("select bem from Bem bem left join fetch bem.categoria left join fetch bem.fornecedor where bem.id =:id")
    Optional<Bem> findOneWithToOneRelationships(@Param("id") Long id);
}
