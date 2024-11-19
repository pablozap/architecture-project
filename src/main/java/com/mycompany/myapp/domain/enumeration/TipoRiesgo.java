package com.mycompany.myapp.domain.enumeration;

/**
 * The TipoRiesgo enumeration.
 */
public enum TipoRiesgo {
    INTEGRITY("Perdida de integridad"),
    AVAILABILITY("Perdida de disponibilidad"),
    CONFIDENTIALITY("Perdida de confidencialidad");

    private final String value;

    TipoRiesgo(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
