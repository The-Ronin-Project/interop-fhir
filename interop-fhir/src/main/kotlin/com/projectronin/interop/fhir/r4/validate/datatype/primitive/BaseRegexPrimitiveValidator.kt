package com.projectronin.interop.fhir.r4.validate.datatype.primitive

import com.projectronin.interop.fhir.r4.datatype.primitive.Primitive
import com.projectronin.interop.fhir.validate.FHIRError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.ProfileValidator
import com.projectronin.interop.fhir.validate.Validation
import com.projectronin.interop.fhir.validate.validation

/**
 * Base class for supporting regex-based primitive validation.
 */
abstract class BaseRegexPrimitiveValidator<PT : Primitive<String, PT>>(
    private val regex: Regex,
    private val fhirError: FHIRError,
) :
    ProfileValidator<PT> {
    override fun validate(
        element: PT,
        parentContext: LocationContext?,
    ): Validation =
        validation {
            element.value?.let { checkTrue(regex.matches(it), fhirError, parentContext) }
        }
}
