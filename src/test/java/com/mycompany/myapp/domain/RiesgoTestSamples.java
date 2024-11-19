package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class RiesgoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Riesgo getRiesgoSample1() {
        return new Riesgo()
            .id(1L)
            .proceso("proceso1")
            .descripcion("descripcion1")
            .frecuencia(1)
            .calificacionControl(1)
            .probabilidad(1)
            .impacto(1)
            .riesgoResidual("riesgoResidual1")
            .planAccion("planAccion1")
            .responsable("responsable1")
            .seguimientoControlExistente("seguimientoControlExistente1")
            .estado("estado1")
            .observaciones("observaciones1");
    }

    public static Riesgo getRiesgoSample2() {
        return new Riesgo()
            .id(2L)
            .proceso("proceso2")
            .descripcion("descripcion2")
            .frecuencia(2)
            .calificacionControl(2)
            .probabilidad(2)
            .impacto(2)
            .riesgoResidual("riesgoResidual2")
            .planAccion("planAccion2")
            .responsable("responsable2")
            .seguimientoControlExistente("seguimientoControlExistente2")
            .estado("estado2")
            .observaciones("observaciones2");
    }

    public static Riesgo getRiesgoRandomSampleGenerator() {
        return new Riesgo()
            .id(longCount.incrementAndGet())
            .proceso(UUID.randomUUID().toString())
            .descripcion(UUID.randomUUID().toString())
            .frecuencia(intCount.incrementAndGet())
            .calificacionControl(intCount.incrementAndGet())
            .probabilidad(intCount.incrementAndGet())
            .impacto(intCount.incrementAndGet())
            .riesgoResidual(UUID.randomUUID().toString())
            .planAccion(UUID.randomUUID().toString())
            .responsable(UUID.randomUUID().toString())
            .seguimientoControlExistente(UUID.randomUUID().toString())
            .estado(UUID.randomUUID().toString())
            .observaciones(UUID.randomUUID().toString());
    }
}
