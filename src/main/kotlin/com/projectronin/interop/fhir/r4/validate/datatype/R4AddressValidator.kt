package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.Address
import com.projectronin.interop.fhir.r4.validate.element.R4ElementContainingValidator
import com.projectronin.interop.fhir.r4.valueset.AddressType
import com.projectronin.interop.fhir.r4.valueset.AddressUse
import com.projectronin.interop.fhir.validate.InvalidValueSetError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.Validation

/**
 * Validator for the [R4 Address](http://hl7.org/fhir/R4/datatypes.html#Address)
 */
object R4AddressValidator : R4ElementContainingValidator<Address>() {
    override fun validateElement(element: Address, parentContext: LocationContext?, validation: Validation) {
        validation.apply {
            element.use?.let { code ->
                checkCodedEnum<AddressUse>(code, InvalidValueSetError(Address::use, code.value), parentContext)
            }

            element.type?.let { code ->
                checkCodedEnum<AddressType>(code, InvalidValueSetError(Address::type, code.value), parentContext)
            }
        }
    }
}
