package com.mycompany.myapp.domain.enumeration;

/**
 * The Integridad enumeration.
 */
public enum Integridad {
    HIGH("Alta"),
    MEDIUM("Moderada"),
    LOW("Baja");

    private final String value;

    Integridad(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
