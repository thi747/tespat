package com.github.thi747.tespat.repository;

import com.github.thi747.tespat.domain.Bem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Bem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BemRepository extends JpaRepository<Bem, Long> {}
