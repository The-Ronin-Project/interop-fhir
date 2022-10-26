package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.EncounterLocation
import com.projectronin.interop.fhir.r4.validate.R4ElementContainingValidator
import com.projectronin.interop.fhir.r4.valueset.EncounterLocationStatus
import com.projectronin.interop.fhir.validate.InvalidValueSetError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.RequiredFieldError
import com.projectronin.interop.fhir.validate.Validation

/**
 * Validator for
 * [R4 Encounter.location](https://hl7.org/fhir/R4/encounter-definitions.html#Encounter.location).
 */
object R4EncounterLocationValidator : R4ElementContainingValidator<EncounterLocation>() {
    private val requiredLocationError = RequiredFieldError(EncounterLocation::location)
    private val invalidLocationStatusError = InvalidValueSetError(EncounterLocation::status)

    override fun validateElement(element: EncounterLocation, parentContext: LocationContext?, validation: Validation) {
        validation.apply {

            checkNotNull(element.location, requiredLocationError, parentContext)

            ifNotNull(element.status) {
                checkCodedEnum<EncounterLocationStatus>(element.status!!, invalidLocationStatusError, parentContext)
            }
        }
    }
}
