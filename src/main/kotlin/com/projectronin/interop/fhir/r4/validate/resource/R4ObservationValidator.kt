package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.resource.Observation
import com.projectronin.interop.fhir.r4.validate.R4ElementContainingValidator
import com.projectronin.interop.fhir.r4.valueset.ObservationStatus
import com.projectronin.interop.fhir.validate.FHIRError
import com.projectronin.interop.fhir.validate.InvalidDynamicValueError
import com.projectronin.interop.fhir.validate.InvalidValueSetError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.RequiredFieldError
import com.projectronin.interop.fhir.validate.Validation
import com.projectronin.interop.fhir.validate.ValidationIssueSeverity

/**
 * Validator for the [R4 Observation](http://hl7.org/fhir/R4/observation.html)
 */
object R4ObservationValidator : R4ElementContainingValidator<Observation>() {
    private val acceptedEffectives = listOf(
        DynamicValueType.DATE_TIME,
        DynamicValueType.PERIOD,
        DynamicValueType.TIMING,
        DynamicValueType.INSTANT
    )

    private val requiredStatusError = RequiredFieldError(Observation::status)
    private val requiredCodeError = RequiredFieldError(Observation::code)
    private val invalidEffectiveError = InvalidDynamicValueError(Observation::effective, acceptedEffectives)

    private val dataAbsentReasonOrValueError = FHIRError(
        code = "R4_OBS_001",
        severity = ValidationIssueSeverity.ERROR,
        description = "dataAbsentReason SHALL only be present if value[x] is not present",
        location = LocationContext(Observation::class)
    )
    private val invalidValueError = FHIRError(
        code = "R4_OBS_002",
        severity = ValidationIssueSeverity.ERROR,
        description = "If Observation.code is the same as an Observation.component.code then the Observation.value SHALL NOT be present",
        location = LocationContext(Observation::class)
    )

    override fun validateElement(element: Observation, parentContext: LocationContext?, validation: Validation) {
        validation.apply {
            checkNotNull(element.status, requiredStatusError, parentContext)

            ifNotNull(element.status) {
                checkCodedEnum<ObservationStatus>(
                    element.status,
                    InvalidValueSetError(Observation::status, element.status.value),
                    parentContext
                )
            }

            checkNotNull(element.code, requiredCodeError, parentContext)

            // R4 Observation.value[x] data types are constrained by the ObservationStatus enum type, so no validation needed.
            element.effective?.let { data ->
                checkTrue(acceptedEffectives.contains(data.type), invalidEffectiveError, parentContext)
            }

            checkTrue(
                (element.value == null || element.dataAbsentReason == null),
                dataAbsentReasonOrValueError,
                parentContext
            )

            if (element.value != null) {
                checkTrue(
                    element.component.all { child -> (child.code != element.code) },
                    invalidValueError,
                    parentContext
                )
            }
        }
    }
}
