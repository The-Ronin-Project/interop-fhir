package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.EncounterStatusHistory
import com.projectronin.interop.fhir.r4.validate.R4ElementContainingValidator
import com.projectronin.interop.fhir.r4.valueset.EncounterStatus
import com.projectronin.interop.fhir.validate.InvalidValueSetError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.RequiredFieldError
import com.projectronin.interop.fhir.validate.Validation

/**
 * Validator for
 * [R4 Encounter.statusHistory](https://hl7.org/fhir/R4/encounter-definitions.html#Encounter.statusHistory).
 */
object R4EncounterStatusHistoryValidator : R4ElementContainingValidator<EncounterStatusHistory>() {
    private val requiredStatusError = RequiredFieldError(EncounterStatusHistory::status)
    private val invalidStatusError = InvalidValueSetError(EncounterStatusHistory::status)
    private val requiredPeriodError = RequiredFieldError(EncounterStatusHistory::period)

    override fun validateElement(
        element: EncounterStatusHistory,
        parentContext: LocationContext?,
        validation: Validation
    ) {
        validation.apply {

            checkNotNull(element.status, requiredStatusError, parentContext)
            ifNotNull(element.status) {
                checkCodedEnum<EncounterStatus>(element.status, invalidStatusError, parentContext)
            }

            checkNotNull(element.period, requiredPeriodError, parentContext)
        }
    }
}
