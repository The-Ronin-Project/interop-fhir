package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.common.enums.CodedEnum
import com.projectronin.interop.fhir.r4.resource.AvailableTime
import com.projectronin.interop.fhir.r4.resource.NotAvailable
import com.projectronin.interop.fhir.r4.resource.PractitionerRole
import com.projectronin.interop.fhir.r4.validate.element.R4ElementContainingValidator
import com.projectronin.interop.fhir.r4.valueset.DayOfWeek
import com.projectronin.interop.fhir.validate.InvalidValueSetError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.RequiredFieldError
import com.projectronin.interop.fhir.validate.Validation

/**
 * Validator for the [R4 PractitionerRole](http://hl7.org/fhir/R4/practitionerrole.html)
 */
object R4PractitionerRoleValidator : R4ElementContainingValidator<PractitionerRole>() {
    override fun validateElement(element: PractitionerRole, parentContext: LocationContext?, validation: Validation) {
        // PractitionerRole has no Validation it needs to do itself, but it should still evaluate its contained elements.
    }
}

/**
 * Validator for the [R4 AvailableTime](http://hl7.org/fhir/R4/practitionerrole-definitions.html#PractitionerRole.availableTime)
 */
object R4AvailableTimeValidator : R4ElementContainingValidator<AvailableTime>() {
    override fun validateElement(element: AvailableTime, parentContext: LocationContext?, validation: Validation) {
        validation.apply {
            element.daysOfWeek.let {
                val invalidDayOfWeekCodes =
                    element.daysOfWeek.filter { runCatching { it.value?.let { it1 -> CodedEnum.byCode<DayOfWeek>(it1) } }.getOrNull() == null }
                checkTrue(
                    invalidDayOfWeekCodes.isNullOrEmpty(),
                    InvalidValueSetError(AvailableTime::daysOfWeek, invalidDayOfWeekCodes.joinToString { it.value!! }),
                    parentContext
                )
            }
        }
    }
}

/**
 * Validator for the [R4 NotAvailable](http://hl7.org/fhir/R4/practitionerrole-definitions.html#PractitionerRole.notAvailable).
 */
object R4NotAvailableValidator : R4ElementContainingValidator<NotAvailable>() {
    private val requiredNotAvailableError = RequiredFieldError(NotAvailable::description)

    override fun validateElement(element: NotAvailable, parentContext: LocationContext?, validation: Validation) {
        validation.apply {
            checkNotNull(element.description, requiredNotAvailableError, parentContext)
        }
    }
}
