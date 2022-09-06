package com.projectronin.interop.fhir.r4.validate.datatype.primitive

import com.projectronin.interop.fhir.r4.datatype.primitive.Base64Binary
import com.projectronin.interop.fhir.validate.InvalidPrimitiveError

/**
 * Validates a Base64Binary against the [R4 profile](http://hl7.org/fhir/R4/datatypes.html#base64Binary)
 */
object R4Base64BinaryValidator :
    BaseRegexPrimitiveValidator<Base64Binary>(
        Regex("""(\s*([0-9a-zA-Z\+\=]){4}\s*)+"""),
        InvalidPrimitiveError(Base64Binary::class)
    )
