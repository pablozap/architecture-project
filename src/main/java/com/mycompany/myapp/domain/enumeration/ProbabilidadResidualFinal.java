package com.mycompany.myapp.domain.enumeration;

/**
 * The ProbabilidadResidualFinal enumeration.
 */
public enum ProbabilidadResidualFinal {
    LOWEST("Muy Baja"),
    LOW("Baja"),
    MEDIUM("Moderada"),
    HIGH("Alta"),
    HIGHEST("Muy Alta");

    private final String value;

    ProbabilidadResidualFinal(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
