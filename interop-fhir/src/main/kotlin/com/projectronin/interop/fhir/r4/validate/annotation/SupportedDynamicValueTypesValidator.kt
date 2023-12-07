package com.projectronin.interop.fhir.r4.validate.annotation

import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.validate.InvalidDynamicValueError
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.Validatable
import com.projectronin.interop.fhir.validate.Validation
import com.projectronin.interop.fhir.validate.annotation.SupportedDynamicValueTypes
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1
import kotlin.reflect.full.isSubclassOf

/**
 * Validator for [SupportedDynamicValueTypes]
 */
class SupportedDynamicValueTypesValidator :
    BaseValidationAnnotationValidator<SupportedDynamicValueTypes>(SupportedDynamicValueTypes::class) {
    override fun <T : Validatable<T>> validateSupportedAnnotations(
        annotations: List<SupportedDynamicValueTypes>,
        property: KProperty1<T, *>,
        kotlinType: KClass<*>,
        element: T,
        elementName: String,
        parentContext: LocationContext?,
        validation: Validation,
    ) {
        val supportedTypes = annotations.flatMap { it.value.toList() }
        if (supportedTypes.isNotEmpty()) {
            if (kotlinType.isSubclassOf(DynamicValue::class)) {
                val dynamicValue = property.get(element) as? DynamicValue<*>
                dynamicValue?.let {
                    val error = InvalidDynamicValueError(LocationContext(elementName, property.name), supportedTypes)
                    validation.checkTrue(supportedTypes.contains(dynamicValue.type), error, parentContext)
                }
            }
        }
    }
}
