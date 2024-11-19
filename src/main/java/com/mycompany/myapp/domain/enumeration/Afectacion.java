package com.mycompany.myapp.domain.enumeration;

/**
 * The Afectacion enumeration.
 */
public enum Afectacion {
    PROBABILITY("Probabilidad"),
    IMPACT("Impacto");

    private final String value;

    Afectacion(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
