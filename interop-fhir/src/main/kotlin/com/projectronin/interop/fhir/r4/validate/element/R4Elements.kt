package com.projectronin.interop.fhir.r4.validate.element

import com.projectronin.interop.fhir.r4.element.Element
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.Validation
import io.github.classgraph.ClassGraph
import java.lang.reflect.ParameterizedType

// This Map actually has ALL validators because there's not really a way to know which are Elements vs Resources, but the method that uses it will only take in Elements, so we're good.
private val validatorsByClass =
    ClassGraph().acceptPackages("com.projectronin.interop.fhir").enableClassInfo().scan().use {
        it.getSubclasses(R4ElementContainingValidator::class.java).standardClasses.mapNotNull { classInfo ->
            if (classInfo.isAbstract || classInfo.isInterface || classInfo.isInnerClass) {
                // Inner class is included to prevent test validators from being loaded
                null
            } else if (classInfo.simpleName == GenericElementValidator::class.simpleName) {
                null
            } else {
                val validator = classInfo.loadClass().kotlin.objectInstance as R4ElementContainingValidator<*>
                val argumentType =
                    (validator::class.java.genericSuperclass as ParameterizedType).actualTypeArguments[0]
                argumentType to validator
            }
        }
    }.toMap()

/**
 * Validates the [element] against the R4 spec.
 */
@Suppress("UNCHECKED_CAST")
fun <T : Element<T>> validateElement(element: Element<T>, parentContext: LocationContext?): Validation {
    val validator =
        validatorsByClass[element::class.java] as? R4ElementContainingValidator<T> ?: GenericElementValidator()
    return element.validate(validator, parentContext)
}

/**
 * A generic validator for elements that ensures their properties are validated.
 */
internal class GenericElementValidator<T : Element<T>> : R4ElementContainingValidator<T>() {
    override fun validateElement(element: T, parentContext: LocationContext?, validation: Validation) {
        // There is no actual validation done for generic elements.
    }
}
