package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.fhir.r4.resource.MedicationStatement
import com.projectronin.interop.fhir.r4.validate.element.R4ElementContainingValidator
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.Validation

object R4MedicationStatementValidator : R4ElementContainingValidator<MedicationStatement>() {
    override fun validateElement(
        element: MedicationStatement,
        parentContext: LocationContext?,
        validation: Validation,
    ) {
        // MedicationStatement has no special Validation logic, but it should still evaluate its annotations and contained elements.
    }
}
