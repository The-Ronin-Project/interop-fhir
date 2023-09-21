package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.fhir.r4.resource.Encounter
import com.projectronin.interop.fhir.r4.resource.EncounterLocation
import com.projectronin.interop.fhir.r4.resource.EncounterStatusHistory
import com.projectronin.interop.fhir.r4.validate.element.R4ElementContainingValidator
import com.projectronin.interop.fhir.r4.valueset.EncounterLocationStatus
import com.projectronin.interop.fhir.r4.valueset.EncounterStatus
import com.projectronin.interop.fhir.validate.InvalidValueSetError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.Validation

/**
 * Validator for the [R4 Encounter](http://hl7.org/fhir/R4/encounter.html)
 */
object R4EncounterValidator : R4ElementContainingValidator<Encounter>() {
    override fun validateElement(element: Encounter, parentContext: LocationContext?, validation: Validation) {
        validation.apply {
            element.status?.let {
                checkCodedEnum<EncounterStatus>(
                    element.status,
                    InvalidValueSetError(Encounter::status, element.status.value),
                    parentContext
                )
            }
        }
    }
}

/**
 * Validator for
 * [R4 Encounter.location](https://hl7.org/fhir/R4/encounter-definitions.html#Encounter.location).
 */
object R4EncounterLocationValidator : R4ElementContainingValidator<EncounterLocation>() {

    override fun validateElement(element: EncounterLocation, parentContext: LocationContext?, validation: Validation) {
        validation.apply {
            element.status?.let {
                checkCodedEnum<EncounterLocationStatus>(
                    element.status,
                    InvalidValueSetError(EncounterLocation::status, element.status.value),
                    parentContext
                )
            }
        }
    }
}

/**
 * Validator for
 * [R4 Encounter.statusHistory](https://hl7.org/fhir/R4/encounter-definitions.html#Encounter.statusHistory).
 */
object R4EncounterStatusHistoryValidator : R4ElementContainingValidator<EncounterStatusHistory>() {
    override fun validateElement(
        element: EncounterStatusHistory,
        parentContext: LocationContext?,
        validation: Validation
    ) {
        validation.apply {
            element.status?.let {
                checkCodedEnum<EncounterStatus>(
                    element.status,
                    InvalidValueSetError(EncounterStatusHistory::status, element.status.value),
                    parentContext
                )
            }
        }
    }
}
