package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.fhir.r4.resource.DocumentReference
import com.projectronin.interop.fhir.r4.resource.DocumentReferenceContent
import com.projectronin.interop.fhir.r4.resource.DocumentReferenceRelatesTo
import com.projectronin.interop.fhir.r4.validate.element.R4ElementContainingValidator
import com.projectronin.interop.fhir.r4.valueset.CompositionStatus
import com.projectronin.interop.fhir.r4.valueset.DocumentReferenceStatus
import com.projectronin.interop.fhir.r4.valueset.DocumentRelationshipType
import com.projectronin.interop.fhir.validate.FHIRError
import com.projectronin.interop.fhir.validate.InvalidValueSetError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.RequiredFieldError
import com.projectronin.interop.fhir.validate.Validation
import com.projectronin.interop.fhir.validate.ValidationIssueSeverity

/**
 * Validator for the [R4 DocumentReference](https://hl7.org/fhir/r4/documentreference.html)
 */
object R4DocumentReferenceValidator : R4ElementContainingValidator<DocumentReference>() {
    private val requiredStatusError = RequiredFieldError(DocumentReference::status)

    private val requiredContentError = FHIRError(
        code = "R4_DOCREF_001",
        severity = ValidationIssueSeverity.ERROR,
        description = "At least one content item is required",
        location = LocationContext(DocumentReference::content)
    )

    override fun validateElement(element: DocumentReference, parentContext: LocationContext?, validation: Validation) {
        validation.apply {
            checkNotNull(element.status, requiredStatusError, parentContext)
            ifNotNull(element.status) {
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

            checkTrue(element.content.isNotEmpty(), requiredContentError, parentContext)
        }
    }
}

/**
 * Validator for the [R4 DocumentReference.RelatesTo](https://hl7.org/fhir/r4/documentreference-definitions.html#DocumentReference.relatesTo)
 */
object R4DocumentReferenceRelatesToValidator : R4ElementContainingValidator<DocumentReferenceRelatesTo>() {
    private val requiredCodeError = RequiredFieldError(DocumentReferenceRelatesTo::code)
    private val requiredTargetError = RequiredFieldError(DocumentReferenceRelatesTo::target)

    override fun validateElement(
        element: DocumentReferenceRelatesTo,
        parentContext: LocationContext?,
        validation: Validation
    ) {
        validation.apply {
            checkNotNull(element.code, requiredCodeError, parentContext)
            ifNotNull(element.code) {
                checkCodedEnum<DocumentRelationshipType>(
                    element.code,
                    InvalidValueSetError(DocumentReferenceRelatesTo::code, element.code.value),
                    parentContext
                )
            }
            checkNotNull(element.target, requiredTargetError, parentContext)
        }
    }
}

/**
 * Validator for the [R4 DocumentReference.Content](https://hl7.org/fhir/r4/documentreference-definitions.html#DocumentReference.content)
 */
object R4DocumentReferenceContentValidator : R4ElementContainingValidator<DocumentReferenceContent>() {
    private val requiredAttachmentError = RequiredFieldError(DocumentReferenceContent::attachment)

    override fun validateElement(
        element: DocumentReferenceContent,
        parentContext: LocationContext?,
        validation: Validation
    ) {
        validation.apply {
            checkNotNull(element.attachment, requiredAttachmentError, parentContext)
        }
    }
}
