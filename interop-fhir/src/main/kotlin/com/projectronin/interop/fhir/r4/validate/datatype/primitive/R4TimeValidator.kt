package com.projectronin.interop.fhir.r4.validate.datatype.primitive

import com.projectronin.interop.fhir.r4.datatype.primitive.Time
import com.projectronin.interop.fhir.validate.InvalidPrimitiveError

/**
 * Validates a Time against the [R4 profile](http://hl7.org/fhir/R4/datatypes.html#time)
 */
object R4TimeValidator : BaseRegexPrimitiveValidator<Time>(
    Regex("""([01][0-9]|2[0-3]):[0-5][0-9]:([0-5][0-9]|60)(\.[0-9]+)?"""),
    InvalidPrimitiveError(Time::class)
)
