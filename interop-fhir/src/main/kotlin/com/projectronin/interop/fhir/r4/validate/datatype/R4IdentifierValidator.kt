package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.validate.element.R4ElementContainingValidator
import com.projectronin.interop.fhir.r4.valueset.IdentifierUse
import com.projectronin.interop.fhir.validate.InvalidValueSetError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.Validation

/**
 * Validator for the [R4 Identifier](http://hl7.org/fhir/R4/datatypes.html#Identifier).
 */
object R4IdentifierValidator : R4ElementContainingValidator<Identifier>() {
    override fun validateElement(element: Identifier, parentContext: LocationContext?, validation: Validation) {
        validation.apply {
            element.use?.let { code ->
                checkCodedEnum<IdentifierUse>(code, InvalidValueSetError(Identifier::use, code.value), parentContext)
            }
        }
    }
}
