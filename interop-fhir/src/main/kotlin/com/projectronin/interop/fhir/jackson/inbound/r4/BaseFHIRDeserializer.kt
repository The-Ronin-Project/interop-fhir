package com.projectronin.interop.fhir.jackson.inbound.r4

import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.projectronin.interop.common.jackson.getAsTextOrNull
import com.projectronin.interop.common.jackson.readValueAs
import com.projectronin.interop.fhir.jackson.getDynamicValueOrNull
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Primitive
import com.projectronin.interop.fhir.r4.datatype.primitive.PrimitiveData
import com.projectronin.interop.fhir.validate.Validatable
import kotlin.reflect.KType
import kotlin.reflect.full.isSubclassOf
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.jvmErasure

abstract class BaseFHIRDeserializer<T : Validatable<T>>(private val clazz: Class<T>) :
    StdDeserializer<T>(clazz) {
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): T {
        val node = p.codec.readTree<JsonNode>(p) ?: throw JsonParseException(p, "unable to parse node")

        val typesByName = getTypesByName()
        val values = typesByName.mapValues { (name, type) ->
            readNode(node, p, name, type)
        }
        return createInstance(values)
    }

    private fun getTypesByName(): Map<String, KType> {
        return clazz.kotlin.memberProperties.associate {
            Pair(it.name, it.returnType)
        }
    }

    private fun readNode(node: JsonNode, parser: JsonParser, fieldName: String, type: KType): Any? {
        val kotlinType = type.jvmErasure
        val javaType = kotlinType.java

        return if (kotlinType == String::class) {
            node.getAsTextOrNull(fieldName)
        } else if (kotlinType == DynamicValue::class) {
            node.getDynamicValueOrNull(fieldName, parser)
        } else if (kotlinType == List::class) {
            val itemType = type.arguments[0].type!!.jvmErasure.java
            val list = node.get(fieldName)?.elements()?.asSequence()
                ?.map { if (it.isNull) null else it.readValueAs(parser, itemType) }?.toList()
                ?: emptyList()
            if (type.arguments[0].type!!.jvmErasure.isSubclassOf(Primitive::class)) {
                val primitiveDataList = node.get("_$fieldName")?.elements()?.asSequence()
                    ?.map { if (it.isNull) null else it.readValueAs(parser, PrimitiveData::class.java) }?.toList()
                    ?: emptyList()
                if (primitiveDataList.isEmpty()) {
                    list
                } else {
                    val primitiveValues = list.map { (it as? Primitive<*, *>)?.value }
                    val valueType =
                        itemType.kotlin.memberProperties.find { it.name == "value" }!!.returnType.jvmErasure.java
                    primitiveDataList.mapIndexed { i, primitiveData ->
                        when (primitiveData) {
                            null -> itemType.getDeclaredConstructor(valueType).newInstance(primitiveValues[i])
                            else -> itemType.getDeclaredConstructor(valueType, FHIRString::class.java, List::class.java)
                                .newInstance(primitiveValues[i], primitiveData.id, primitiveData.extension)
                        }
                    }
                }
            } else {
                list
            }
        } else if (kotlinType.isSubclassOf(Primitive::class)) {
            val primitiveExtensions = node.get("_$fieldName")
            val basePrimitive = node.get(fieldName)?.readValueAs(parser, javaType) as? Primitive<*, *>
            if (primitiveExtensions == null) {
                basePrimitive
            } else {
                val valueType = kotlinType.memberProperties.find { it.name == "value" }!!.returnType.jvmErasure.java
                val value = basePrimitive?.value
                val primitiveData = node.get("_$fieldName").readValueAs(parser, PrimitiveData::class.java)
                javaType.getDeclaredConstructor(valueType, FHIRString::class.java, List::class.java)
                    .newInstance(value, primitiveData.id, primitiveData.extension)
            }
        } else {
            node.get(fieldName)?.readValueAs(parser, javaType)
        }
    }

    private fun createInstance(values: Map<String, Any?>): T {
        val constructor = clazz.kotlin.constructors.first()
        val constructorParameters = constructor.parameters
        val actualParameters = constructorParameters.mapNotNull { param ->
            val value = values[param.name]
            if (!param.isOptional) {
                Pair(param, value)
            } else {
                value?.let { Pair(param, it) }
            }
        }.associate { it }
        return constructor.callBy(actualParameters)
    }
}
