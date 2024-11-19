package com.mycompany.myapp.domain.enumeration;

/**
 * The ZonaRiesgo enumeration.
 */
public enum ZonaRiesgo {
    LOWEST("Muy Bajo"),
    LOW("Bajo"),
    MEDIUM("Moderado"),
    HIGH("Alto"),
    HIGHEST("Muy Alto");

    private final String value;

    ZonaRiesgo(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
