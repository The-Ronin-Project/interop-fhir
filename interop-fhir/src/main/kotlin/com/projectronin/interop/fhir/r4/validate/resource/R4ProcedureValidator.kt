package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.resource.Procedure
import com.projectronin.interop.fhir.r4.resource.ProcedureFocalDevice
import com.projectronin.interop.fhir.r4.resource.ProcedurePerformer
import com.projectronin.interop.fhir.r4.validate.element.R4ElementContainingValidator
import com.projectronin.interop.fhir.r4.valueset.EventStatus
import com.projectronin.interop.fhir.validate.InvalidDynamicValueError
import com.projectronin.interop.fhir.validate.InvalidValueSetError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.RequiredFieldError
import com.projectronin.interop.fhir.validate.Validation

/**
 * Validator for the [R4 Procedure](http://hl7.org/fhir/R4/procedure.html)
 */
object R4ProcedureValidator : R4ElementContainingValidator<Procedure>() {
    private val acceptedPerformedTypes = listOf(
        DynamicValueType.DATE_TIME,
        DynamicValueType.PERIOD,
        DynamicValueType.STRING,
        DynamicValueType.AGE,
        DynamicValueType.RANGE
    )

    private val requiredStatusError = RequiredFieldError(Procedure::status)
    private val requiredSubjectError = RequiredFieldError(Procedure::subject)
    private val invalidPerformedError = InvalidDynamicValueError(Procedure::performed, acceptedPerformedTypes)

    override fun validateElement(element: Procedure, parentContext: LocationContext?, validation: Validation) {
        validation.apply {
            checkNotNull(element.status, requiredStatusError, parentContext)
            ifNotNull(element.status) {
                checkCodedEnum<EventStatus>(
                    element.status,
                    InvalidValueSetError(Procedure::status, element.status.value),
                    parentContext
                )
            }

            checkNotNull(element.subject, requiredSubjectError, parentContext)

            element.performed?.let { performed ->
                checkTrue(acceptedPerformedTypes.contains(performed.type), invalidPerformedError, parentContext)
            }
        }
    }
}

object R4ProcedurePerformerValidator : R4ElementContainingValidator<ProcedurePerformer>() {
    private val requiredActorError = RequiredFieldError(ProcedurePerformer::actor)
    override fun validateElement(element: ProcedurePerformer, parentContext: LocationContext?, validation: Validation) {
        validation.apply {
            checkNotNull(element.actor, requiredActorError, parentContext)
        }
    }
}

object R4ProcedureFocalDeviceValidator : R4ElementContainingValidator<ProcedureFocalDevice>() {
    private val requiredManipulatedError = RequiredFieldError(ProcedureFocalDevice::manipulated)
    override fun validateElement(
        element: ProcedureFocalDevice,
        parentContext: LocationContext?,
        validation: Validation
    ) {
        validation.apply {
            checkNotNull(element.manipulated, requiredManipulatedError, parentContext)
        }
    }
}
