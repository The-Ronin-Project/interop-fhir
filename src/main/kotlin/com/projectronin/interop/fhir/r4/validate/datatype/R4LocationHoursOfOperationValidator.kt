package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.common.enums.CodedEnum
import com.projectronin.interop.fhir.r4.datatype.LocationHoursOfOperation
import com.projectronin.interop.fhir.r4.validate.R4ElementContainingValidator
import com.projectronin.interop.fhir.r4.valueset.DayOfWeek
import com.projectronin.interop.fhir.validate.InvalidValueSetError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.Validation

/**
 * Validator for the [R4 LocationHoursOfOperation](http://hl7.org/fhir/R4/location-definitions.html#Location.hoursOfOperation)
 */
object R4LocationHoursOfOperationValidator : R4ElementContainingValidator<LocationHoursOfOperation>() {
    private val invalidDayOfWeekError = InvalidValueSetError(LocationHoursOfOperation::daysOfWeek)

    override fun validateElement(
        element: LocationHoursOfOperation,
        parentContext: LocationContext?,
        validation: Validation
    ) {
        validation.apply {
            checkTrue(
                element.daysOfWeek.none { runCatching { it.value?.let { it1 -> CodedEnum.byCode<DayOfWeek>(it1) } }.getOrNull() == null },
                invalidDayOfWeekError,
                parentContext
            )
        }
    }
}
