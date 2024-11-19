package com.mycompany.myapp.domain.enumeration;

/**
 * The Proceso enumeration.
 */
public enum Proceso {
    MISSION("Misional"),
    STRATEGIC("Estrategico"),
    SUPPORT("Apoyo"),
    EVALUATION("Evaluacion"),
    IMPROVEMENT("Mejora");

    private final String value;

    Proceso(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
