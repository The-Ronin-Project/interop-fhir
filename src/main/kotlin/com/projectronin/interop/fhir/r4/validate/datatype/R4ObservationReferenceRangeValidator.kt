package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.ObservationReferenceRange
import com.projectronin.interop.fhir.r4.validate.R4ElementContainingValidator
import com.projectronin.interop.fhir.validate.FHIRError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.Validation
import com.projectronin.interop.fhir.validate.ValidationIssueSeverity

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
