package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.resource.Procedure
import com.projectronin.interop.fhir.r4.validate.element.R4ElementContainingValidator
import com.projectronin.interop.fhir.r4.valueset.EventStatus
import com.projectronin.interop.fhir.validate.InvalidDynamicValueError
import com.projectronin.interop.fhir.validate.InvalidValueSetError
import com.projectronin.interop.fhir.validate.LocationContext
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

    private val invalidPerformedError = InvalidDynamicValueError(Procedure::performed, acceptedPerformedTypes)

    override fun validateElement(element: Procedure, parentContext: LocationContext?, validation: Validation) {
        validation.apply {
            element.status?.let {
                checkCodedEnum<EventStatus>(
                    element.status,
                    InvalidValueSetError(Procedure::status, element.status.value),
                    parentContext
                )
            }

            element.performed?.let { performed ->
                checkTrue(acceptedPerformedTypes.contains(performed.type), invalidPerformedError, parentContext)
            }
        }
    }
}
