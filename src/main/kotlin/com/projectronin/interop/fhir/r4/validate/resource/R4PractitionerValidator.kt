package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.fhir.r4.resource.Practitioner
import com.projectronin.interop.fhir.r4.resource.Qualification
import com.projectronin.interop.fhir.r4.validate.element.R4ElementContainingValidator
import com.projectronin.interop.fhir.r4.valueset.AdministrativeGender
import com.projectronin.interop.fhir.validate.InvalidValueSetError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.RequiredFieldError
import com.projectronin.interop.fhir.validate.Validation

/**
 * Validator for the [R4 Practitioner](http://hl7.org/fhir/R4/practitioner.html)
 */
object R4PractitionerValidator : R4ElementContainingValidator<Practitioner>() {
    override fun validateElement(element: Practitioner, parentContext: LocationContext?, validation: Validation) {
        validation.apply {
            element.gender?.let { code ->
                checkCodedEnum<AdministrativeGender>(
                    code,
                    InvalidValueSetError(Practitioner::gender, code.value),
                    parentContext
                )
            }
        }
    }
}

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
