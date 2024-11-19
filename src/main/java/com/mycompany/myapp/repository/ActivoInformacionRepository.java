package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ActivoInformacion;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ActivoInformacion entity.
 */
@Repository
public interface ActivoInformacionRepository extends JpaRepository<ActivoInformacion, Long> {
    default Optional<ActivoInformacion> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<ActivoInformacion> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<ActivoInformacion> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select activoInformacion from ActivoInformacion activoInformacion left join fetch activoInformacion.grupoActivo",
        countQuery = "select count(activoInformacion) from ActivoInformacion activoInformacion"
    )
    Page<ActivoInformacion> findAllWithToOneRelationships(Pageable pageable);

    @Query("select activoInformacion from ActivoInformacion activoInformacion left join fetch activoInformacion.grupoActivo")
    List<ActivoInformacion> findAllWithToOneRelationships();

    @Query(
        "select activoInformacion from ActivoInformacion activoInformacion left join fetch activoInformacion.grupoActivo where activoInformacion.id =:id"
    )
    Optional<ActivoInformacion> findOneWithToOneRelationships(@Param("id") Long id);
}
