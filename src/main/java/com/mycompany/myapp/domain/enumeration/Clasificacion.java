package com.mycompany.myapp.domain.enumeration;

/**
 * The Clasificacion enumeration.
 */
public enum Clasificacion {
    EXECUTION("Ejecucion y Administracion"),
    INTERN("Fraude interno"),
    EXTERN("Fraude externo"),
    FAILS("Fallas tecnologicas"),
    RELATIONS("Relaciones laborales"),
    HUMAN("Usuarios productos y practicas"),
    DAMAGE("Daños a activos fijos/eventos");

    private final String value;

    Clasificacion(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
