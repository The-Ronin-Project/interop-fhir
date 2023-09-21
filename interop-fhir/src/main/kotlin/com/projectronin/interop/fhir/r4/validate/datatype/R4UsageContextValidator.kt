package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
import com.projectronin.interop.fhir.r4.datatype.UsageContext
import com.projectronin.interop.fhir.r4.validate.element.R4ElementContainingValidator
import com.projectronin.interop.fhir.validate.InvalidDynamicValueError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.Validation

/**
 * Validator for the [R4 UsageContext](http://hl7.org/fhir/R4/metadatatypes.html#UsageContext).
 */
object R4UsageContextValidator : R4ElementContainingValidator<UsageContext>() {
    private val acceptedValueTypes = listOf(
        DynamicValueType.CODEABLE_CONCEPT,
        DynamicValueType.QUANTITY,
        DynamicValueType.RANGE,
        DynamicValueType.REFERENCE
    )

    private val invalidValueError = InvalidDynamicValueError(UsageContext::value, acceptedValueTypes)

    override fun validateElement(element: UsageContext, parentContext: LocationContext?, validation: Validation) {
        validation.apply {
            element.value?.let {
                checkTrue(acceptedValueTypes.contains(element.value.type), invalidValueError, parentContext)
            }
        }
    }
}
