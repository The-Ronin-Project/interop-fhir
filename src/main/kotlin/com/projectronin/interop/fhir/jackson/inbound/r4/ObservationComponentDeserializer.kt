package com.projectronin.interop.fhir.jackson.inbound.r4

import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.projectronin.interop.common.jackson.getAs
import com.projectronin.interop.common.jackson.getAsList
import com.projectronin.interop.common.jackson.getAsOrNull
import com.projectronin.interop.fhir.jackson.getDynamicValueOrNull
import com.projectronin.interop.fhir.r4.datatype.ObservationComponent

/**
 * Jackson deserializer for [ObservationComponent]s
 */
class ObservationComponentDeserializer : StdDeserializer<ObservationComponent>(ObservationComponent::class.java) {
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): ObservationComponent {
        val node = p.codec.readTree<JsonNode>(p) ?: throw JsonParseException(p, "Unable to parse node")

        return ObservationComponent(
            id = node.getAsOrNull("id", p),
            extension = node.getAsList("extension", p),
            modifierExtension = node.getAsList("modifierExtension", p),
            code = node.getAs("code", p),
            value = node.getDynamicValueOrNull("value", p),
            dataAbsentReason = node.getAsOrNull("dataAbsentReason", p),
            interpretation = node.getAsList("interpretation", p),
            referenceRange = node.getAsList("referenceRange", p),
        )
    }
}
