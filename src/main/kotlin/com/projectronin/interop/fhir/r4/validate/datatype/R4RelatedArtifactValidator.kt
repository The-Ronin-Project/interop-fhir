package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.RelatedArtifact
import com.projectronin.interop.fhir.r4.validate.element.R4ElementContainingValidator
import com.projectronin.interop.fhir.r4.valueset.RelatedArtifactType
import com.projectronin.interop.fhir.validate.InvalidValueSetError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.RequiredFieldError
import com.projectronin.interop.fhir.validate.Validation

/**
 * Validator for the [R4 RelatedArtifact](http://hl7.org/fhir/R4/metadatatypes.html#RelatedArtifact).
 */
object R4RelatedArtifactValidator : R4ElementContainingValidator<RelatedArtifact>() {
    private val requiredTypeError = RequiredFieldError(RelatedArtifact::type)

    override fun validateElement(element: RelatedArtifact, parentContext: LocationContext?, validation: Validation) {
        validation.apply {
            checkNotNull(element.type, requiredTypeError, parentContext)
            ifNotNull(element.type) {
                checkCodedEnum<RelatedArtifactType>(
                    element.type,
                    InvalidValueSetError(RelatedArtifact::type, element.type.value),
                    parentContext
                )
            }
        }
    }
}
