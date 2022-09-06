package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.Qualification
import com.projectronin.interop.fhir.r4.validate.R4ElementContainingValidator
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.RequiredFieldError
import com.projectronin.interop.fhir.validate.Validation

/**
 * Validator for the [R4 Qualification](http://hl7.org/fhir/R4/practitioner-definitions.html#Practitioner.qualification).
 */
object R4QualificationValidator : R4ElementContainingValidator<Qualification>() {
    private val requiredCodeError = RequiredFieldError(Qualification::code)

    override fun validateElement(element: Qualification, parentContext: LocationContext?, validation: Validation) {
        validation.apply {
            checkNotNull(element.code, requiredCodeError, parentContext)
        }
    }
}
