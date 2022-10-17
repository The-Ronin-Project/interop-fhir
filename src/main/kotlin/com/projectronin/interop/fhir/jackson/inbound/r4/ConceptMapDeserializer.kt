package com.projectronin.interop.fhir.jackson.inbound.r4

import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.projectronin.interop.common.jackson.getAsList
import com.projectronin.interop.common.jackson.getAsOrNull
import com.projectronin.interop.fhir.jackson.getDynamicValueOrNull
import com.projectronin.interop.fhir.r4.resource.ConceptMap

class ConceptMapDeserializer : StdDeserializer<ConceptMap>(ConceptMap::class.java) {
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext?): ConceptMap {
        val node = p.codec.readTree<JsonNode>(p) ?: throw JsonParseException(p, "Unable to parse node")
        return ConceptMap(
            id = node.getAsOrNull("id", p),
            meta = node.getAsOrNull("meta", p),
            implicitRules = node.getAsOrNull("implicitRules", p),
            language = node.getAsOrNull("language", p),
            text = node.getAsOrNull("text", p),
            contained = node.getAsList("contained", p),
            extension = node.getAsList("extension", p),
            modifierExtension = node.getAsList("modifierExtension", p),
            url = node.getAsOrNull("url", p),
            identifier = node.getAsOrNull("identifier", p),
            version = node.getAsOrNull("version", p),
            name = node.getAsOrNull("name", p),
            status = node.getAsOrNull("status", p),
            experimental = node.getAsOrNull("experimental", p),
            date = node.getAsOrNull("date", p),
            publisher = node.getAsOrNull("publisher", p),
            contact = node.getAsList("contact", p),
            description = node.getAsOrNull("description", p),
            useContext = node.getAsList("useContext", p),
            jurisdiction = node.getAsList("jurisdiction", p),
            purpose = node.getAsOrNull("purpose", p),
            copyright = node.getAsOrNull("copyright", p),
            source = node.getDynamicValueOrNull("source", p),
            target = node.getDynamicValueOrNull("target", p),
            group = node.getAsList("group", p),
        )
    }
}
