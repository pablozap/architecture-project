package com.mycompany.myapp.domain.enumeration;

/**
 * The ImpactoResidualFinal enumeration.
 */
public enum ImpactoResidualFinal {
    LOWEST("Leve"),
    LOW("Menor"),
    MEDIUM("Moderado"),
    HIGH("Mayor"),
    HIGHEST("Catastrofico");

    private final String value;

    ImpactoResidualFinal(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
