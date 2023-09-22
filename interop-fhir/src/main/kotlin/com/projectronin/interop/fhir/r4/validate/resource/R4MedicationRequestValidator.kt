package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.resource.MedicationRequest
import com.projectronin.interop.fhir.r4.resource.Substitution
import com.projectronin.interop.fhir.r4.validate.element.R4ElementContainingValidator
import com.projectronin.interop.fhir.validate.InvalidDynamicValueError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.Validation

/**
 * Validator for the [R4 MedicationRequest](https://www.hl7.org/fhir/R4/medicationrequest.html)
 */
object R4MedicationRequestValidator : R4ElementContainingValidator<MedicationRequest>() {
    private val acceptedReportedTypes = listOf(DynamicValueType.BOOLEAN, DynamicValueType.REFERENCE)
    private val acceptedMedicationTypes = listOf(DynamicValueType.CODEABLE_CONCEPT, DynamicValueType.REFERENCE)

    private val invalidReportedError = InvalidDynamicValueError(MedicationRequest::reported, acceptedReportedTypes)
    private val invalidMedicationError =
        InvalidDynamicValueError(MedicationRequest::medication, acceptedMedicationTypes)

    override fun validateElement(element: MedicationRequest, parentContext: LocationContext?, validation: Validation) {
        validation.apply {
            element.reported?.let {
                checkTrue(acceptedReportedTypes.contains(it.type), invalidReportedError, parentContext)
            }

            element.medication?.let {
                checkTrue(
                    acceptedMedicationTypes.contains(element.medication.type),
                    invalidMedicationError,
                    parentContext
                )
            }
        }
    }
}

/**
 * Validator for the [R4 Substitution](https://www.hl7.org/fhir/R4/medicationrequest-definitions.html#MedicationRequest.substitution)
 */
object R4SubstitutionValidator : R4ElementContainingValidator<Substitution>() {
    private val acceptedAllowedTypes = listOf(DynamicValueType.BOOLEAN, DynamicValueType.CODEABLE_CONCEPT)

    private val invalidAllowedError = InvalidDynamicValueError(Substitution::allowed, acceptedAllowedTypes)

    override fun validateElement(element: Substitution, parentContext: LocationContext?, validation: Validation) {
        validation.apply {
            element.allowed?.let {
                checkTrue(acceptedAllowedTypes.contains(element.allowed.type), invalidAllowedError, parentContext)
            }
        }
    }
}
