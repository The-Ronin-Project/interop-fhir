package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.fhir.r4.resource.MedicationRequest
import com.projectronin.interop.fhir.r4.validate.element.R4ElementContainingValidator
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.Validation

/**
 * Validator for the [R4 MedicationRequest](https://www.hl7.org/fhir/R4/medicationrequest.html)
 */
object R4MedicationRequestValidator : R4ElementContainingValidator<MedicationRequest>() {
    override fun validateElement(element: MedicationRequest, parentContext: LocationContext?, validation: Validation) {
        // MedicationRequest has no special Validation logic, but it should still evaluate its annotations and contained elements.
    }
}
