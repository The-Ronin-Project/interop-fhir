package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.Range
import com.projectronin.interop.fhir.r4.validate.R4ElementContainingValidator
import com.projectronin.interop.fhir.validate.FHIRError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.Validation
import com.projectronin.interop.fhir.validate.ValidationIssueSeverity

/**
 * Validator for the [R4 Range](http://hl7.org/fhir/R4/datatypes.html#Range)
 */
object R4RangeValidator : R4ElementContainingValidator<Range>() {
    private val lowLowerThanHighError = FHIRError(
        code = "R4_RANGE_001",
        severity = ValidationIssueSeverity.ERROR,
        description = "If present, low SHALL have a lower value than high",
        location = LocationContext(Range::class)
    )

    override fun validateElement(element: Range, parentContext: LocationContext?, validation: Validation) {
        validation.apply {
            checkTrue(
                (element.low == null || element.high == null || (element.low.value?.value != null && element.high.value?.value != null && element.low.value.value < element.high.value.value)),
                lowLowerThanHighError,
                parentContext
            )
        }
    }
}
