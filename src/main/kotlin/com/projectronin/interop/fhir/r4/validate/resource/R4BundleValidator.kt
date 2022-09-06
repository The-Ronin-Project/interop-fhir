package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.fhir.r4.resource.Bundle
import com.projectronin.interop.fhir.r4.validate.R4ElementContainingValidator
import com.projectronin.interop.fhir.r4.valueset.BundleType
import com.projectronin.interop.fhir.validate.InvalidValueSetError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.RequiredFieldError
import com.projectronin.interop.fhir.validate.Validation

/**
 * Validator for the [R4 Bundle](http://hl7.org/fhir/R4/bundle.html)
 */
object R4BundleValidator : R4ElementContainingValidator<Bundle>() {
    private val requiredTypeError = RequiredFieldError(Bundle::type)
    private val invalidTypeError = InvalidValueSetError(Bundle::type)

    override fun validateElement(element: Bundle, parentContext: LocationContext?, validation: Validation) {
        validation.apply {
            checkNotNull(element.type, requiredTypeError, parentContext)

            ifNotNull(element.type) {
                checkCodedEnum<BundleType>(element.type, invalidTypeError, parentContext)
            }
        }
    }
}
