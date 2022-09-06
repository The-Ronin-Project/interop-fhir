package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.BundleResponse
import com.projectronin.interop.fhir.r4.validate.R4ElementContainingValidator
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.RequiredFieldError
import com.projectronin.interop.fhir.validate.Validation

/**
 * Validator for the [R4 BundleResponse](http://hl7.org/fhir/R4/bundle-definitions.html#Bundle.entry.response)
 */
object R4BundleResponseValidator : R4ElementContainingValidator<BundleResponse>() {
    private val requiredStatusError = RequiredFieldError(BundleResponse::status)

    override fun validateElement(element: BundleResponse, parentContext: LocationContext?, validation: Validation) {
        validation.apply {
            checkNotNull(element.status, requiredStatusError, parentContext)
        }
    }
}
