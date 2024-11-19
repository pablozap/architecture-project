package com.mycompany.myapp.domain.enumeration;

/**
 * The ImpactoInherente enumeration.
 */
public enum ImpactoInherente {
    NA("No ha calificado"),
    LOWEST("Leve"),
    LOW("Menor"),
    MEDIUM("Moderado"),
    HIGH("Mayor"),
    HIGHEST("Catastrofico");

    private final String value;

    ImpactoInherente(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
