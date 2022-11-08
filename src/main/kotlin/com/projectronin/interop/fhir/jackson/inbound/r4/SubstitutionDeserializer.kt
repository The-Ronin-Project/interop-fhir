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
import com.projectronin.interop.fhir.r4.datatype.medication.Substitution

class SubstitutionDeserializer : StdDeserializer<Substitution>(Substitution::class.java) {
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): Substitution {
        val node = p.codec.readTree<JsonNode>(p) ?: throw JsonParseException(p, "Unable to parse node")

        return Substitution(
            id = node.getAsTextOrNull("id"),
            extension = node.getAsList("extension", p),
            allowed = node.getDynamicValueOrNull("allowed", p),
            reason = node.getAsOrNull("reason", p)
        )
    }
}
