package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.GrupoActivos;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class GrupoActivosRepositoryWithBagRelationshipsImpl implements GrupoActivosRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String GRUPOACTIVOS_PARAMETER = "grupoActivos";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<GrupoActivos> fetchBagRelationships(Optional<GrupoActivos> grupoActivos) {
        return grupoActivos.map(this::fetchRiesgos);
    }

    @Override
    public Page<GrupoActivos> fetchBagRelationships(Page<GrupoActivos> grupoActivos) {
        return new PageImpl<>(
            fetchBagRelationships(grupoActivos.getContent()),
            grupoActivos.getPageable(),
            grupoActivos.getTotalElements()
        );
    }

    @Override
    public List<GrupoActivos> fetchBagRelationships(List<GrupoActivos> grupoActivos) {
        return Optional.of(grupoActivos).map(this::fetchRiesgos).orElse(Collections.emptyList());
    }

    GrupoActivos fetchRiesgos(GrupoActivos result) {
        return entityManager
            .createQuery(
                "select grupoActivos from GrupoActivos grupoActivos left join fetch grupoActivos.riesgos where grupoActivos.id = :id",
                GrupoActivos.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<GrupoActivos> fetchRiesgos(List<GrupoActivos> grupoActivos) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, grupoActivos.size()).forEach(index -> order.put(grupoActivos.get(index).getId(), index));
        List<GrupoActivos> result = entityManager
            .createQuery(
                "select grupoActivos from GrupoActivos grupoActivos left join fetch grupoActivos.riesgos where grupoActivos in :grupoActivos",
                GrupoActivos.class
            )
            .setParameter(GRUPOACTIVOS_PARAMETER, grupoActivos)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
