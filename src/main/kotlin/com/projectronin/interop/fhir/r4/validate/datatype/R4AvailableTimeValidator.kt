package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.common.enums.CodedEnum
import com.projectronin.interop.fhir.r4.datatype.AvailableTime
import com.projectronin.interop.fhir.r4.validate.R4ElementContainingValidator
import com.projectronin.interop.fhir.r4.valueset.DayOfWeek
import com.projectronin.interop.fhir.validate.InvalidValueSetError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.Validation

/**
 * Validator for the [R4 AvailableTime](http://hl7.org/fhir/R4/practitionerrole-definitions.html#PractitionerRole.availableTime)
 */
object R4AvailableTimeValidator : R4ElementContainingValidator<AvailableTime>() {
    private val invalidDayOfWeekError = InvalidValueSetError(AvailableTime::daysOfWeek)

    override fun validateElement(element: AvailableTime, parentContext: LocationContext?, validation: Validation) {
        validation.apply {
            checkTrue(
                element.daysOfWeek.none { runCatching { CodedEnum.byCode<DayOfWeek>(it.value) }.getOrNull() == null },
                invalidDayOfWeekError,
                parentContext
            )
        }
    }
}
