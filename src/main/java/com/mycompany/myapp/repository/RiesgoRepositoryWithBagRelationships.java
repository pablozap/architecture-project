package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Riesgo;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface RiesgoRepositoryWithBagRelationships {
    Optional<Riesgo> fetchBagRelationships(Optional<Riesgo> riesgo);

    List<Riesgo> fetchBagRelationships(List<Riesgo> riesgos);

    Page<Riesgo> fetchBagRelationships(Page<Riesgo> riesgos);
}
