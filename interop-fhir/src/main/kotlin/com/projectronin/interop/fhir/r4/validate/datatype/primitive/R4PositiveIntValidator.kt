package com.projectronin.interop.fhir.r4.validate.datatype.primitive

import com.projectronin.interop.fhir.r4.datatype.primitive.PositiveInt
import com.projectronin.interop.fhir.validate.InvalidPrimitiveError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.ProfileValidator
import com.projectronin.interop.fhir.validate.Validation
import com.projectronin.interop.fhir.validate.validation

/**
 * Validates a PositiveInt against the [R4 profile](http://hl7.org/fhir/R4/datatypes.html#positiveInt)
 */
object R4PositiveIntValidator : ProfileValidator<PositiveInt> {
    private val error = InvalidPrimitiveError(PositiveInt::class)

    override fun validate(
        element: PositiveInt,
        parentContext: LocationContext?,
    ): Validation =
        validation {
            element.value?.let { checkTrue(it > 0, error, parentContext) }
        }
}
