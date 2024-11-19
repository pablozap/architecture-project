package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Amenazas;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Amenazas entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AmenazasRepository extends JpaRepository<Amenazas, Long> {}
