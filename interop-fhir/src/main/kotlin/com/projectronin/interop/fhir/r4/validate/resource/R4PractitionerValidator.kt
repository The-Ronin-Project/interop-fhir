package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.fhir.r4.resource.Practitioner
import com.projectronin.interop.fhir.r4.validate.element.R4ElementContainingValidator
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.Validation

/**
 * Validator for the [R4 Practitioner](http://hl7.org/fhir/R4/practitioner.html)
 */
object R4PractitionerValidator : R4ElementContainingValidator<Practitioner>() {
    override fun validateElement(
        element: Practitioner,
        parentContext: LocationContext?,
        validation: Validation,
    ) {
        // Practitioner has no special Validation logic, but it should still evaluate its annotations and contained elements.
    }
}
