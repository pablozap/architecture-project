package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class AmenazasAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAmenazasAllPropertiesEquals(Amenazas expected, Amenazas actual) {
        assertAmenazasAutoGeneratedPropertiesEquals(expected, actual);
        assertAmenazasAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAmenazasAllUpdatablePropertiesEquals(Amenazas expected, Amenazas actual) {
        assertAmenazasUpdatableFieldsEquals(expected, actual);
        assertAmenazasUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAmenazasAutoGeneratedPropertiesEquals(Amenazas expected, Amenazas actual) {
        assertThat(expected)
            .as("Verify Amenazas auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAmenazasUpdatableFieldsEquals(Amenazas expected, Amenazas actual) {
        assertThat(expected)
            .as("Verify Amenazas relevant properties")
            .satisfies(e -> assertThat(e.getNombre()).as("check nombre").isEqualTo(actual.getNombre()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAmenazasUpdatableRelationshipsEquals(Amenazas expected, Amenazas actual) {
        // empty method
    }
}
