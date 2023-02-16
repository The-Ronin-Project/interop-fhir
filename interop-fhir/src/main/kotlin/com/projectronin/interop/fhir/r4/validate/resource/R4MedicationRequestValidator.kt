package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.resource.MedicationRequest
import com.projectronin.interop.fhir.r4.resource.Substitution
import com.projectronin.interop.fhir.r4.validate.element.R4ElementContainingValidator
import com.projectronin.interop.fhir.r4.valueset.MedicationRequestIntent
import com.projectronin.interop.fhir.r4.valueset.MedicationRequestStatus
import com.projectronin.interop.fhir.r4.valueset.RequestPriority
import com.projectronin.interop.fhir.validate.InvalidDynamicValueError
import com.projectronin.interop.fhir.validate.InvalidValueSetError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.RequiredFieldError
import com.projectronin.interop.fhir.validate.Validation

/**
 * Validator for the [R4 MedicationRequest](https://www.hl7.org/fhir/R4/medicationrequest.html)
 */
object R4MedicationRequestValidator : R4ElementContainingValidator<MedicationRequest>() {
    private val acceptedReportedTypes = listOf(DynamicValueType.BOOLEAN, DynamicValueType.REFERENCE)
    private val acceptedMedicationTypes = listOf(DynamicValueType.CODEABLE_CONCEPT, DynamicValueType.REFERENCE)

    private val requiredStatusError = RequiredFieldError(MedicationRequest::status)
    private val requiredIntentError = RequiredFieldError(MedicationRequest::intent)
    private val invalidReportedError = InvalidDynamicValueError(MedicationRequest::reported, acceptedReportedTypes)
    private val requiredMedicationError = RequiredFieldError(MedicationRequest::medication)
    private val invalidMedicationError =
        InvalidDynamicValueError(MedicationRequest::medication, acceptedMedicationTypes)
    private val requiredSubjectError = RequiredFieldError(MedicationRequest::subject)

    override fun validateElement(element: MedicationRequest, parentContext: LocationContext?, validation: Validation) {
        validation.apply {
            checkNotNull(element.status, requiredStatusError, parentContext)
            ifNotNull(element.status) {
                checkCodedEnum<MedicationRequestStatus>(
                    element.status,
                    InvalidValueSetError(MedicationRequest::status, element.status.value),
                    parentContext
                )
            }

            checkNotNull(element.intent, requiredIntentError, parentContext)
            ifNotNull(element.intent) {
                checkCodedEnum<MedicationRequestIntent>(
                    element.intent,
                    InvalidValueSetError(MedicationRequest::intent, element.intent.value),
                    parentContext
                )
            }

            element.priority?.let { code ->
                checkCodedEnum<RequestPriority>(
                    code,
                    InvalidValueSetError(MedicationRequest::priority, code.value),
                    parentContext
                )
            }

            element.reported?.let {
                checkTrue(acceptedReportedTypes.contains(it.type), invalidReportedError, parentContext)
            }

            checkNotNull(element.medication, requiredMedicationError, parentContext)
            ifNotNull(element.medication) {
                checkTrue(
                    acceptedMedicationTypes.contains(element.medication.type),
                    invalidMedicationError,
                    parentContext
                )
            }

            checkNotNull(element.subject, requiredSubjectError, parentContext)
        }
    }
}

/**
 * Validator for the [R4 Substitution](https://www.hl7.org/fhir/R4/medicationrequest-definitions.html#MedicationRequest.substitution)
 */
object R4SubstitutionValidator : R4ElementContainingValidator<Substitution>() {
    private val acceptedAllowedTypes = listOf(DynamicValueType.BOOLEAN, DynamicValueType.CODEABLE_CONCEPT)

    private val requiredAllowedError = RequiredFieldError(Substitution::allowed)
    private val invalidAllowedError = InvalidDynamicValueError(Substitution::allowed, acceptedAllowedTypes)

    override fun validateElement(element: Substitution, parentContext: LocationContext?, validation: Validation) {
        validation.apply {
            checkNotNull(element.allowed, requiredAllowedError, parentContext)
            ifNotNull(element.allowed) {
                checkTrue(acceptedAllowedTypes.contains(element.allowed.type), invalidAllowedError, parentContext)
            }
        }
    }
}
