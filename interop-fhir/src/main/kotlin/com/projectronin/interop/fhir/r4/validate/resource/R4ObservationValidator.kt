package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.resource.Observation
import com.projectronin.interop.fhir.r4.resource.ObservationComponent
import com.projectronin.interop.fhir.r4.resource.ObservationReferenceRange
import com.projectronin.interop.fhir.r4.validate.element.R4ElementContainingValidator
import com.projectronin.interop.fhir.validate.FHIRError
import com.projectronin.interop.fhir.validate.InvalidDynamicValueError
import com.projectronin.interop.fhir.validate.LocationContext
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

/**
 * Validator for the [R4 ObservationComponent](http://hl7.org/fhir/R4/observation-definitions.html#Observation.component)
 */
object R4ObservationComponentValidator : R4ElementContainingValidator<ObservationComponent>() {
    private val acceptedValues = listOf(
        DynamicValueType.QUANTITY,
        DynamicValueType.CODEABLE_CONCEPT,
        DynamicValueType.STRING,
        DynamicValueType.BOOLEAN,
        DynamicValueType.INTEGER,
        DynamicValueType.RANGE,
        DynamicValueType.RATIO,
        DynamicValueType.SAMPLED_DATA,
        DynamicValueType.TIME,
        DynamicValueType.DATE_TIME,
        DynamicValueType.PERIOD
    )

    private val invalidValueError = InvalidDynamicValueError(ObservationComponent::value, acceptedValues)
    private val valueOrDataAbsentReasonError = FHIRError(
        code = "R4_OBSCOM_001",
        severity = ValidationIssueSeverity.ERROR,
        description = "dataAbsentReason SHALL only be present if value[x] is not present",
        location = LocationContext(ObservationComponent::class)
    )

    override fun validateElement(
        element: ObservationComponent,
        parentContext: LocationContext?,
        validation: Validation
    ) {
        validation.apply {
            element.value?.let { value ->
                checkTrue(acceptedValues.contains(value.type), invalidValueError, parentContext)
            }

            checkTrue(
                (element.value == null || element.dataAbsentReason == null),
                valueOrDataAbsentReasonError,
                parentContext
            )
        }
    }
}

/**
 * Validator for the [R4 ObservationReferenceRange](http://hl7.org/fhir/R4/observation-definitions.html#Observation.referenceRange)
 */
object R4ObservationReferenceRangeValidator : R4ElementContainingValidator<ObservationReferenceRange>() {
    private val requiredDetailsError = FHIRError(
        code = "R4_OBSREFRNG_001",
        severity = ValidationIssueSeverity.ERROR,
        description = "referenceRange must have at least a low or a high or text",
        location = LocationContext(ObservationReferenceRange::class)
    )

    override fun validateElement(
        element: ObservationReferenceRange,
        parentContext: LocationContext?,
        validation: Validation
    ) {
        validation.apply {
            checkTrue(
                ((element.low != null) || (element.high != null) || (element.text != null)),
                requiredDetailsError,
                parentContext
            )
        }
    }
}
