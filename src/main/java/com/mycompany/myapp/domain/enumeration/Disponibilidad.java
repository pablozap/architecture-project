package com.mycompany.myapp.domain.enumeration;

/**
 * The Disponibilidad enumeration.
 */
public enum Disponibilidad {
    HIGH("Alta"),
    MEDIUM("Moderada"),
    LOW("Baja");

    private final String value;

    Disponibilidad(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
