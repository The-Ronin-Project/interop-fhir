package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.fhir.r4.resource.Medication
import com.projectronin.interop.fhir.r4.validate.element.R4ElementContainingValidator
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.Validation

/**
 * Validator for the [R4 Medication](https://www.hl7.org/fhir/R4/medication.html)
 */
object R4MedicationValidator : R4ElementContainingValidator<Medication>() {
    override fun validateElement(element: Medication, parentContext: LocationContext?, validation: Validation) {
        // Medication has no special Validation logic, but it should still evaluate its annotations and contained elements.
    }
}
