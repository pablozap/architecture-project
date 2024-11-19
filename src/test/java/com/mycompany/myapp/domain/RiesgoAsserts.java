package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class RiesgoAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRiesgoAllPropertiesEquals(Riesgo expected, Riesgo actual) {
        assertRiesgoAutoGeneratedPropertiesEquals(expected, actual);
        assertRiesgoAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRiesgoAllUpdatablePropertiesEquals(Riesgo expected, Riesgo actual) {
        assertRiesgoUpdatableFieldsEquals(expected, actual);
        assertRiesgoUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRiesgoAutoGeneratedPropertiesEquals(Riesgo expected, Riesgo actual) {
        assertThat(expected)
            .as("Verify Riesgo auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRiesgoUpdatableFieldsEquals(Riesgo expected, Riesgo actual) {
        assertThat(expected)
            .as("Verify Riesgo relevant properties")
            .satisfies(e -> assertThat(e.getProceso()).as("check proceso").isEqualTo(actual.getProceso()))
            .satisfies(e -> assertThat(e.getTipoRiesgo()).as("check tipoRiesgo").isEqualTo(actual.getTipoRiesgo()))
            .satisfies(e -> assertThat(e.getDescripcion()).as("check descripcion").isEqualTo(actual.getDescripcion()))
            .satisfies(e -> assertThat(e.getClasificacion()).as("check clasificacion").isEqualTo(actual.getClasificacion()))
            .satisfies(e -> assertThat(e.getFrecuencia()).as("check frecuencia").isEqualTo(actual.getFrecuencia()))
            .satisfies(e ->
                assertThat(e.getAfectacionEconomica()).as("check afectacionEconomica").isEqualTo(actual.getAfectacionEconomica())
            )
            .satisfies(e ->
                assertThat(e.getImpactoReputacional()).as("check impactoReputacional").isEqualTo(actual.getImpactoReputacional())
            )
            .satisfies(e ->
                assertThat(e.getProbabilidadInherente()).as("check probabilidadInherente").isEqualTo(actual.getProbabilidadInherente())
            )
            .satisfies(e -> assertThat(e.getImpactoInherente()).as("check impactoInherente").isEqualTo(actual.getImpactoInherente()))
            .satisfies(e -> assertThat(e.getZonaRiesgo()).as("check zonaRiesgo").isEqualTo(actual.getZonaRiesgo()))
            .satisfies(e -> assertThat(e.getAfectacion()).as("check afectacion").isEqualTo(actual.getAfectacion()))
            .satisfies(e -> assertThat(e.getTipoControl()).as("check tipoControl").isEqualTo(actual.getTipoControl()))
            .satisfies(e -> assertThat(e.getImplementacion()).as("check implementacion").isEqualTo(actual.getImplementacion()))
            .satisfies(e ->
                assertThat(e.getCalificacionControl()).as("check calificacionControl").isEqualTo(actual.getCalificacionControl())
            )
            .satisfies(e -> assertThat(e.getDocumentado()).as("check documentado").isEqualTo(actual.getDocumentado()))
            .satisfies(e -> assertThat(e.getFrecuenciaControl()).as("check frecuenciaControl").isEqualTo(actual.getFrecuenciaControl()))
            .satisfies(e -> assertThat(e.getEvidencia()).as("check evidencia").isEqualTo(actual.getEvidencia()))
            .satisfies(e -> assertThat(e.getProbabilidad()).as("check probabilidad").isEqualTo(actual.getProbabilidad()))
            .satisfies(e -> assertThat(e.getImpacto()).as("check impacto").isEqualTo(actual.getImpacto()))
            .satisfies(e ->
                assertThat(e.getProbabilidadResidualFinal())
                    .as("check probabilidadResidualFinal")
                    .isEqualTo(actual.getProbabilidadResidualFinal())
            )
            .satisfies(e ->
                assertThat(e.getImpactoResidualFinal()).as("check impactoResidualFinal").isEqualTo(actual.getImpactoResidualFinal())
            )
            .satisfies(e -> assertThat(e.getZonaDeRiesgoFinal()).as("check zonaDeRiesgoFinal").isEqualTo(actual.getZonaDeRiesgoFinal()))
            .satisfies(e -> assertThat(e.getRiesgoResidual()).as("check riesgoResidual").isEqualTo(actual.getRiesgoResidual()))
            .satisfies(e -> assertThat(e.getTratamiento()).as("check tratamiento").isEqualTo(actual.getTratamiento()))
            .satisfies(e -> assertThat(e.getPlanAccion()).as("check planAccion").isEqualTo(actual.getPlanAccion()))
            .satisfies(e -> assertThat(e.getResponsable()).as("check responsable").isEqualTo(actual.getResponsable()))
            .satisfies(e ->
                assertThat(e.getFechaImplementacion()).as("check fechaImplementacion").isEqualTo(actual.getFechaImplementacion())
            )
            .satisfies(e ->
                assertThat(e.getSeguimientoControlExistente())
                    .as("check seguimientoControlExistente")
                    .isEqualTo(actual.getSeguimientoControlExistente())
            )
            .satisfies(e -> assertThat(e.getEstado()).as("check estado").isEqualTo(actual.getEstado()))
            .satisfies(e -> assertThat(e.getObservaciones()).as("check observaciones").isEqualTo(actual.getObservaciones()))
            .satisfies(e -> assertThat(e.getFechaMonitoreo()).as("check fechaMonitoreo").isEqualTo(actual.getFechaMonitoreo()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRiesgoUpdatableRelationshipsEquals(Riesgo expected, Riesgo actual) {
        assertThat(expected)
            .as("Verify Riesgo relationships")
            .satisfies(e -> assertThat(e.getActivos()).as("check activos").isEqualTo(actual.getActivos()))
            .satisfies(e -> assertThat(e.getControles()).as("check controles").isEqualTo(actual.getControles()))
            .satisfies(e -> assertThat(e.getAmenaza()).as("check amenaza").isEqualTo(actual.getAmenaza()))
            .satisfies(e -> assertThat(e.getVulnerabilidad()).as("check vulnerabilidad").isEqualTo(actual.getVulnerabilidad()));
    }
}
