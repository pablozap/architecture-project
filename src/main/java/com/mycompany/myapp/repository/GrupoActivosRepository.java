package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.GrupoActivos;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the GrupoActivos entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GrupoActivosRepository extends JpaRepository<GrupoActivos, Long> {}
