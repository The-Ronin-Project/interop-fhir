package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.common.enums.CodedEnum
import com.projectronin.interop.fhir.r4.resource.Location
import com.projectronin.interop.fhir.r4.resource.LocationHoursOfOperation
import com.projectronin.interop.fhir.r4.validate.element.R4ElementContainingValidator
import com.projectronin.interop.fhir.r4.valueset.DayOfWeek
import com.projectronin.interop.fhir.r4.valueset.LocationMode
import com.projectronin.interop.fhir.r4.valueset.LocationStatus
import com.projectronin.interop.fhir.validate.InvalidValueSetError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.Validation

/**
 * Validator for the [R4 Location](http://hl7.org/fhir/R4/location.html)
 */
object R4LocationValidator : R4ElementContainingValidator<Location>() {
    override fun validateElement(element: Location, parentContext: LocationContext?, validation: Validation) {
        validation.apply {
            element.status?.let { code ->
                checkCodedEnum<LocationStatus>(code, InvalidValueSetError(Location::status, code.value), parentContext)
            }

            element.mode?.let { code ->
                checkCodedEnum<LocationMode>(code, InvalidValueSetError(Location::mode, code.value), parentContext)
            }
        }
    }
}

/**
 * Validator for the [R4 LocationHoursOfOperation](http://hl7.org/fhir/R4/location-definitions.html#Location.hoursOfOperation)
 */
object R4LocationHoursOfOperationValidator : R4ElementContainingValidator<LocationHoursOfOperation>() {

    override fun validateElement(
        element: LocationHoursOfOperation,
        parentContext: LocationContext?,
        validation: Validation
    ) {
        validation.apply {
            element.daysOfWeek.let {
                val invalidDayOfWeekCodes =
                    element.daysOfWeek.filter { runCatching { it.value?.let { it1 -> CodedEnum.byCode<DayOfWeek>(it1) } }.getOrNull() == null }
                checkTrue(
                    invalidDayOfWeekCodes.isNullOrEmpty(),
                    InvalidValueSetError(
                        LocationHoursOfOperation::daysOfWeek,
                        invalidDayOfWeekCodes.joinToString { it.value!! }
                    ),
                    parentContext
                )
            }
        }
    }
}
