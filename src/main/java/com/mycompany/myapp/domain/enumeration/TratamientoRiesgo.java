package com.mycompany.myapp.domain.enumeration;

/**
 * The TratamientoRiesgo enumeration.
 */
public enum TratamientoRiesgo {
    REDUCE("Reducir"),
    TRANSFER("Transferir"),
    ACCEPT("Aceptar"),
    AVOID("Evitar");

    private final String value;

    TratamientoRiesgo(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
