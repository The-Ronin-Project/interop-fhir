package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.Sort
import com.projectronin.interop.fhir.r4.validate.element.R4ElementContainingValidator
import com.projectronin.interop.fhir.r4.valueset.SortDirection
import com.projectronin.interop.fhir.validate.InvalidValueSetError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.Validation

/**
 * Validator for the [R4 Sort](http://hl7.org/fhir/R4/metadatatypes-definitions.html#DataRequirement.sort).
 */
object R4SortValidator : R4ElementContainingValidator<Sort>() {
    override fun validateElement(element: Sort, parentContext: LocationContext?, validation: Validation) {
        validation.apply {
            element.direction?.let {
                checkCodedEnum<SortDirection>(
                    element.direction,
                    InvalidValueSetError(Sort::direction, element.direction.value),
                    parentContext
                )
            }
        }
    }
}
