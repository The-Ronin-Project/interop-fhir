package com.projectronin.interop.fhir.r4.validate.datatype.primitive

import com.projectronin.interop.fhir.r4.datatype.primitive.Oid
import com.projectronin.interop.fhir.validate.InvalidPrimitiveError

/**
 * Validates an Oid against the [R4 profile](http://hl7.org/fhir/R4/datatypes.html#oid)
 */
object R4OidValidator : BaseRegexPrimitiveValidator<Oid>(
    Regex("""urn:oid:[0-2](\.(0|[1-9][0-9]*))+"""),
    InvalidPrimitiveError(Oid::class)
)
