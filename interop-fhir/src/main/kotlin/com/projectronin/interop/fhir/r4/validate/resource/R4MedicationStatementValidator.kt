package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.resource.MedicationStatement
import com.projectronin.interop.fhir.r4.validate.element.R4ElementContainingValidator
import com.projectronin.interop.fhir.r4.valueset.MedicationStatementStatus
import com.projectronin.interop.fhir.validate.InvalidDynamicValueError
import com.projectronin.interop.fhir.validate.InvalidValueSetError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.Validation

object R4MedicationStatementValidator : R4ElementContainingValidator<MedicationStatement>() {
    private val acceptedMedications = listOf(DynamicValueType.CODEABLE_CONCEPT, DynamicValueType.REFERENCE)
    private val acceptedEffectives = listOf(DynamicValueType.DATE_TIME, DynamicValueType.PERIOD)

    private val invalidMedicationError = InvalidDynamicValueError(MedicationStatement::medication, acceptedMedications)
    private val invalidEffectiveError = InvalidDynamicValueError(MedicationStatement::effective, acceptedEffectives)

    override fun validateElement(
        element: MedicationStatement,
        parentContext: LocationContext?,
        validation: Validation
    ) {
        validation.apply {
            element.status?.let {
                checkCodedEnum<MedicationStatementStatus>(
                    element.status,
                    InvalidValueSetError(MedicationStatement::status, element.status.value),
                    parentContext
                )
            }

            element.medication?.let {
                checkTrue(acceptedMedications.contains(element.medication.type), invalidMedicationError, parentContext)
            }

            element.effective?.let { data ->
                checkTrue(acceptedEffectives.contains(data.type), invalidEffectiveError, parentContext)
            }
        }
    }
}
