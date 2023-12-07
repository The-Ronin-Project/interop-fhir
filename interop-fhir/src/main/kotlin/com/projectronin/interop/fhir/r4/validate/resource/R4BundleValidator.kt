package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.fhir.r4.resource.Bundle
import com.projectronin.interop.fhir.r4.validate.element.R4ElementContainingValidator
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.Validation

/**
 * Validator for the [R4 Bundle](http://hl7.org/fhir/R4/bundle.html)
 */
object R4BundleValidator : R4ElementContainingValidator<Bundle>() {
    override fun validateElement(
        element: Bundle,
        parentContext: LocationContext?,
        validation: Validation,
    ) {
        // Bundle has no special Validation logic, but it should still evaluate its annotations and contained elements.
    }
}
