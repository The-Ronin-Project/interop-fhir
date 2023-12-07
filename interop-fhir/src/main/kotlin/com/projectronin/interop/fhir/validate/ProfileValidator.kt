package com.projectronin.interop.fhir.validate

/**
 * Classes that inherit from this interface are capable of validating any [Validatable] type against a specific profile mandated by the class.
 */
interface ProfileValidator<T : Validatable<T>> {
    /**
     * Validates the [element] against this profile. An optional [parentContext] can be provided to give current Location information for a nested validation.
     */
    fun validate(
        element: T,
        parentContext: LocationContext? = null,
    ): Validation
}
