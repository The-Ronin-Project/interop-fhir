package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.fhir.r4.resource.DocumentReference
import com.projectronin.interop.fhir.r4.resource.DocumentReferenceRelatesTo
import com.projectronin.interop.fhir.r4.validate.element.R4ElementContainingValidator
import com.projectronin.interop.fhir.r4.valueset.CompositionStatus
import com.projectronin.interop.fhir.r4.valueset.DocumentReferenceStatus
import com.projectronin.interop.fhir.r4.valueset.DocumentRelationshipType
import com.projectronin.interop.fhir.validate.InvalidValueSetError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.Validation

/**
 * Validator for the [R4 DocumentReference](https://hl7.org/fhir/r4/documentreference.html)
 */
object R4DocumentReferenceValidator : R4ElementContainingValidator<DocumentReference>() {
    override fun validateElement(element: DocumentReference, parentContext: LocationContext?, validation: Validation) {
        validation.apply {
            element.status?.let {
                checkCodedEnum<DocumentReferenceStatus>(
                    element.status,
                    InvalidValueSetError(DocumentReference::status, element.status.value),
                    parentContext
                )
            }

            element.docStatus?.let { code ->
                checkCodedEnum<CompositionStatus>(
                    code,
                    InvalidValueSetError(DocumentReference::docStatus, code.value),
                    parentContext
                )
            }
        }
    }
}

/**
 * Validator for the [R4 DocumentReference.RelatesTo](https://hl7.org/fhir/r4/documentreference-definitions.html#DocumentReference.relatesTo)
 */
object R4DocumentReferenceRelatesToValidator : R4ElementContainingValidator<DocumentReferenceRelatesTo>() {
    override fun validateElement(
        element: DocumentReferenceRelatesTo,
        parentContext: LocationContext?,
        validation: Validation
    ) {
        validation.apply {
            element.code?.let {
                checkCodedEnum<DocumentRelationshipType>(
                    element.code,
                    InvalidValueSetError(DocumentReferenceRelatesTo::code, element.code.value),
                    parentContext
                )
            }
        }
    }
}
