package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.resource.DiagnosticReport
import com.projectronin.interop.fhir.r4.validate.element.R4ElementContainingValidator
import com.projectronin.interop.fhir.validate.InvalidDynamicValueError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.Validation

/**
 * Validator for the [R4 DiagnosticReport](https://hl7.org/fhir/r4/diagnosticreport.html)
 */
object R4DiagnosticReportValidator : R4ElementContainingValidator<DiagnosticReport>() {
    private val acceptedEffectiveTypes = listOf(DynamicValueType.DATE_TIME, DynamicValueType.PERIOD)

    private val invalidEffectiveError = InvalidDynamicValueError(DiagnosticReport::effective, acceptedEffectiveTypes)

    override fun validateElement(element: DiagnosticReport, parentContext: LocationContext?, validation: Validation) {
        validation.apply {
            element.effective?.let {
                checkTrue(acceptedEffectiveTypes.contains(it.type), invalidEffectiveError, parentContext)
            }
        }
    }
}
