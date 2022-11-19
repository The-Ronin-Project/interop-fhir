package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.CarePlanDetail
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.validate.R4ElementContainingValidator
import com.projectronin.interop.fhir.r4.valueset.CarePlanActivityKind
import com.projectronin.interop.fhir.r4.valueset.CarePlanActivityStatus
import com.projectronin.interop.fhir.validate.InvalidDynamicValueError
import com.projectronin.interop.fhir.validate.InvalidValueSetError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.RequiredFieldError
import com.projectronin.interop.fhir.validate.Validation

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
    private val invalidScheduledValueError = InvalidDynamicValueError(CarePlanDetail::scheduled, acceptedScheduledValues)
    private val invalidProductValueError = InvalidDynamicValueError(CarePlanDetail::product, acceptedProductValues)

    private val invalidKindError = InvalidValueSetError(CarePlanDetail::kind)

    private val requiredStatusError = RequiredFieldError(CarePlanDetail::status)
    private val invalidStatusError = InvalidValueSetError(CarePlanDetail::status)

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

            element.kind?.let {
                checkCodedEnum<CarePlanActivityKind>(element.kind, invalidKindError, parentContext)
            }

            checkNotNull(element.status, requiredStatusError, parentContext)
            ifNotNull(element.status) {
                checkCodedEnum<CarePlanActivityStatus>(element.status, invalidStatusError, parentContext)
            }
        }
    }
}
