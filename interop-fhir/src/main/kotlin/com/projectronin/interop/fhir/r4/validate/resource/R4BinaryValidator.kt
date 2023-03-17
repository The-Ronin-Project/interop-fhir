package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.fhir.r4.resource.Binary
import com.projectronin.interop.fhir.r4.validate.element.R4ElementContainingValidator
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.RequiredFieldError
import com.projectronin.interop.fhir.validate.Validation

/**
 * Validator for the [R4 Binary](https://hl7.org/fhir/r4/binary.html)
 */
object R4BinaryValidator : R4ElementContainingValidator<Binary>() {
    private val requiredContentTypeError = RequiredFieldError(Binary::contentType)

    override fun validateElement(element: Binary, parentContext: LocationContext?, validation: Validation) {
        validation.apply {
            checkNotNull(element.contentType, requiredContentTypeError, parentContext)

            // Technically there is a required binding for contentType as well, but it is all MIME types which is not a
            // practical item for us to check and validate.
        }
    }
}
