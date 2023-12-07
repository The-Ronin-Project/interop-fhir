package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.fhir.r4.resource.Procedure
import com.projectronin.interop.fhir.r4.validate.element.R4ElementContainingValidator
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.Validation

/**
 * Validator for the [R4 Procedure](http://hl7.org/fhir/R4/procedure.html)
 */
object R4ProcedureValidator : R4ElementContainingValidator<Procedure>() {
    override fun validateElement(
        element: Procedure,
        parentContext: LocationContext?,
        validation: Validation,
    ) {
        // Procedure has no special Validation logic, but it should still evaluate its annotations and contained elements.
    }
}
