package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.HumanName
import com.projectronin.interop.fhir.r4.validate.element.R4ElementContainingValidator
import com.projectronin.interop.fhir.r4.valueset.NameUse
import com.projectronin.interop.fhir.validate.InvalidValueSetError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.Validation

/**
 * Validator for the [R4 HumanName](http://hl7.org/fhir/R4/datatypes.html#HumanName).
 */
object R4HumanNameValidator : R4ElementContainingValidator<HumanName>() {

    override fun validateElement(element: HumanName, parentContext: LocationContext?, validation: Validation) {
        validation.apply {
            element.use?.let { code ->
                checkCodedEnum<NameUse>(code, InvalidValueSetError(HumanName::use, code.value), parentContext)
            }
        }
    }
}