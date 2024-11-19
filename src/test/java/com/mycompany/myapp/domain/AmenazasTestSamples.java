package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AmenazasTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Amenazas getAmenazasSample1() {
        return new Amenazas().id(1L).nombre("nombre1");
    }

    public static Amenazas getAmenazasSample2() {
        return new Amenazas().id(2L).nombre("nombre2");
    }

    public static Amenazas getAmenazasRandomSampleGenerator() {
        return new Amenazas().id(longCount.incrementAndGet()).nombre(UUID.randomUUID().toString());
    }
}
