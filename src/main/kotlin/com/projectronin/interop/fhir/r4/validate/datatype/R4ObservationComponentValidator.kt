package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.ObservationComponent
import com.projectronin.interop.fhir.r4.validate.R4ElementContainingValidator
import com.projectronin.interop.fhir.validate.FHIRError
import com.projectronin.interop.fhir.validate.InvalidDynamicValueError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.RequiredFieldError
import com.projectronin.interop.fhir.validate.Validation
import com.projectronin.interop.fhir.validate.ValidationIssueSeverity

/**
 * Validator for the [R4 ObservationComponent](http://hl7.org/fhir/R4/observation-definitions.html#Observation.component)
 */
object R4ObservationComponentValidator : R4ElementContainingValidator<ObservationComponent>() {
    private val acceptedValues = listOf(
        DynamicValueType.QUANTITY,
        DynamicValueType.CODEABLE_CONCEPT,
        DynamicValueType.STRING,
        DynamicValueType.BOOLEAN,
        DynamicValueType.INTEGER,
        DynamicValueType.RANGE,
        DynamicValueType.RATIO,
        DynamicValueType.SAMPLED_DATA,
        DynamicValueType.TIME,
        DynamicValueType.DATE_TIME,
        DynamicValueType.PERIOD
    )

    private val requiredCodeError = RequiredFieldError(ObservationComponent::code)
    private val invalidValueError = InvalidDynamicValueError(ObservationComponent::value, acceptedValues)
    private val valueOrDataAbsentReasonError = FHIRError(
        code = "R4_OBSCOM_001",
        severity = ValidationIssueSeverity.ERROR,
        description = "dataAbsentReason SHALL only be present if value[x] is not present",
        location = LocationContext(ObservationComponent::class)
    )

    override fun validateElement(
        element: ObservationComponent,
        parentContext: LocationContext?,
        validation: Validation
    ) {
        validation.apply {
            checkNotNull(element.code, requiredCodeError, parentContext)

            element.value?.let { value ->
                checkTrue(acceptedValues.contains(value.type), invalidValueError, parentContext)
            }

            checkTrue(
                (element.value == null || element.dataAbsentReason == null),
                valueOrDataAbsentReasonError,
                parentContext
            )
        }
    }
}
