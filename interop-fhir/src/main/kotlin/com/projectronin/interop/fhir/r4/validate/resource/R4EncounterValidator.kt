package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.fhir.r4.resource.Encounter
import com.projectronin.interop.fhir.r4.validate.element.R4ElementContainingValidator
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.Validation

/**
 * Validator for the [R4 Encounter](http://hl7.org/fhir/R4/encounter.html)
 */
object R4EncounterValidator : R4ElementContainingValidator<Encounter>() {
    override fun validateElement(
        element: Encounter,
        parentContext: LocationContext?,
        validation: Validation,
    ) {
        // Encounter has no special Validation logic, but it should still evaluate its annotations and contained elements.
    }
}
