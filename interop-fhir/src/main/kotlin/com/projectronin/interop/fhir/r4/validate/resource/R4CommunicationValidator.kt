package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.fhir.r4.resource.Communication
import com.projectronin.interop.fhir.r4.validate.element.R4ElementContainingValidator
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.Validation

/**
 * Validator for the [R4 Communication](https://hl7.org/fhir/r4/communication.html)
 */
object R4CommunicationValidator : R4ElementContainingValidator<Communication>() {
    override fun validateElement(
        element: Communication,
        parentContext: LocationContext?,
        validation: Validation,
    ) {
        // Communication has no special Validation logic, but it should still evaluate its annotations and contained elements.
    }
}
