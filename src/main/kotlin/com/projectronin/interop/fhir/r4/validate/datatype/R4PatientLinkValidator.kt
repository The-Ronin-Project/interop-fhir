package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.PatientLink
import com.projectronin.interop.fhir.r4.validate.R4ElementContainingValidator
import com.projectronin.interop.fhir.r4.valueset.LinkType
import com.projectronin.interop.fhir.validate.InvalidValueSetError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.RequiredFieldError
import com.projectronin.interop.fhir.validate.Validation

/**
 * Validator for the [R4 PatientLink](http://hl7.org/fhir/R4/patient-definitions.html#Patient.link).
 */
object R4PatientLinkValidator : R4ElementContainingValidator<PatientLink>() {
    private val requiredOtherError = RequiredFieldError(PatientLink::other)
    private val requiredTypeError = RequiredFieldError(PatientLink::type)
    private val invalidTypeError = InvalidValueSetError(PatientLink::type)

    override fun validateElement(element: PatientLink, parentContext: LocationContext?, validation: Validation) {
        validation.apply {
            checkNotNull(element.other, requiredOtherError, parentContext)
            checkNotNull(element.type, requiredTypeError, parentContext)

            ifNotNull(element.type) {
                checkCodedEnum<LinkType>(element.type, invalidTypeError, parentContext)
            }
        }
    }
}
