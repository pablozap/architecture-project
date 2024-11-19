package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class VulnerabilidadesTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Vulnerabilidades getVulnerabilidadesSample1() {
        return new Vulnerabilidades().id(1L).nombre("nombre1").descripcion("descripcion1");
    }

    public static Vulnerabilidades getVulnerabilidadesSample2() {
        return new Vulnerabilidades().id(2L).nombre("nombre2").descripcion("descripcion2");
    }

    public static Vulnerabilidades getVulnerabilidadesRandomSampleGenerator() {
        return new Vulnerabilidades()
            .id(longCount.incrementAndGet())
            .nombre(UUID.randomUUID().toString())
            .descripcion(UUID.randomUUID().toString());
    }
}
