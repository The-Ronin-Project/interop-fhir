package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.EncounterDiagnosis
import com.projectronin.interop.fhir.r4.validate.R4ElementContainingValidator
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.RequiredFieldError
import com.projectronin.interop.fhir.validate.Validation

/**
 * Validator for
 * [R4 Encounter.diagnosis](https://hl7.org/fhir/R4/encounter-definitions.html#Encounter.diagnosis).
 */
object R4EncounterDiagnosisValidator : R4ElementContainingValidator<EncounterDiagnosis>() {
    private val requiredConditionError = RequiredFieldError(EncounterDiagnosis::condition)

    override fun validateElement(element: EncounterDiagnosis, parentContext: LocationContext?, validation: Validation) {
        validation.apply {
            checkNotNull(element.condition, requiredConditionError, parentContext)
        }
    }
}
