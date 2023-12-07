package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.fhir.r4.resource.Observation
import com.projectronin.interop.fhir.r4.resource.ObservationComponent
import com.projectronin.interop.fhir.r4.resource.ObservationReferenceRange
import com.projectronin.interop.fhir.r4.validate.element.R4ElementContainingValidator
import com.projectronin.interop.fhir.validate.FHIRError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.Validation
import com.projectronin.interop.fhir.validate.ValidationIssueSeverity

/**
 * Validator for the [R4 Observation](http://hl7.org/fhir/R4/observation.html)
 */
object R4ObservationValidator : R4ElementContainingValidator<Observation>() {
    private val dataAbsentReasonOrValueError =
        FHIRError(
            code = "R4_OBS_001",
            severity = ValidationIssueSeverity.ERROR,
            description = "dataAbsentReason SHALL only be present if value[x] is not present",
            location = LocationContext(Observation::class),
        )

    @Suppress("ktlint:standard:max-line-length")
    private val invalidValueError =
        FHIRError(
            code = "R4_OBS_002",
            severity = ValidationIssueSeverity.ERROR,
            description = "If Observation.code is the same as an Observation.component.code then the Observation.value SHALL NOT be present",
            location = LocationContext(Observation::class),
        )

    override fun validateElement(
        element: Observation,
        parentContext: LocationContext?,
        validation: Validation,
    ) {
        validation.apply {
            checkTrue(
                (element.value == null || element.dataAbsentReason == null),
                dataAbsentReasonOrValueError,
                parentContext,
            )

            if (element.value != null) {
                checkTrue(
                    element.component.all { child -> (child.code != element.code) },
                    invalidValueError,
                    parentContext,
                )
            }
        }
    }
}

/**
 * Validator for the [R4 ObservationComponent](http://hl7.org/fhir/R4/observation-definitions.html#Observation.component)
 */
object R4ObservationComponentValidator : R4ElementContainingValidator<ObservationComponent>() {
    private val valueOrDataAbsentReasonError =
        FHIRError(
            code = "R4_OBSCOM_001",
            severity = ValidationIssueSeverity.ERROR,
            description = "dataAbsentReason SHALL only be present if value[x] is not present",
            location = LocationContext(ObservationComponent::class),
        )

    override fun validateElement(
        element: ObservationComponent,
        parentContext: LocationContext?,
        validation: Validation,
    ) {
        validation.apply {
            checkTrue(
                (element.value == null || element.dataAbsentReason == null),
                valueOrDataAbsentReasonError,
                parentContext,
            )
        }
    }
}

/**
 * Validator for the [R4 ObservationReferenceRange](http://hl7.org/fhir/R4/observation-definitions.html#Observation.referenceRange)
 */
object R4ObservationReferenceRangeValidator : R4ElementContainingValidator<ObservationReferenceRange>() {
    private val requiredDetailsError =
        FHIRError(
            code = "R4_OBSREFRNG_001",
            severity = ValidationIssueSeverity.ERROR,
            description = "referenceRange must have at least a low or a high or text",
            location = LocationContext(ObservationReferenceRange::class),
        )

    override fun validateElement(
        element: ObservationReferenceRange,
        parentContext: LocationContext?,
        validation: Validation,
    ) {
        validation.apply {
            checkTrue(
                ((element.low != null) || (element.high != null) || (element.text != null)),
                requiredDetailsError,
                parentContext,
            )
        }
    }
}
