package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Controles;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Controles entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ControlesRepository extends JpaRepository<Controles, Long> {}
