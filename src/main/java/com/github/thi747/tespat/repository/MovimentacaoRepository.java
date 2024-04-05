package com.github.thi747.tespat.repository;

import com.github.thi747.tespat.domain.Movimentacao;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Movimentacao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long> {}
