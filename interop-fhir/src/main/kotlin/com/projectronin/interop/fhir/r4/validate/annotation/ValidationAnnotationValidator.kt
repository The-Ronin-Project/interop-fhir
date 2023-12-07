package com.projectronin.interop.fhir.r4.validate.annotation

import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.Validatable
import com.projectronin.interop.fhir.validate.Validation
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1

/**
 * Defines a validator for a validation annotation.
 */
interface ValidationAnnotationValidator {
    /**
     * The KClass of the validation annotation supported by this validator. Only a single validator should exist for each annotation.
     */
    val supportedAnnotation: KClass<out Annotation>

    /**
     * Validates the provided annotations (which should each be an instance of the [supportedAnnotation]) against the
     * [element]'s [property] where they were defined.
     */
    fun <T : Validatable<T>> validateAnnotations(
        annotations: List<Annotation>,
        property: KProperty1<T, *>,
        kotlinType: KClass<*>,
        element: T,
        elementName: String,
        parentContext: LocationContext?,
        validation: Validation,
    )
}
