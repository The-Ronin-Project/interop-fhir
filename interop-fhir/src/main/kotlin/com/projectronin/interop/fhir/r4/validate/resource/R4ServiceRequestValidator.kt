package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.resource.ServiceRequest
import com.projectronin.interop.fhir.r4.validate.element.R4ElementContainingValidator
import com.projectronin.interop.fhir.r4.valueset.RequestIntent
import com.projectronin.interop.fhir.r4.valueset.RequestPriority
import com.projectronin.interop.fhir.r4.valueset.RequestStatus
import com.projectronin.interop.fhir.validate.FHIRError
import com.projectronin.interop.fhir.validate.InvalidDynamicValueError
import com.projectronin.interop.fhir.validate.InvalidValueSetError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.RequiredFieldError
import com.projectronin.interop.fhir.validate.Validation
import com.projectronin.interop.fhir.validate.ValidationIssueSeverity

/**
 * Validator for the [R4 ServiceRequest](http://hl7.org/fhir/R4/servicerequest.html)
 */
object R4ServiceRequestValidator : R4ElementContainingValidator<ServiceRequest>() {
    private val acceptedQuantityTypes =
        listOf(DynamicValueType.QUANTITY, DynamicValueType.RATIO, DynamicValueType.RANGE)
    private val acceptedOccurrenceTypes =
        listOf(DynamicValueType.DATE_TIME, DynamicValueType.PERIOD, DynamicValueType.TIMING)
    private val acceptedAsNeededTypes = listOf(DynamicValueType.BOOLEAN, DynamicValueType.CODEABLE_CONCEPT)

    private val requiredStatusError = RequiredFieldError(ServiceRequest::status)
    private val requiredIntentError = RequiredFieldError(ServiceRequest::intent)
    private val invalidQuantityError = InvalidDynamicValueError(ServiceRequest::quantity, acceptedQuantityTypes)
    private val requiredSubjectError = RequiredFieldError(ServiceRequest::subject)
    private val invalidOccurrenceError = InvalidDynamicValueError(ServiceRequest::occurrence, acceptedOccurrenceTypes)
    private val invalidAsNeededError = InvalidDynamicValueError(ServiceRequest::asNeeded, acceptedAsNeededTypes)

    private val orderDetailWithNoCodeError = FHIRError(
        code = "R4_PRR_001",
        severity = ValidationIssueSeverity.ERROR,
        description = "orderDetail SHALL only be present if code is present",
        location = LocationContext(ServiceRequest::orderDetail)
    )

    override fun validateElement(element: ServiceRequest, parentContext: LocationContext?, validation: Validation) {
        validation.apply {
            checkNotNull(element.status, requiredStatusError, parentContext)
            ifNotNull(element.status) {
                checkCodedEnum<RequestStatus>(
                    element.status,
                    InvalidValueSetError(ServiceRequest::status, element.status.value),
                    parentContext
                )
            }

            checkNotNull(element.intent, requiredIntentError, parentContext)
            ifNotNull(element.intent) {
                checkCodedEnum<RequestIntent>(
                    element.intent,
                    InvalidValueSetError(ServiceRequest::intent, element.intent.value),
                    parentContext
                )
            }

            element.priority?.let { priority ->
                checkCodedEnum<RequestPriority>(
                    priority,
                    InvalidValueSetError(ServiceRequest::priority, priority.value),
                    parentContext
                )
            }

            element.quantity?.let { quantity ->
                checkTrue(acceptedQuantityTypes.contains(quantity.type), invalidQuantityError, parentContext)
            }

            checkNotNull(element.subject, requiredSubjectError, parentContext)

            element.occurrence?.let { occurrence ->
                checkTrue(acceptedOccurrenceTypes.contains(occurrence.type), invalidOccurrenceError, parentContext)
            }

            element.asNeeded?.let { asNeeded ->
                checkTrue(acceptedAsNeededTypes.contains(asNeeded.type), invalidAsNeededError, parentContext)
            }

            checkTrue(element.orderDetail.isEmpty() || element.code != null, orderDetailWithNoCodeError, parentContext)
        }
    }
}
