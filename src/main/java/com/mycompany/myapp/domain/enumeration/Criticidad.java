package com.mycompany.myapp.domain.enumeration;

/**
 * The Criticidad enumeration.
 */
public enum Criticidad {
    HIGH("Alta"),
    MEDIUM("Moderada"),
    LOW("Baja");

    private final String value;

    Criticidad(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
