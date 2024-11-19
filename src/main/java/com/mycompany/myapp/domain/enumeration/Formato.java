package com.mycompany.myapp.domain.enumeration;

/**
 * The Formato enumeration.
 */
public enum Formato {
    EXCEL("Hoja de calculo"),
    TEXT("Documento de texto"),
    PDF("Documento PDF"),
    PRESENTATION("Presentacion"),
    MULTIMEDIA("Imagen/Audio"),
    NA("No Aplica");

    private final String value;

    Formato(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
