package com.projectronin.interop.fhir.r4.validate.annotation

import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.RequiredFieldError
import com.projectronin.interop.fhir.validate.Validatable
import com.projectronin.interop.fhir.validate.Validation
import com.projectronin.interop.fhir.validate.annotation.RequiredField
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1
import kotlin.reflect.full.isSubclassOf

/**
 * Validator for [RequiredField]
 */
class RequiredFieldValidator : BaseValidationAnnotationValidator<RequiredField>(RequiredField::class) {
    override fun <T : Validatable<T>> validateSupportedAnnotations(
        annotations: List<RequiredField>,
        property: KProperty1<T, *>,
        kotlinType: KClass<*>,
        element: T,
        elementName: String,
        parentContext: LocationContext?,
        validation: Validation,
    ) {
        val error = RequiredFieldError(LocationContext(elementName, property.name))
        validation.apply {
            val value = property.get(element)
            checkNotNull(value, error, parentContext)

            if (kotlinType.isSubclassOf(Collection::class)) {
                (value as? Collection<*>)?.let { collection ->
                    checkTrue(collection.isNotEmpty(), error, parentContext)
                }
            }
        }
    }
}
