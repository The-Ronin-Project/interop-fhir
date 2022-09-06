package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.Communication
import com.projectronin.interop.fhir.r4.validate.R4ElementContainingValidator
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.RequiredFieldError
import com.projectronin.interop.fhir.validate.Validation

/**
 * Validator for the [R4 Communication](http://hl7.org/fhir/R4/patient-definitions.html#Patient.communication).
 */
object R4CommunicationValidator : R4ElementContainingValidator<Communication>() {
    private val requiredLanguageError = RequiredFieldError(Communication::language)

    override fun validateElement(element: Communication, parentContext: LocationContext?, validation: Validation) {
        validation.apply {
            checkNotNull(element.language, requiredLanguageError, parentContext)
        }
    }
}
