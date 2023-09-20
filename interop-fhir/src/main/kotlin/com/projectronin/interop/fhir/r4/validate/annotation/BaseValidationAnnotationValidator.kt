package com.projectronin.interop.fhir.r4.validate.annotation

import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.Validatable
import com.projectronin.interop.fhir.validate.Validation
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1

/**
 * Base implementation of [ValidationAnnotationValidator] that adds common logic.
 */
abstract class BaseValidationAnnotationValidator<A : Annotation>(
    override val supportedAnnotation: KClass<A>
) : ValidationAnnotationValidator {
    /**
     * Validates the provided [annotations] against the [element]'s [property] where they were defined.
     */
    protected abstract fun <T : Validatable<T>> validateSupportedAnnotations(
        annotations: List<A>,
        property: KProperty1<T, *>,
        kotlinType: KClass<*>,
        element: T,
        elementName: String,
        parentContext: LocationContext?,
        validation: Validation
    )

    override fun <T : Validatable<T>> validateAnnotations(
        annotations: List<Annotation>,
        property: KProperty1<T, *>,
        kotlinType: KClass<*>,
        element: T,
        elementName: String,
        parentContext: LocationContext?,
        validation: Validation
    ) {
        val supportedAnnotations = annotations.filterIsInstance(supportedAnnotation.java)
        if (supportedAnnotations.isNotEmpty()) {
            validateSupportedAnnotations(
                supportedAnnotations,
                property,
                kotlinType,
                element,
                elementName,
                parentContext,
                validation
            )
        }
    }
}
