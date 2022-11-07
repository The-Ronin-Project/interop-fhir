package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.Period
import com.projectronin.interop.fhir.r4.validate.R4ElementContainingValidator
import com.projectronin.interop.fhir.validate.FHIRError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.Validation
import com.projectronin.interop.fhir.validate.ValidationIssueSeverity

/**
 * Validator for the [R4 Period](http://hl7.org/fhir/R4/datatypes.html#Period).
 */
object R4PeriodValidator : R4ElementContainingValidator<Period>() {
    private val startBeforeEndError = FHIRError(
        code = "R4_PRD_001",
        severity = ValidationIssueSeverity.ERROR,
        description = "start SHALL have a lower value than end",
        location = LocationContext(Period::class)
    )

    override fun validateElement(element: Period, parentContext: LocationContext?, validation: Validation) {
        validation.apply {
            element.start?.value?.let { start ->
                element.end?.value?.let { end ->
                    // Due to the formatting restrictions on DateTime, we are safe to do direct comparisons like this.
                    // We include equal to account for less than precise times, such as "2018-2018"
                    checkTrue((start <= end), startBeforeEndError, parentContext)
                }
            }
        }
    }
}
