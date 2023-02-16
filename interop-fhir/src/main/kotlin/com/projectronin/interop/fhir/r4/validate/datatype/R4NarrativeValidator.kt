package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.Narrative
import com.projectronin.interop.fhir.r4.validate.element.R4ElementContainingValidator
import com.projectronin.interop.fhir.r4.valueset.NarrativeStatus
import com.projectronin.interop.fhir.validate.InvalidValueSetError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.RequiredFieldError
import com.projectronin.interop.fhir.validate.Validation

/**
 * Validator for the [R4 Narrative](http://hl7.org/fhir/R4/narrative.html#Narrative).
 * This class does not validate FHIR rules for Narrative.div. It assumes non-empty content with appropriate HTML format.
 */
object R4NarrativeValidator : R4ElementContainingValidator<Narrative>() {
    private val requiredStatusError = RequiredFieldError(Narrative::status)
    private val requiredDivError = RequiredFieldError(Narrative::div)

    override fun validateElement(element: Narrative, parentContext: LocationContext?, validation: Validation) {
        validation.apply {
            checkNotNull(element.status, requiredStatusError, parentContext)
            ifNotNull(element.status) {
                checkCodedEnum<NarrativeStatus>(
                    element.status,
                    InvalidValueSetError(Narrative::status, element.status.value),
                    parentContext
                )
            }

            checkNotNull(element.div, requiredDivError, parentContext)
        }
    }
}
