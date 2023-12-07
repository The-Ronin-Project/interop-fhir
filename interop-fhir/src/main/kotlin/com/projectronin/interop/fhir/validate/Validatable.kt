package com.projectronin.interop.fhir.validate

interface Validatable<T : Validatable<T>> {
    /**
     * Validates this resource against the supplied [validator]. An optional [parentContext] can be provided to give current Location information for a nested validation.
     */
    @Suppress("UNCHECKED_CAST")
    fun validate(
        validator: ProfileValidator<T>,
        parentContext: LocationContext? = null,
    ) = validator.validate(this as T, parentContext)
}
