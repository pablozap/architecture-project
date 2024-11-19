package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ControlesTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Controles getControlesSample1() {
        return new Controles().id(1L).nombre("nombre1").descripcion("descripcion1");
    }

    public static Controles getControlesSample2() {
        return new Controles().id(2L).nombre("nombre2").descripcion("descripcion2");
    }

    public static Controles getControlesRandomSampleGenerator() {
        return new Controles()
            .id(longCount.incrementAndGet())
            .nombre(UUID.randomUUID().toString())
            .descripcion(UUID.randomUUID().toString());
    }
}
