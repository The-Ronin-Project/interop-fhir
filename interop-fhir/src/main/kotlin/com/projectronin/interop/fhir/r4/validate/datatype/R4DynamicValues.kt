package com.projectronin.interop.fhir.r4.validate.datatype

import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.primitive.Primitive
import com.projectronin.interop.fhir.r4.element.Element
import com.projectronin.interop.fhir.r4.validate.datatype.primitive.validatePrimitive
import com.projectronin.interop.fhir.r4.validate.element.validateElement
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.Validation

/**
 * Validates the [dynamicValue] against the R4 spec.
 */
fun <T> validateDynamicValue(dynamicValue: DynamicValue<T>, parentContext: LocationContext?): Validation {
    return when (val value = dynamicValue.value) {
        is Primitive<*, *> -> validatePrimitive(value, parentContext)
        is Element<*> -> validateElement(value, parentContext)
        else -> Validation()
    }
}
