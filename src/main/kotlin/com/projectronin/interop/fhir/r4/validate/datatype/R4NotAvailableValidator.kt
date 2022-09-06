package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.NotAvailable
import com.projectronin.interop.fhir.r4.validate.R4ElementContainingValidator
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.RequiredFieldError
import com.projectronin.interop.fhir.validate.Validation

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
