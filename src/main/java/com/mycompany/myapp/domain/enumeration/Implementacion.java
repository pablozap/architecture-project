package com.mycompany.myapp.domain.enumeration;

/**
 * The Implementacion enumeration.
 */
public enum Implementacion {
    AUTOMATIC("Automatica"),
    MANUAL("Manual");

    private final String value;

    Implementacion(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
