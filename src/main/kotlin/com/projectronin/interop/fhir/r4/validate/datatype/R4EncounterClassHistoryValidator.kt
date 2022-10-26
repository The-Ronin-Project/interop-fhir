package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.EncounterClassHistory
import com.projectronin.interop.fhir.r4.validate.R4ElementContainingValidator
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.RequiredFieldError
import com.projectronin.interop.fhir.validate.Validation

/**
 * Validator for
 * [R4 Encounter.classHistory](https://hl7.org/fhir/R4/encounter-definitions.html#Encounter.classHistory).
 */
object R4EncounterClassHistoryValidator : R4ElementContainingValidator<EncounterClassHistory>() {
    private val requiredClassError = RequiredFieldError(EncounterClassHistory::`class`)
    private val requiredPeriodError = RequiredFieldError(EncounterClassHistory::period)

    override fun validateElement(element: EncounterClassHistory, parentContext: LocationContext?, validation: Validation) {
        validation.apply {

            checkNotNull(element.`class`, requiredClassError, parentContext)

            checkNotNull(element.period, requiredPeriodError, parentContext)
        }
    }
}
