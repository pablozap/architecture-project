package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class ActivoInformacionAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertActivoInformacionAllPropertiesEquals(ActivoInformacion expected, ActivoInformacion actual) {
        assertActivoInformacionAutoGeneratedPropertiesEquals(expected, actual);
        assertActivoInformacionAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertActivoInformacionAllUpdatablePropertiesEquals(ActivoInformacion expected, ActivoInformacion actual) {
        assertActivoInformacionUpdatableFieldsEquals(expected, actual);
        assertActivoInformacionUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertActivoInformacionAutoGeneratedPropertiesEquals(ActivoInformacion expected, ActivoInformacion actual) {
        assertThat(expected)
            .as("Verify ActivoInformacion auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertActivoInformacionUpdatableFieldsEquals(ActivoInformacion expected, ActivoInformacion actual) {
        assertThat(expected)
            .as("Verify ActivoInformacion relevant properties")
            .satisfies(e -> assertThat(e.getProceso()).as("check proceso").isEqualTo(actual.getProceso()))
            .satisfies(e -> assertThat(e.getNombre()).as("check nombre").isEqualTo(actual.getNombre()))
            .satisfies(e -> assertThat(e.getDescripcion()).as("check descripcion").isEqualTo(actual.getDescripcion()))
            .satisfies(e -> assertThat(e.getTipoActivo()).as("check tipoActivo").isEqualTo(actual.getTipoActivo()))
            .satisfies(e -> assertThat(e.getLey1581()).as("check ley1581").isEqualTo(actual.getLey1581()))
            .satisfies(e ->
                assertThat(e.getClasificacionInformacion1712())
                    .as("check clasificacionInformacion1712")
                    .isEqualTo(actual.getClasificacionInformacion1712())
            )
            .satisfies(e -> assertThat(e.getLey1266()).as("check ley1266").isEqualTo(actual.getLey1266()))
            .satisfies(e -> assertThat(e.getFormato()).as("check formato").isEqualTo(actual.getFormato()))
            .satisfies(e -> assertThat(e.getPropietario()).as("check propietario").isEqualTo(actual.getPropietario()))
            .satisfies(e -> assertThat(e.getUsuario()).as("check usuario").isEqualTo(actual.getUsuario()))
            .satisfies(e -> assertThat(e.getCustodio()).as("check custodio").isEqualTo(actual.getCustodio()))
            .satisfies(e -> assertThat(e.getUsuarioFinal()).as("check usuarioFinal").isEqualTo(actual.getUsuarioFinal()))
            .satisfies(e -> assertThat(e.getFecha()).as("check fecha").isEqualTo(actual.getFecha()))
            .satisfies(e -> assertThat(e.getEstadoActivo()).as("check estadoActivo").isEqualTo(actual.getEstadoActivo()))
            .satisfies(e -> assertThat(e.getFechaIngreso()).as("check fechaIngreso").isEqualTo(actual.getFechaIngreso()))
            .satisfies(e -> assertThat(e.getFechaRetiro()).as("check fechaRetiro").isEqualTo(actual.getFechaRetiro()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertActivoInformacionUpdatableRelationshipsEquals(ActivoInformacion expected, ActivoInformacion actual) {
        assertThat(expected)
            .as("Verify ActivoInformacion relationships")
            .satisfies(e -> assertThat(e.getGrupoActivo()).as("check grupoActivo").isEqualTo(actual.getGrupoActivo()));
    }
}
