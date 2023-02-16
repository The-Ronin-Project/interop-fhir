package com.projectronin.interop.fhir.r4.validate.datatype.primitive

import com.projectronin.interop.fhir.r4.datatype.primitive.Uuid
import com.projectronin.interop.fhir.validate.InvalidPrimitiveError

/**
 * Validates an Uuid against the [R4 profile](http://hl7.org/fhir/R4/datatypes.html#uuid)
 */
object R4UuidValidator : BaseRegexPrimitiveValidator<Uuid>(
    Regex("""urn:uuid:[0-9a-f]{8}(-[0-9a-f]{4}){3}-[0-9a-f]{12}"""),
    InvalidPrimitiveError(Uuid::class)
)
