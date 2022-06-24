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
import com.projectronin.interop.fhir.r4.resource.Observation

/**
 * Jackson deserializer for [Observation]s
 */
class ObservationDeserializer : StdDeserializer<Observation>(Observation::class.java) {
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): Observation {
        val node = p.codec.readTree<JsonNode>(p) ?: throw JsonParseException(p, "Unable to parse node")

        return Observation(
            id = node.getAsOrNull("id", p),
            meta = node.getAsOrNull("meta", p),
            implicitRules = node.getAsOrNull("implicitRules", p),
            language = node.getAsOrNull("language", p),
            text = node.getAsOrNull("text", p),
            contained = node.getAsList("contained", p),
            extension = node.getAsList("extension", p),
            modifierExtension = node.getAsList("modifierExtension", p),
            identifier = node.getAsList("identifier", p),
            basedOn = node.getAsList("basedOn", p),
            partOf = node.getAsList("partOf", p),
            status = node.getAs("status", p),
            category = node.getAsList("category", p),
            code = node.getAs("code", p),
            subject = node.getAsOrNull("subject", p),
            focus = node.getAsList("focus", p),
            encounter = node.getAsOrNull("encounter", p),
            effective = node.getDynamicValueOrNull("effective", p),
            issued = node.getAsOrNull("issued", p),
            performer = node.getAsList("performer", p),
            value = node.getDynamicValueOrNull("value", p),
            dataAbsentReason = node.getAsOrNull("dataAbsentReason", p),
            interpretation = node.getAsList("interpretation", p),
            note = node.getAsList("note", p),
            bodySite = node.getAsOrNull("bodySite", p),
            method = node.getAsOrNull("method", p),
            specimen = node.getAsOrNull("specimen", p),
            device = node.getAsOrNull("device", p),
            referenceRange = node.getAsList("referenceRange", p),
            hasMember = node.getAsList("hasMember", p),
            derivedFrom = node.getAsList("derivedFrom", p),
            component = node.getAsList("component", p),
        )
    }
}
