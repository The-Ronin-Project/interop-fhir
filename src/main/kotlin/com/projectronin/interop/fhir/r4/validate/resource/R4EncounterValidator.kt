package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.fhir.r4.resource.Encounter
import com.projectronin.interop.fhir.r4.validate.R4ElementContainingValidator
import com.projectronin.interop.fhir.r4.valueset.EncounterStatus
import com.projectronin.interop.fhir.validate.InvalidValueSetError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.RequiredFieldError
import com.projectronin.interop.fhir.validate.Validation

/**
 * Validator for the [R4 Encounter](http://hl7.org/fhir/R4/encounter.html)
 */
object R4EncounterValidator : R4ElementContainingValidator<Encounter>() {
    private val requiredStatusError = RequiredFieldError(Encounter::status)
    private val requiredClassError = RequiredFieldError(Encounter::`class`)

    override fun validateElement(element: Encounter, parentContext: LocationContext?, validation: Validation) {
        validation.apply {

            checkNotNull(element.status, requiredStatusError, parentContext)
            ifNotNull(element.status) {
                checkCodedEnum<EncounterStatus>(
                    element.status,
                    InvalidValueSetError(Encounter::status, element.status.value),
                    parentContext
                )
            }

            checkNotNull(element.`class`, requiredClassError, parentContext)
        }
    }
}
