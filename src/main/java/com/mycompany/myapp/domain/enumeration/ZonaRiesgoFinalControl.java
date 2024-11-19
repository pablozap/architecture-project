package com.mycompany.myapp.domain.enumeration;

/**
 * The ZonaRiesgoFinalControl enumeration.
 */
public enum ZonaRiesgoFinalControl {
    LOWEST("Muy bajo"),
    LOW("Leve"),
    MEDIUM("Moderado"),
    HIGH("Alto"),
    HIGHEST("Muy alto");

    private final String value;

    ZonaRiesgoFinalControl(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
