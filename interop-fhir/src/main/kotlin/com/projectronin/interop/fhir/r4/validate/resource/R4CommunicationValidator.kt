package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.resource.Communication
import com.projectronin.interop.fhir.r4.resource.CommunicationPayload
import com.projectronin.interop.fhir.r4.validate.element.R4ElementContainingValidator
import com.projectronin.interop.fhir.r4.valueset.EventStatus
import com.projectronin.interop.fhir.r4.valueset.RequestPriority
import com.projectronin.interop.fhir.validate.InvalidDynamicValueError
import com.projectronin.interop.fhir.validate.InvalidValueSetError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.RequiredFieldError
import com.projectronin.interop.fhir.validate.Validation

/**
 * Validator for the [R4 Communication](https://hl7.org/fhir/r4/communication.html)
 */
object R4CommunicationValidator : R4ElementContainingValidator<Communication>() {
    private val requiredStatusError = RequiredFieldError(Communication::status)

    override fun validateElement(
        element: Communication,
        parentContext: LocationContext?,
        validation: Validation
    ) {
        validation.apply {
            checkNotNull(
                element.status,
                requiredStatusError,
                parentContext
            )

            ifNotNull(element.status) {
                checkCodedEnum<EventStatus>(
                    element.status,
                    InvalidValueSetError(Communication::status, element.status.value),
                    parentContext
                )
            }

            ifNotNull(element.priority) {
                checkCodedEnum<RequestPriority>(
                    element.priority!!,
                    InvalidValueSetError(Communication::priority, element.priority.value),
                    parentContext
                )
            }
        }
    }
}

/**
 * Validator for the [R4 Communication.Payload](https://hl7.org/fhir/r4/communication-definitions.html#Communication.payload)
 */
object R4CommunicationPayloadValidator : R4ElementContainingValidator<CommunicationPayload>() {
    private val requiredContentError = RequiredFieldError(CommunicationPayload::content)

    private val acceptedContentTypes = listOf(
        DynamicValueType.STRING,
        DynamicValueType.ATTACHMENT,
        DynamicValueType.ANNOTATION
    )
    private val invalidContentError = InvalidDynamicValueError(CommunicationPayload::content, acceptedContentTypes)

    override fun validateElement(
        element: CommunicationPayload,
        parentContext: LocationContext?,
        validation: Validation
    ) {
        validation.apply {
            checkNotNull(
                element.content,
                requiredContentError,
                parentContext
            )

            ifNotNull(element.content) {
                checkTrue(
                    acceptedContentTypes.contains(element.content.type),
                    invalidContentError,
                    parentContext
                )
            }
        }
    }
}
