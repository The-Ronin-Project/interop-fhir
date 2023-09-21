package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.Contributor
import com.projectronin.interop.fhir.r4.validate.element.R4ElementContainingValidator
import com.projectronin.interop.fhir.r4.valueset.ContributorType
import com.projectronin.interop.fhir.validate.InvalidValueSetError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.Validation

/**
 * Validator for the [R4 Contributor](http://hl7.org/fhir/R4/metadatatypes.html#Contributor).
 */
object R4ContributorValidator : R4ElementContainingValidator<Contributor>() {
    override fun validateElement(element: Contributor, parentContext: LocationContext?, validation: Validation) {
        validation.apply {
            element.type?.let {
                checkCodedEnum<ContributorType>(
                    element.type,
                    InvalidValueSetError(Contributor::type, element.type.value),
                    parentContext
                )
            }
        }
    }
}
