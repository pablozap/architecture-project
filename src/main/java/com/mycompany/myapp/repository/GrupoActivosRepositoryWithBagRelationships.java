package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.GrupoActivos;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface GrupoActivosRepositoryWithBagRelationships {
    Optional<GrupoActivos> fetchBagRelationships(Optional<GrupoActivos> grupoActivos);

    List<GrupoActivos> fetchBagRelationships(List<GrupoActivos> grupoActivos);

    Page<GrupoActivos> fetchBagRelationships(Page<GrupoActivos> grupoActivos);
}
