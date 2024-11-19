package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class GrupoActivosTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static GrupoActivos getGrupoActivosSample1() {
        return new GrupoActivos().id(1L).nombre("nombre1");
    }

    public static GrupoActivos getGrupoActivosSample2() {
        return new GrupoActivos().id(2L).nombre("nombre2");
    }

    public static GrupoActivos getGrupoActivosRandomSampleGenerator() {
        return new GrupoActivos().id(longCount.incrementAndGet()).nombre(UUID.randomUUID().toString());
    }
}
