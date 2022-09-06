package com.projectronin.interop.fhir.r4.validate.datatype.primitive

import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.validate.InvalidPrimitiveError

/**
 * Validates a Uri against the [R4 profile](http://hl7.org/fhir/R4/datatypes.html#uri)
 */
object R4UriValidator : BaseRegexPrimitiveValidator<Uri>(Regex("""\S*"""), InvalidPrimitiveError(Uri::class))
