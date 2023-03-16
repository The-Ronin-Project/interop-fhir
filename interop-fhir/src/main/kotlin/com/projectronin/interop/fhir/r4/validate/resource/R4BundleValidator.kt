package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.fhir.r4.resource.Bundle
import com.projectronin.interop.fhir.r4.resource.BundleLink
import com.projectronin.interop.fhir.r4.resource.BundleRequest
import com.projectronin.interop.fhir.r4.resource.BundleResponse
import com.projectronin.interop.fhir.r4.resource.BundleSearch
import com.projectronin.interop.fhir.r4.validate.element.R4ElementContainingValidator
import com.projectronin.interop.fhir.r4.valueset.BundleType
import com.projectronin.interop.fhir.r4.valueset.HttpVerb
import com.projectronin.interop.fhir.r4.valueset.SearchEntryMode
import com.projectronin.interop.fhir.validate.InvalidValueSetError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.RequiredFieldError
import com.projectronin.interop.fhir.validate.Validation

/**
 * Validator for the [R4 Bundle](http://hl7.org/fhir/R4/bundle.html)
 */
object R4BundleValidator : R4ElementContainingValidator<Bundle>() {
    private val requiredTypeError = RequiredFieldError(Bundle::type)

    override fun validateElement(element: Bundle, parentContext: LocationContext?, validation: Validation) {
        validation.apply {
            checkNotNull(element.type, requiredTypeError, parentContext)

            ifNotNull(element.type) {
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
 * Validator for the [R4 BundleLink](http://hl7.org/fhir/R4/bundle-definitions.html#Bundle.link)
 */
object R4BundleLinkValidator : R4ElementContainingValidator<BundleLink>() {
    private val requiredRelationError = RequiredFieldError(BundleLink::relation)
    private val requiredUrlError = RequiredFieldError(BundleLink::url)

    override fun validateElement(element: BundleLink, parentContext: LocationContext?, validation: Validation) {
        validation.apply {
            checkNotNull(element.relation, requiredRelationError, parentContext)
            checkNotNull(element.url, requiredUrlError, parentContext)
        }
    }
}

/**
 * Validator for the [R4 BundleRequest](http://hl7.org/fhir/R4/bundle-definitions.html#Bundle.entry.request).
 */
object R4BundleRequestValidator : R4ElementContainingValidator<BundleRequest>() {
    private val requiredMethodError = RequiredFieldError(BundleRequest::method)
    private val requiredUrlError = RequiredFieldError(BundleRequest::url)

    override fun validateElement(element: BundleRequest, parentContext: LocationContext?, validation: Validation) {
        validation.apply {
            checkNotNull(element.method, requiredMethodError, parentContext)

            ifNotNull(element.method) {
                checkCodedEnum<HttpVerb>(
                    element.method,
                    InvalidValueSetError(BundleRequest::method, element.method.value),
                    parentContext
                )
            }

            checkNotNull(element.url, requiredUrlError, parentContext)
        }
    }
}

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
