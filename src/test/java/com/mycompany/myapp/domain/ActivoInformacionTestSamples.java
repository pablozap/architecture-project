package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ActivoInformacionTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static ActivoInformacion getActivoInformacionSample1() {
        return new ActivoInformacion()
            .id(1L)
            .nombre("nombre1")
            .descripcion("descripcion1")
            .propietario("propietario1")
            .usuario("usuario1")
            .custodio("custodio1")
            .usuarioFinal("usuarioFinal1")
            .estadoActivo("estadoActivo1");
    }

    public static ActivoInformacion getActivoInformacionSample2() {
        return new ActivoInformacion()
            .id(2L)
            .nombre("nombre2")
            .descripcion("descripcion2")
            .propietario("propietario2")
            .usuario("usuario2")
            .custodio("custodio2")
            .usuarioFinal("usuarioFinal2")
            .estadoActivo("estadoActivo2");
    }

    public static ActivoInformacion getActivoInformacionRandomSampleGenerator() {
        return new ActivoInformacion()
            .id(longCount.incrementAndGet())
            .nombre(UUID.randomUUID().toString())
            .descripcion(UUID.randomUUID().toString())
            .propietario(UUID.randomUUID().toString())
            .usuario(UUID.randomUUID().toString())
            .custodio(UUID.randomUUID().toString())
            .usuarioFinal(UUID.randomUUID().toString())
            .estadoActivo(UUID.randomUUID().toString());
    }
}
