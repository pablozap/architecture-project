package com.mycompany.myapp.domain.enumeration;

/**
 * The AfectacionEconomica enumeration.
 */
public enum AfectacionEconomica {
    LOWEST("Menor a 10 SMLMV"),
    LOW("Entre 10 y 50 SMLMV"),
    MEDIUM("Entre 50 y 100 SMLMV"),
    HIGH("Entre 100 y 500 SMLMV"),
    HIGHEST("Mayor a 500 SMLMV");

    private final String value;

    AfectacionEconomica(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
