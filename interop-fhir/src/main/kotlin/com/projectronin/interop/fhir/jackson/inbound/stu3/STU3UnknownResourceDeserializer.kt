package com.projectronin.interop.fhir.jackson.inbound.stu3

import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonNode
import com.projectronin.interop.common.jackson.getAsOrNull
import com.projectronin.interop.common.jackson.getAsTextOrNull
import com.projectronin.interop.fhir.jackson.inbound.r4.BaseFHIRDeserializer
import com.projectronin.interop.fhir.stu3.resource.STU3UnknownResource

/**
 * Jackson deserializer for [STU3UnknownResource]s
 */
class STU3UnknownResourceDeserializer : BaseFHIRDeserializer<STU3UnknownResource>(STU3UnknownResource::class.java) {
    private val discreteValues = setOf("resourceType", "id", "meta", "implicitRules", "language")

    override fun deserialize(
        p: JsonParser,
        ctxt: DeserializationContext,
    ): STU3UnknownResource {
        // First we read the tree so that we can easily handle some of the required fields for a resource.
        val node = p.codec.readTree<JsonNode>(p) ?: throw JsonParseException(p, "Unable to parse node")

        // But now we want a Map for everything else, so we have to do some transformations using the node
        val mapParser = p.codec.treeAsTokens(node)
        val typeRef = object : TypeReference<Map<String, Any?>>() {}
        val map = mapParser.readValueAs<Map<String, Any?>>(typeRef)

        val otherData = map.filterNot { discreteValues.contains(it.key) }

        return STU3UnknownResource(
            resourceType = node.getAsTextOrNull("resourceType")!!,
            id = node.getAsOrNull("id", p),
            meta = node.getAsOrNull("meta", p),
            implicitRules = node.getAsOrNull("implicitRules", p),
            language = node.getAsOrNull("language", p),
            otherData = otherData,
        )
    }
}
