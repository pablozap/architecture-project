package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Riesgo;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Riesgo entity.
 *
 * When extending this class, extend RiesgoRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface RiesgoRepository extends RiesgoRepositoryWithBagRelationships, JpaRepository<Riesgo, Long> {
    default Optional<Riesgo> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findOneWithToOneRelationships(id));
    }

    default List<Riesgo> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAllWithToOneRelationships());
    }

    default Page<Riesgo> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAllWithToOneRelationships(pageable));
    }

    @Query(
        value = "select riesgo from Riesgo riesgo left join fetch riesgo.amenaza left join fetch riesgo.vulnerabilidad",
        countQuery = "select count(riesgo) from Riesgo riesgo"
    )
    Page<Riesgo> findAllWithToOneRelationships(Pageable pageable);

    @Query("select riesgo from Riesgo riesgo left join fetch riesgo.amenaza left join fetch riesgo.vulnerabilidad")
    List<Riesgo> findAllWithToOneRelationships();

    @Query("select riesgo from Riesgo riesgo left join fetch riesgo.amenaza left join fetch riesgo.vulnerabilidad where riesgo.id =:id")
    Optional<Riesgo> findOneWithToOneRelationships(@Param("id") Long id);
}
