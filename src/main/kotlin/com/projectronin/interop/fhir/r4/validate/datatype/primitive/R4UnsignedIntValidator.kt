package com.projectronin.interop.fhir.r4.validate.datatype.primitive

import com.projectronin.interop.fhir.r4.datatype.primitive.UnsignedInt
import com.projectronin.interop.fhir.validate.InvalidPrimitiveError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.ProfileValidator
import com.projectronin.interop.fhir.validate.Validation
import com.projectronin.interop.fhir.validate.validation

/**
 * Validates an UnsignedInt against the [R4 profile](http://hl7.org/fhir/R4/datatypes.html#unsignedInt)
 */
object R4UnsignedIntValidator : ProfileValidator<UnsignedInt> {
    private val error = InvalidPrimitiveError(UnsignedInt::class)

    override fun validate(element: UnsignedInt, parentContext: LocationContext?): Validation = validation {
        element.value?.let { checkTrue(it >= 0, error, parentContext) }
    }
}
