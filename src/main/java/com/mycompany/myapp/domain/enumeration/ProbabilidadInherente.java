package com.mycompany.myapp.domain.enumeration;

/**
 * The ProbabilidadInherente enumeration.
 */
public enum ProbabilidadInherente {
    LOWEST("Muy Baja"),
    LOW("Baja"),
    MEDIUM("Moderada"),
    HIGH("Alta"),
    HIGHEST("Muy Alta");

    private final String value;

    ProbabilidadInherente(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
