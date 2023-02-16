package com.projectronin.interop.fhir.r4.validate.datatype.primitive

import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.validate.InvalidPrimitiveError

/**
 * Validates a Code against the [R4 profile](http://hl7.org/fhir/R4/datatypes.html#code)
 */
object R4CodeValidator :
    BaseRegexPrimitiveValidator<Code>(Regex("""[^\s]+(\s[^\s]+)*"""), InvalidPrimitiveError(Code::class))
