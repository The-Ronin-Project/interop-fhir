package com.projectronin.interop.fhir.r4.validate.datatype.primitive

import com.projectronin.interop.fhir.r4.datatype.primitive.Base64Binary
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.Date
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Instant
import com.projectronin.interop.fhir.r4.datatype.primitive.Oid
import com.projectronin.interop.fhir.r4.datatype.primitive.PositiveInt
import com.projectronin.interop.fhir.r4.datatype.primitive.Primitive
import com.projectronin.interop.fhir.r4.datatype.primitive.Time
import com.projectronin.interop.fhir.r4.datatype.primitive.UnsignedInt
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.datatype.primitive.Uuid
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.Validation

/**
 * Validates the [primitive] against the R4 spec.
 */
fun <T, PT : Primitive<T, PT>> validatePrimitive(
    primitive: Primitive<T, PT>,
    parentContext: LocationContext?
): Validation {
    return when (primitive) {
        is Base64Binary -> primitive.validate(R4Base64BinaryValidator, parentContext)
        is Code -> primitive.validate(R4CodeValidator, parentContext)
        is Date -> primitive.validate(R4DateValidator, parentContext)
        is DateTime -> primitive.validate(R4DateTimeValidator, parentContext)
        is Id -> primitive.validate(R4IdValidator, parentContext)
        is Instant -> primitive.validate(R4InstantValidator, parentContext)
        is Oid -> primitive.validate(R4OidValidator, parentContext)
        is PositiveInt -> primitive.validate(R4PositiveIntValidator, parentContext)
        is Time -> primitive.validate(R4TimeValidator, parentContext)
        is UnsignedInt -> primitive.validate(R4UnsignedIntValidator, parentContext)
        is Uri -> primitive.validate(R4UriValidator, parentContext)
        is Uuid -> primitive.validate(R4UuidValidator, parentContext)
        else -> Validation()
    }
}
