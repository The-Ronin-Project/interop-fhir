package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.Signature
import com.projectronin.interop.fhir.r4.validate.element.R4ElementContainingValidator
import com.projectronin.interop.fhir.validate.FHIRError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.RequiredFieldError
import com.projectronin.interop.fhir.validate.Validation
import com.projectronin.interop.fhir.validate.ValidationIssueSeverity

/**
 * Validator for the [R4 Signature](http://hl7.org/fhir/R4/datatypes.html#Signature)
 */
object R4SignatureValidator : R4ElementContainingValidator<Signature>() {
    private val requiredWhenError = RequiredFieldError(Signature::`when`)
    private val requiredWhoError = RequiredFieldError(Signature::who)
    private val requiredTypeError = FHIRError(
        code = "R4_SIG_001",
        severity = ValidationIssueSeverity.ERROR,
        description = "The Signature SHALL include a CommitmentTypeIndication for the Purpose(s) of Signature",
        location = LocationContext(Signature::type)
    )

    override fun validateElement(element: Signature, parentContext: LocationContext?, validation: Validation) {
        validation.apply {
            checkNotNull(element.`when`, requiredWhenError, parentContext)
            checkNotNull(element.who, requiredWhoError, parentContext)

            checkTrue(element.type.isNotEmpty(), requiredTypeError, parentContext)
        }
    }
}
