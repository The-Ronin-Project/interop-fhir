package com.projectronin.interop.fhir.jackson

import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.JsonNode
import com.projectronin.interop.common.enums.CodedEnum
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType

/**
 * Resolver for [DynamicValue]s.
 */
class DynamicValueResolver(private val jsonParser: JsonParser) {
    /**
     * Resolves a [DynamicValue] from the supplied [node] based off the [typeString].
     */
    fun resolveDynamicValue(
        node: JsonNode,
        typeString: String,
    ): DynamicValue<Any> {
        if (typeString.isEmpty()) {
            return resolveNestedDynamicValue(node)
        }

        return resolveNonNestedDynamicValue(node, typeString)
    }

    private fun resolveNonNestedDynamicValue(
        node: JsonNode,
        typeString: String,
    ): DynamicValue<Any> {
        val type = getDynamicValueType(typeString)
        val value = type.readValue(node, jsonParser)

        return DynamicValue(type, value)
    }

    private fun resolveNestedDynamicValue(node: JsonNode): DynamicValue<Any> {
        val fieldNames = node.fieldNames().asSequence().toList()
        if (fieldNames.size != 1) {
            throw JsonParseException(
                jsonParser,
                "Encountered a nested dynamic value with an invalid number (${fieldNames.size}) of elements",
            )
        }

        val fieldName = fieldNames.first()
        val type = getDynamicValueType(fieldName.replaceFirstChar { it.uppercase() })
        val value = type.readValue(node.get(fieldName), jsonParser)

        return DynamicValue(type, value)
    }

    private fun getDynamicValueType(typeString: String) =
        CodedEnum.byCode<DynamicValueType>(typeString)
            ?: throw JsonParseException(jsonParser, "Unknown type for a dynamic value: $typeString")
}
