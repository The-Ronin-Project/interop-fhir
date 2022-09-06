package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.fhir.r4.resource.Practitioner
import com.projectronin.interop.fhir.r4.validate.R4ElementContainingValidator
import com.projectronin.interop.fhir.r4.valueset.AdministrativeGender
import com.projectronin.interop.fhir.validate.InvalidValueSetError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.Validation

/**
 * Validator for the [R4 Practitioner](http://hl7.org/fhir/R4/practitioner.html)
 */
object R4PractitionerValidator : R4ElementContainingValidator<Practitioner>() {
    private val invalidGenderError = InvalidValueSetError(Practitioner::gender)

    override fun validateElement(element: Practitioner, parentContext: LocationContext?, validation: Validation) {
        validation.apply {
            element.gender?.let {
                checkCodedEnum<AdministrativeGender>(it, invalidGenderError, parentContext)
            }
        }
    }
}
