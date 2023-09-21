package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.resource.Immunization
import com.projectronin.interop.fhir.r4.resource.ImmunizationEducation
import com.projectronin.interop.fhir.r4.resource.ImmunizationProtocolApplied
import com.projectronin.interop.fhir.r4.validate.element.R4ElementContainingValidator
import com.projectronin.interop.fhir.r4.valueset.ImmunizationStatus
import com.projectronin.interop.fhir.validate.FHIRError
import com.projectronin.interop.fhir.validate.InvalidDynamicValueError
import com.projectronin.interop.fhir.validate.InvalidValueSetError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.Validation
import com.projectronin.interop.fhir.validate.ValidationIssueSeverity

/**
 * Validator for the [R4 Immunization](https://hl7.org/fhir/r4/immunization.html)
 */
object R4ImmunizationValidator : R4ElementContainingValidator<Immunization>() {
    private val acceptedOccurrenceTypes = listOf(DynamicValueType.DATE_TIME, DynamicValueType.STRING)

    private val invalidOccurrenceError = InvalidDynamicValueError(Immunization::occurrence, acceptedOccurrenceTypes)

    override fun validateElement(element: Immunization, parentContext: LocationContext?, validation: Validation) {
        validation.apply {
            element.status?.let {
                checkCodedEnum<ImmunizationStatus>(
                    element.status,
                    InvalidValueSetError(Immunization::status, element.status.value),
                    parentContext
                )
            }

            element.occurrence?.let {
                checkTrue(
                    acceptedOccurrenceTypes.contains(element.occurrence.type),
                    invalidOccurrenceError,
                    parentContext
                )
            }
        }
    }
}

/**
 * Validator for the [R4 Immunization.Education](https://hl7.org/fhir/r4/immunization-definitions.html#Immunization.education).
 */
object R4ImmunizationEducationValidator : R4ElementContainingValidator<ImmunizationEducation>() {
    private val noDocumentTypeOrReferenceError = FHIRError(
        code = "R4_IMMEDU_001",
        severity = ValidationIssueSeverity.ERROR,
        description = "One of documentType or reference SHALL be present",
        location = LocationContext(ImmunizationEducation::class)
    )

    override fun validateElement(
        element: ImmunizationEducation,
        parentContext: LocationContext?,
        validation: Validation
    ) {
        validation.apply {
            checkTrue(
                element.documentType != null || element.reference != null,
                noDocumentTypeOrReferenceError,
                parentContext
            )
        }
    }
}

/**
 * Validator for the [R4 Immunization.ProtocolApplied](https://hl7.org/fhir/r4/immunization-definitions.html#Immunization.protocolApplied).
 */
object R4ImmunizationProtocolAppliedValidator : R4ElementContainingValidator<ImmunizationProtocolApplied>() {
    private val acceptedDoseNumberTypes = listOf(DynamicValueType.POSITIVE_INT, DynamicValueType.STRING)
    private val acceptedSeriesDosesTypes = listOf(DynamicValueType.POSITIVE_INT, DynamicValueType.STRING)

    private val invalidDoseNumberError =
        InvalidDynamicValueError(ImmunizationProtocolApplied::doseNumber, acceptedDoseNumberTypes)
    private val invalidSeriesDosesError =
        InvalidDynamicValueError(ImmunizationProtocolApplied::seriesDoses, acceptedSeriesDosesTypes)

    override fun validateElement(
        element: ImmunizationProtocolApplied,
        parentContext: LocationContext?,
        validation: Validation
    ) {
        validation.apply {
            element.doseNumber?.let {
                checkTrue(
                    acceptedDoseNumberTypes.contains(element.doseNumber.type),
                    invalidDoseNumberError,
                    parentContext
                )
            }

            element.seriesDoses?.let { seriesDoses ->
                checkTrue(acceptedSeriesDosesTypes.contains(seriesDoses.type), invalidSeriesDosesError, parentContext)
            }
        }
    }
}
