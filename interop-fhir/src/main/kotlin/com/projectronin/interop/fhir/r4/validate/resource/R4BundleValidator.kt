package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.fhir.r4.resource.Bundle
import com.projectronin.interop.fhir.r4.resource.BundleRequest
import com.projectronin.interop.fhir.r4.resource.BundleSearch
import com.projectronin.interop.fhir.r4.validate.element.R4ElementContainingValidator
import com.projectronin.interop.fhir.r4.valueset.BundleType
import com.projectronin.interop.fhir.r4.valueset.HttpVerb
import com.projectronin.interop.fhir.r4.valueset.SearchEntryMode
import com.projectronin.interop.fhir.validate.InvalidValueSetError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.Validation

/**
 * Validator for the [R4 Bundle](http://hl7.org/fhir/R4/bundle.html)
 */
object R4BundleValidator : R4ElementContainingValidator<Bundle>() {
    override fun validateElement(element: Bundle, parentContext: LocationContext?, validation: Validation) {
        validation.apply {
            element.type?.let {
                checkCodedEnum<BundleType>(
                    element.type,
                    InvalidValueSetError(Bundle::type, element.type.value),
                    parentContext
                )
            }
        }
    }
}

/**
 * Validator for the [R4 BundleRequest](http://hl7.org/fhir/R4/bundle-definitions.html#Bundle.entry.request).
 */
object R4BundleRequestValidator : R4ElementContainingValidator<BundleRequest>() {
    override fun validateElement(element: BundleRequest, parentContext: LocationContext?, validation: Validation) {
        validation.apply {
            element.method?.let {
                checkCodedEnum<HttpVerb>(
                    element.method,
                    InvalidValueSetError(BundleRequest::method, element.method.value),
                    parentContext
                )
            }
        }
    }
}

/**
 * Validator for the [R4 BundleSearch](http://hl7.org/fhir/R4/bundle-definitions.html#Bundle.entry.search).
 */
object R4BundleSearchValidator : R4ElementContainingValidator<BundleSearch>() {
    override fun validateElement(element: BundleSearch, parentContext: LocationContext?, validation: Validation) {
        validation.apply {
            element.mode?.let { code ->
                checkCodedEnum<SearchEntryMode>(
                    code,
                    InvalidValueSetError(BundleSearch::mode, code.value),
                    parentContext
                )
            }
        }
    }
}
