package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.BundleRequest
import com.projectronin.interop.fhir.r4.validate.R4ElementContainingValidator
import com.projectronin.interop.fhir.r4.valueset.HttpVerb
import com.projectronin.interop.fhir.validate.InvalidValueSetError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.RequiredFieldError
import com.projectronin.interop.fhir.validate.Validation

/**
 * Validator for the [R4 BundleRequest](http://hl7.org/fhir/R4/bundle-definitions.html#Bundle.entry.request).
 */
object R4BundleRequestValidator : R4ElementContainingValidator<BundleRequest>() {
    private val requiredMethodError = RequiredFieldError(BundleRequest::method)
    private val invalidMethodError = InvalidValueSetError(BundleRequest::method)
    private val requiredUrlError = RequiredFieldError(BundleRequest::url)

    override fun validateElement(element: BundleRequest, parentContext: LocationContext?, validation: Validation) {
        validation.apply {
            checkNotNull(element.method, requiredMethodError, parentContext)

            ifNotNull(element.method) {
                checkCodedEnum<HttpVerb>(element.method, invalidMethodError, parentContext)
            }

            checkNotNull(element.url, requiredUrlError, parentContext)
        }
    }
}
