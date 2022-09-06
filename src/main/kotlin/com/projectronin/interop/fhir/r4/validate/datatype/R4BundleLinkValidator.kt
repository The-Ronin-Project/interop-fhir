package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.BundleLink
import com.projectronin.interop.fhir.r4.validate.R4ElementContainingValidator
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.RequiredFieldError
import com.projectronin.interop.fhir.validate.Validation

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
