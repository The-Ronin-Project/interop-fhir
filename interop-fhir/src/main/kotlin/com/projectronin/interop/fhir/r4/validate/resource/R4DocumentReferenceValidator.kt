package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.fhir.r4.resource.DocumentReference
import com.projectronin.interop.fhir.r4.validate.element.R4ElementContainingValidator
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.Validation

/**
 * Validator for the [R4 DocumentReference](https://hl7.org/fhir/r4/documentreference.html)
 */
object R4DocumentReferenceValidator : R4ElementContainingValidator<DocumentReference>() {
    override fun validateElement(
        element: DocumentReference,
        parentContext: LocationContext?,
        validation: Validation,
    ) {
        // DocumentReference has no special Validation logic, but it should still evaluate its annotations and contained elements.
    }
}
