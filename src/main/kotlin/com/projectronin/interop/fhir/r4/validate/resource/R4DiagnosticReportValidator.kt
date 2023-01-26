package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.resource.DiagnosticReport
import com.projectronin.interop.fhir.r4.resource.DiagnosticReportMedia
import com.projectronin.interop.fhir.r4.validate.element.R4ElementContainingValidator
import com.projectronin.interop.fhir.r4.valueset.DiagnosticReportStatus
import com.projectronin.interop.fhir.validate.InvalidDynamicValueError
import com.projectronin.interop.fhir.validate.InvalidValueSetError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.RequiredFieldError
import com.projectronin.interop.fhir.validate.Validation

/**
 * Validator for the [R4 DiagnosticReport](https://hl7.org/fhir/r4/diagnosticreport.html)
 */
object R4DiagnosticReportValidator : R4ElementContainingValidator<DiagnosticReport>() {
    private val acceptedEffectiveTypes = listOf(DynamicValueType.DATE_TIME, DynamicValueType.PERIOD)

    private val requiredStatusError = RequiredFieldError(DiagnosticReport::status)
    private val requiredCodeError = RequiredFieldError(DiagnosticReport::code)
    private val invalidEffectiveError = InvalidDynamicValueError(DiagnosticReport::effective, acceptedEffectiveTypes)

    override fun validateElement(element: DiagnosticReport, parentContext: LocationContext?, validation: Validation) {
        validation.apply {
            checkNotNull(element.status, requiredStatusError, parentContext)
            ifNotNull(element.status) {
                checkCodedEnum<DiagnosticReportStatus>(
                    element.status,
                    InvalidValueSetError(DiagnosticReport::status, element.status.value),
                    parentContext
                )
            }

            checkNotNull(element.code, requiredCodeError, parentContext)

            element.effective?.let {
                checkTrue(acceptedEffectiveTypes.contains(it.type), invalidEffectiveError, parentContext)
            }
        }
    }
}

/**
 * Validator for the [R4 Media](https://hl7.org/fhir/r4/diagnosticreport-definitions.html#DiagnosticReport.media)
 */
object R4DiagnosticReportMediaValidator : R4ElementContainingValidator<DiagnosticReportMedia>() {
    private val requiredLinkError = RequiredFieldError(DiagnosticReportMedia::link)

    override fun validateElement(
        element: DiagnosticReportMedia,
        parentContext: LocationContext?,
        validation: Validation
    ) {
        validation.apply {
            checkNotNull(element.link, requiredLinkError, parentContext)
        }
    }
}
