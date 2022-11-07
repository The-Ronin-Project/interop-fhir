package com.projectronin.interop.fhir.jackson.outbound.r4

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.jsontype.TypeSerializer
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import com.projectronin.interop.common.jackson.writeListField
import com.projectronin.interop.common.jackson.writeNullableField
import com.projectronin.interop.fhir.jackson.writeDynamicValueField
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.primitive.Primitive
import com.projectronin.interop.fhir.r4.datatype.primitive.PrimitiveData
import com.projectronin.interop.fhir.validate.Validatable
import kotlin.reflect.KProperty1
import kotlin.reflect.full.isSubclassOf
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.jvmErasure

abstract class BaseFHIRSerializer<T : Validatable<T>>(private val clazz: Class<T>) : StdSerializer<T>(clazz) {

    override fun serialize(value: T, gen: JsonGenerator, provider: SerializerProvider) {
        gen.writeStartObject()
        // need to order the properties in declared order instead of generated order
        val properties = clazz.kotlin.memberProperties.toList()
        val orderedFields = clazz.declaredFields.toList()
        val orderedProperties = arrayListOf<KProperty1<T, *>>()

        orderedFields.forEach { field ->
            properties.forEach { property ->
                if (property.name == field.name) {
                    if (property.name == "resourceType") {
                        orderedProperties.add(0, property)
                    } else {
                        orderedProperties.add(property)
                    }
                }
            }
        }
        orderedProperties.forEach { property -> writeField(gen, value, property.name, property) }
        gen.writeEndObject()
    }

    override fun serializeWithType(
        value: T,
        gen: JsonGenerator,
        serializers: SerializerProvider,
        typeSer: TypeSerializer
    ) {
        serialize(value, gen, serializers)
    }

    private fun writeField(gen: JsonGenerator, value: T, fieldName: String, property: KProperty1<T, *>) {
        val kotlinType = property.returnType.jvmErasure
        val fieldValue = property.get(value)

        if (fieldValue != null) {
            if (kotlinType == List::class) {
                gen.writeListField(fieldName, fieldValue as List<*>)
                if (fieldValue.isNotEmpty() && fieldValue[0]?.javaClass?.kotlin?.isSubclassOf(Primitive::class) == true) {
                    val primitiveDataList = fieldValue.map { primitive ->
                        primitive as Primitive<*, *>
                        if (primitive.hasPrimitiveData()) {
                            PrimitiveData(id = primitive.id, extension = primitive.extension)
                        } else {
                            null
                        }
                    }
                    if (primitiveDataList.any { it != null }) {
                        gen.writeListField("_$fieldName", primitiveDataList)
                    }
                }
            } else if (kotlinType == DynamicValue::class) {
                gen.writeDynamicValueField(fieldName, fieldValue as DynamicValue<*>)
            } else if (kotlinType.isSubclassOf(Primitive::class)) {
                val primitive = fieldValue as Primitive<*, *>
                gen.writeNullableField(fieldName, fieldValue.value)
                if (primitive.hasPrimitiveData()) {
                    val primitiveData = PrimitiveData(
                        primitive.id,
                        primitive.extension
                    )
                    gen.writeObjectField("_$fieldName", primitiveData)
                }
            } else {
                gen.writeNullableField(fieldName, fieldValue)
            }
        }
    }
}
