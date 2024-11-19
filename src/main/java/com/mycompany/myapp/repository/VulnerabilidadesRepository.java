package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Vulnerabilidades;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Vulnerabilidades entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VulnerabilidadesRepository extends JpaRepository<Vulnerabilidades, Long> {}
