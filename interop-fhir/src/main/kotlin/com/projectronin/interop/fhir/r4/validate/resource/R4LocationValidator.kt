package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.fhir.r4.resource.Location
import com.projectronin.interop.fhir.r4.validate.element.R4ElementContainingValidator
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.Validation

/**
 * Validator for the [R4 Location](http://hl7.org/fhir/R4/location.html)
 */
object R4LocationValidator : R4ElementContainingValidator<Location>() {
    override fun validateElement(
        element: Location,
        parentContext: LocationContext?,
        validation: Validation,
    ) {
        // Location has no special Validation logic, but it should still evaluate its annotations and contained elements.
    }
}
