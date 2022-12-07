package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.resource.MedicationStatement
import com.projectronin.interop.fhir.r4.validate.R4ElementContainingValidator
import com.projectronin.interop.fhir.r4.valueset.MedicationStatementStatus
import com.projectronin.interop.fhir.validate.InvalidDynamicValueError
import com.projectronin.interop.fhir.validate.InvalidValueSetError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.RequiredFieldError
import com.projectronin.interop.fhir.validate.Validation

object R4MedicationStatementValidator : R4ElementContainingValidator<MedicationStatement>() {
    private val acceptedMedications = listOf(DynamicValueType.CODEABLE_CONCEPT, DynamicValueType.REFERENCE)
    private val acceptedEffectives = listOf(DynamicValueType.DATE_TIME, DynamicValueType.PERIOD)

    private val requiredStatusError = RequiredFieldError(MedicationStatement::status)

    private val requiredMedicationError = RequiredFieldError(MedicationStatement::medication)
    private val invalidMedicationError = InvalidDynamicValueError(MedicationStatement::medication, acceptedMedications)

    private val requiredSubjectError = RequiredFieldError(MedicationStatement::subject)

    private val invalidEffectiveError = InvalidDynamicValueError(MedicationStatement::effective, acceptedEffectives)

    override fun validateElement(element: MedicationStatement, parentContext: LocationContext?, validation: Validation) {
        validation.apply {
            checkNotNull(element.status, requiredStatusError, parentContext)
            ifNotNull(element.status) {
                checkCodedEnum<MedicationStatementStatus>(
                    element.status,
                    InvalidValueSetError(MedicationStatement::status, element.status.value),
                    parentContext
                )
            }

            checkNotNull(element.medication, requiredMedicationError, parentContext)
            ifNotNull(element.medication) {
                checkTrue(acceptedMedications.contains(element.medication.type), invalidMedicationError, parentContext)
            }

            checkNotNull(element.subject, requiredSubjectError, parentContext)

            element.effective?.let { data ->
                checkTrue(acceptedEffectives.contains(data.type), invalidEffectiveError, parentContext)
            }
        }
    }
}
