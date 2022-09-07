package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.fhir.r4.resource.ConceptMap
import com.projectronin.interop.fhir.r4.validate.R4ElementContainingValidator
import com.projectronin.interop.fhir.r4.valueset.ConceptMapStatus
import com.projectronin.interop.fhir.validate.InvalidValueSetError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.RequiredFieldError
import com.projectronin.interop.fhir.validate.Validation

object R4ConceptMapValidator : R4ElementContainingValidator<ConceptMap>() {
    private val requiredStatusError = RequiredFieldError(ConceptMap::status)
    private val invalidStatusError = InvalidValueSetError(ConceptMap::status)

    override fun validateElement(element: ConceptMap, parentContext: LocationContext?, validation: Validation) {
        validation.apply {
            checkNotNull(element.status, requiredStatusError, parentContext)
            ifNotNull(element.status) {
                checkCodedEnum<ConceptMapStatus>(element.status, invalidStatusError, parentContext)
            }
        }
    }
}
