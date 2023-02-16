package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.fhir.r4.resource.Encounter
import com.projectronin.interop.fhir.r4.resource.EncounterClassHistory
import com.projectronin.interop.fhir.r4.resource.EncounterDiagnosis
import com.projectronin.interop.fhir.r4.resource.EncounterLocation
import com.projectronin.interop.fhir.r4.resource.EncounterStatusHistory
import com.projectronin.interop.fhir.r4.validate.element.R4ElementContainingValidator
import com.projectronin.interop.fhir.r4.valueset.EncounterLocationStatus
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

/**
 * Validator for
 * [R4 Encounter.location](https://hl7.org/fhir/R4/encounter-definitions.html#Encounter.location).
 */
object R4EncounterLocationValidator : R4ElementContainingValidator<EncounterLocation>() {
    private val requiredLocationError = RequiredFieldError(EncounterLocation::location)

    override fun validateElement(element: EncounterLocation, parentContext: LocationContext?, validation: Validation) {
        validation.apply {

            checkNotNull(element.location, requiredLocationError, parentContext)

            ifNotNull(element.status) {
                checkCodedEnum<EncounterLocationStatus>(
                    element.status!!,
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
    private val requiredStatusError = RequiredFieldError(EncounterStatusHistory::status)
    private val requiredPeriodError = RequiredFieldError(EncounterStatusHistory::period)

    override fun validateElement(
        element: EncounterStatusHistory,
        parentContext: LocationContext?,
        validation: Validation
    ) {
        validation.apply {

            checkNotNull(element.status, requiredStatusError, parentContext)
            ifNotNull(element.status) {
                checkCodedEnum<EncounterStatus>(
                    element.status,
                    InvalidValueSetError(EncounterStatusHistory::status, element.status.value),
                    parentContext
                )
            }

            checkNotNull(element.period, requiredPeriodError, parentContext)
        }
    }
}
