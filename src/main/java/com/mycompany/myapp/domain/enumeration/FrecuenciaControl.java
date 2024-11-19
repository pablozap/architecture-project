package com.mycompany.myapp.domain.enumeration;

/**
 * The FrecuenciaControl enumeration.
 */
public enum FrecuenciaControl {
    CONTINUOUS("Continua"),
    RANDOM("Aleatoria");

    private final String value;

    FrecuenciaControl(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
