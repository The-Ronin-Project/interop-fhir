package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.BundleSearch
import com.projectronin.interop.fhir.r4.validate.R4ElementContainingValidator
import com.projectronin.interop.fhir.r4.valueset.SearchEntryMode
import com.projectronin.interop.fhir.validate.InvalidValueSetError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.Validation

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
