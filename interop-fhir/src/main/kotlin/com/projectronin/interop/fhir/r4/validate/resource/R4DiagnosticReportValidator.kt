package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.fhir.r4.resource.DiagnosticReport
import com.projectronin.interop.fhir.r4.validate.element.R4ElementContainingValidator
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.Validation

/**
 * Validator for the [R4 DiagnosticReport](https://hl7.org/fhir/r4/diagnosticreport.html)
 */
object R4DiagnosticReportValidator : R4ElementContainingValidator<DiagnosticReport>() {
    override fun validateElement(element: DiagnosticReport, parentContext: LocationContext?, validation: Validation) {
        // DiagnosticReport has no special Validation logic, but it should still evaluate its annotations and contained elements.
    }
}
