package com.mycompany.myapp.domain.enumeration;

/**
 * The TipoControl enumeration.
 */
public enum TipoControl {
    PREVENTIVE("Preventivo"),
    DETECTIVE("Detectivo"),
    CORRECTIVE("Correctivo");

    private final String value;

    TipoControl(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
