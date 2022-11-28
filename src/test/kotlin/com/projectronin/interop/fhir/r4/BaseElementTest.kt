package com.projectronin.interop.fhir.r4

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.Element
import com.projectronin.interop.fhir.r4.resource.ContainedResource
import com.projectronin.interop.fhir.r4.resource.Resource
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.fail
import kotlin.reflect.KClass
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.isSubclassOf
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.jvmErasure

abstract class BaseElementTest {
    /**
     * Verifies the provided [classes] contain a Serializer, Deserializer and only contain valid data types for each property.
     * Any properties that should be ignored can be provded as the [ignoredProperties].
     */
    protected fun verifyElements(classes: List<KClass<*>>, vararg ignoredProperties: String) {
        classes.forEach { clazz ->
            clazz.findAnnotation<JsonSerialize>() ?: fail { "No JsonSerialize found for ${clazz.simpleName}" }
            clazz.findAnnotation<JsonDeserialize>()
                ?: fail { "No JsonDeserialize found for ${clazz.simpleName}" }

            clazz.memberProperties.forEach { property ->
                if (!ignoredProperties.contains(property.name)) {
                    val returnType = property.returnType
                    val jvmType = returnType.jvmErasure
                    val typeToCheck = if (jvmType.isSubclassOf(List::class)) {
                        returnType.arguments[0].type!!.jvmErasure
                    } else {
                        jvmType
                    }

                    val valid = when {
                        typeToCheck == DynamicValue::class -> true
                        typeToCheck == ContainedResource::class -> true
                        typeToCheck.isSubclassOf(Element::class) -> true
                        typeToCheck.isSubclassOf(Resource::class) -> true
                        else -> false
                    }

                    assertTrue(valid, "${clazz.simpleName}.${property.name} is a non-Element type")
                }
            }
        }
    }
}
