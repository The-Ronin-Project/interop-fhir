package com.projectronin.interop.fhir.r4.validate.datatype.primitive

import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.validate.InvalidPrimitiveError

/**
 * Validates an Id against the [R4 profile](http://hl7.org/fhir/R4/datatypes.html#id)
 * FHIR recommends a length limit of 64 characters which we are not enforcing.
 */
object R4IdValidator : BaseRegexPrimitiveValidator<Id>(Regex("""[A-Za-z0-9\-\.]+"""), InvalidPrimitiveError(Id::class))
