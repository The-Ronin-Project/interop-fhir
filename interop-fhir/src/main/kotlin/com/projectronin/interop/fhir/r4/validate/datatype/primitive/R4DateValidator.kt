package com.projectronin.interop.fhir.r4.validate.datatype.primitive

import com.projectronin.interop.fhir.r4.datatype.primitive.Date
import com.projectronin.interop.fhir.validate.InvalidPrimitiveError

/**
 * Validates a Date against the [R4 profile](http://hl7.org/fhir/R4/datatypes.html#date)
 */
object R4DateValidator :
    BaseRegexPrimitiveValidator<Date>(
        Regex("""([0-9]([0-9]([0-9][1-9]|[1-9]0)|[1-9]00)|[1-9]000)(-(0[1-9]|1[0-2])(-(0[1-9]|[1-2][0-9]|3[0-1]))?)?"""),
        InvalidPrimitiveError(Date::class),
    )
