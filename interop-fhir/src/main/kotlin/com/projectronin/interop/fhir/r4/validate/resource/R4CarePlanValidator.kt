package com.projectronin.interop.fhir.r4.validate.resource

import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.resource.CarePlan
import com.projectronin.interop.fhir.r4.resource.CarePlanActivity
import com.projectronin.interop.fhir.r4.resource.CarePlanDetail
import com.projectronin.interop.fhir.r4.validate.element.R4ElementContainingValidator
import com.projectronin.interop.fhir.validate.FHIRError
import com.projectronin.interop.fhir.validate.InvalidDynamicValueError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.Validation
import com.projectronin.interop.fhir.validate.ValidationIssueSeverity

object R4CarePlanValidator : R4ElementContainingValidator<CarePlan>() {
    override fun validateElement(element: CarePlan, parentContext: LocationContext?, validation: Validation) {
        // CarePlan has no special Validation logic, but it should still evaluate its annotations and contained elements.
    }
}

object R4CarePlanActivityValidator : R4ElementContainingValidator<CarePlanActivity>() {

    private val referenceOrDetailError = FHIRError(
        code = "R4_CRPLNACT_001",
        severity = ValidationIssueSeverity.ERROR,
        description = "Provide a reference or detail, not both",
        location = LocationContext(CarePlanActivity::class)
    )

    override fun validateElement(
        element: CarePlanActivity,
        parentContext: LocationContext?,
        validation: Validation
    ) {
        validation.apply {
            checkTrue(
                (element.reference == null || element.detail == null),
                referenceOrDetailError,
                parentContext
            )
        }
    }
}

object R4CarePlanDetailValidator : R4ElementContainingValidator<CarePlanDetail>() {
    private val acceptedScheduledValues = listOf(
        DynamicValueType.TIMING,
        DynamicValueType.PERIOD,
        DynamicValueType.STRING
    )
    private val acceptedProductValues = listOf(
        DynamicValueType.CODEABLE_CONCEPT,
        DynamicValueType.REFERENCE
    )
    private val invalidScheduledValueError =
        InvalidDynamicValueError(CarePlanDetail::scheduled, acceptedScheduledValues)
    private val invalidProductValueError = InvalidDynamicValueError(CarePlanDetail::product, acceptedProductValues)

    override fun validateElement(
        element: CarePlanDetail,
        parentContext: LocationContext?,
        validation: Validation
    ) {
        validation.apply {
            element.scheduled?.let { value ->
                checkTrue(acceptedScheduledValues.contains(value.type), invalidScheduledValueError, parentContext)
            }

            element.product?.let { value ->
                checkTrue(acceptedProductValues.contains(value.type), invalidProductValueError, parentContext)
            }
        }
    }
}
