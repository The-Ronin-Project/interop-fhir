package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.resource.MedicationRequest
import com.projectronin.interop.fhir.r4.validate.R4ElementContainingValidator
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
    private val invalidStatusError = InvalidValueSetError(MedicationRequest::status)
    private val requiredIntentError = RequiredFieldError(MedicationRequest::intent)
    private val invalidIntentError = InvalidValueSetError(MedicationRequest::intent)
    private val invalidPriorityError = InvalidValueSetError(MedicationRequest::priority)
    private val invalidReportedError = InvalidDynamicValueError(MedicationRequest::reported, acceptedReportedTypes)
    private val requiredMedicationError = RequiredFieldError(MedicationRequest::medication)
    private val invalidMedicationError =
        InvalidDynamicValueError(MedicationRequest::medication, acceptedMedicationTypes)
    private val requiredSubjectError = RequiredFieldError(MedicationRequest::subject)

    override fun validateElement(element: MedicationRequest, parentContext: LocationContext?, validation: Validation) {
        validation.apply {
            checkNotNull(element.status, requiredStatusError, parentContext)
            ifNotNull(element.status) {
                checkCodedEnum<MedicationRequestStatus>(element.status, invalidStatusError, parentContext)
            }

            checkNotNull(element.intent, requiredIntentError, parentContext)
            ifNotNull(element.intent) {
                checkCodedEnum<MedicationRequestIntent>(element.intent, invalidIntentError, parentContext)
            }

            element.priority?.let {
                checkCodedEnum<RequestPriority>(it, invalidPriorityError, parentContext)
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
