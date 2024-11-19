package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Riesgo;
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
public class RiesgoRepositoryWithBagRelationshipsImpl implements RiesgoRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String RIESGOS_PARAMETER = "riesgos";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Riesgo> fetchBagRelationships(Optional<Riesgo> riesgo) {
        return riesgo.map(this::fetchActivos).map(this::fetchControles);
    }

    @Override
    public Page<Riesgo> fetchBagRelationships(Page<Riesgo> riesgos) {
        return new PageImpl<>(fetchBagRelationships(riesgos.getContent()), riesgos.getPageable(), riesgos.getTotalElements());
    }

    @Override
    public List<Riesgo> fetchBagRelationships(List<Riesgo> riesgos) {
        return Optional.of(riesgos).map(this::fetchActivos).map(this::fetchControles).orElse(Collections.emptyList());
    }

    Riesgo fetchActivos(Riesgo result) {
        return entityManager
            .createQuery("select riesgo from Riesgo riesgo left join fetch riesgo.activos where riesgo.id = :id", Riesgo.class)
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Riesgo> fetchActivos(List<Riesgo> riesgos) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, riesgos.size()).forEach(index -> order.put(riesgos.get(index).getId(), index));
        List<Riesgo> result = entityManager
            .createQuery("select riesgo from Riesgo riesgo left join fetch riesgo.activos where riesgo in :riesgos", Riesgo.class)
            .setParameter(RIESGOS_PARAMETER, riesgos)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }

    Riesgo fetchControles(Riesgo result) {
        return entityManager
            .createQuery("select riesgo from Riesgo riesgo left join fetch riesgo.controles where riesgo.id = :id", Riesgo.class)
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Riesgo> fetchControles(List<Riesgo> riesgos) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, riesgos.size()).forEach(index -> order.put(riesgos.get(index).getId(), index));
        List<Riesgo> result = entityManager
            .createQuery("select riesgo from Riesgo riesgo left join fetch riesgo.controles where riesgo in :riesgos", Riesgo.class)
            .setParameter(RIESGOS_PARAMETER, riesgos)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
