package com.projectronin.interop.fhir.r4.validate

import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.Element
import com.projectronin.interop.fhir.r4.datatype.primitive.Primitive
import com.projectronin.interop.fhir.r4.validate.datatype.primitive.validatePrimitive
import com.projectronin.interop.fhir.r4.validate.datatype.validateDatatype
import com.projectronin.interop.fhir.r4.validate.datatype.validateDynamicValue
import com.projectronin.interop.fhir.validate.LocationContext
import com.projectronin.interop.fhir.validate.ProfileValidator
import com.projectronin.interop.fhir.validate.Validatable
import com.projectronin.interop.fhir.validate.Validation
import com.projectronin.interop.fhir.validate.append
import com.projectronin.interop.fhir.validate.validation
import kotlin.reflect.full.isSubclassOf
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.jvmErasure

/**
 * Base validator for handling common validation for R4 elements potentially containing other datatypes. This should only be used for models within FHIR. Any models extending FHIR should be unconcerned with validating specific elements.
 */
abstract class R4ElementContainingValidator<T : Validatable<T>>(private val elementName: String? = null) :
    ProfileValidator<T> {
    /**
     * Validates the specific [element] as part of the [validation].
     */
    protected abstract fun validateElement(element: T, parentContext: LocationContext?, validation: Validation)

    override fun validate(element: T, parentContext: LocationContext?): Validation = validation {
        validateElement(element, parentContext, this)

        validateProperties(element, parentContext, this)
    }

    /**
     * Validates the properties of the [element]. This is done through reflections by inspecting each property and determining if it includes a type we should validate against.
     */
    private fun validateProperties(element: T, parentContext: LocationContext?, validation: Validation) {
        val elementName = elementName ?: element.javaClass.simpleName

        element.javaClass.kotlin.memberProperties.forEach { property ->
            val kotlinType = property.returnType.jvmErasure
            val currentContext = parentContext.append(LocationContext(elementName, property.name))

            if (kotlinType.isSubclassOf(Primitive::class)) {
                val primitive = property.get(element) as? Primitive<*, *>
                primitive?.let {
                    validation.merge(validatePrimitive(it, currentContext))
                }
            } else if (kotlinType.isSubclassOf(Element::class)) {
                val datatype = property.get(element) as? Element<*>
                datatype?.let {
                    validation.merge(validateDatatype(it, currentContext))
                }
            } else if (kotlinType.isSubclassOf(DynamicValue::class)) {
                val dynamicValue = property.get(element) as? DynamicValue<*>
                dynamicValue?.let {
                    validation.merge(validateDynamicValue(it, currentContext))
                }
            } else if (kotlinType.isSubclassOf(Collection::class)) {
                val collection = property.get(element) as? Collection<*>
                collection?.let {
                    collection.filterIsInstance<Primitive<*, *>>()
                        .forEachIndexed { index, t ->
                            val indexedContext =
                                parentContext.append(LocationContext(elementName, "${property.name}[$index]"))

                            validation.merge(validatePrimitive(t, indexedContext))
                        }
                    collection.filterIsInstance<Element<*>>()
                        .forEachIndexed { index, t ->
                            val indexedContext =
                                parentContext.append(LocationContext(elementName, "${property.name}[$index]"))

                            validation.merge(validateDatatype(t, indexedContext))
                        }
                    collection.filterIsInstance<DynamicValue<*>>()
                        .forEachIndexed { index, t ->
                            val indexedContext =
                                parentContext.append(LocationContext(elementName, "${property.name}[$index]"))

                            validation.merge(validateDynamicValue(t, indexedContext))
                        }
                }
            }
        }
    }
}
