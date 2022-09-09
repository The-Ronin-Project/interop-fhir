package com.projectronin.interop.fhir.jackson.inbound.r4

import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.projectronin.interop.common.jackson.getAsList
import com.projectronin.interop.common.jackson.getAsOrNull
import com.projectronin.interop.common.jackson.getAsTextOrNull
import com.projectronin.interop.fhir.jackson.getDynamicValueOrNull
import com.projectronin.interop.fhir.r4.datatype.Ingredient

/**
 * Jackson deserializer for [Ingredient]s
 */
class IngredientDeserializer : StdDeserializer<Ingredient>(Ingredient::class.java) {
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): Ingredient {
        val node = p.codec.readTree<JsonNode>(p) ?: throw JsonParseException(p, "Unable to parse node")

        return Ingredient(
            id = node.getAsTextOrNull("id"),
            extension = node.getAsList("extension", p),
            modifierExtension = node.getAsList("modifierExtension", p),
            item = node.getDynamicValueOrNull("item", p),
            isActive = node.getAsOrNull("isActive", p),
            strength = node.getAsOrNull("strength", p)
        )
    }
}
