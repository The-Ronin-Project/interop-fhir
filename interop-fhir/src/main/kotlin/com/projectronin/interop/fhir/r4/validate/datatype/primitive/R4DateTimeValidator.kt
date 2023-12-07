package com.projectronin.interop.fhir.r4.validate.datatype.primitive

import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.interop.fhir.validate.InvalidPrimitiveError

/**
 * Validates a DateTime against the [R4 profile](http://hl7.org/fhir/R4/datatypes.html#dateTime)
 */
object R4DateTimeValidator : BaseRegexPrimitiveValidator<DateTime>(
    @Suppress("ktlint:standard:max-line-length")
    Regex(
        """([0-9]([0-9]([0-9][1-9]|[1-9]0)|[1-9]00)|[1-9]000)(-(0[1-9]|1[0-2])(-(0[1-9]|[1-2][0-9]|3[0-1])(T([01][0-9]|2[0-3]):[0-5][0-9]:([0-5][0-9]|60)(\.[0-9]+)?(Z|(\+|-)((0[0-9]|1[0-3]):[0-5][0-9]|14:00)))?)?)?""",
    ),
    InvalidPrimitiveError(DateTime::class),
)
