package com.mycompany.myapp.domain.enumeration;

/**
 * The ClasificacionInformacion1712 enumeration.
 */
public enum ClasificacionInformacion1712 {
    RESTRICTED("Contiene información publica reservada"),
    CLASSIFIED("Contiene información publica clasificada"),
    PUBLIC("Contiene información publica"),
    NONPUBLIC("La información contenida no está clasificada");

    private final String value;

    ClasificacionInformacion1712(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
