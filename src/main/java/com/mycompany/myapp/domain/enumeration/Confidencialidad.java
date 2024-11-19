package com.mycompany.myapp.domain.enumeration;

/**
 * The Confidencialidad enumeration.
 */
public enum Confidencialidad {
    HIGH("Alta"),
    MEDIUM("Moderada"),
    LOW("Baja");

    private final String value;

    Confidencialidad(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
