package com.projectronin.interop.fhir.jackson.inbound.r4

import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.projectronin.interop.common.jackson.getAsList
import com.projectronin.interop.common.jackson.getAsOrNull
import com.projectronin.interop.fhir.jackson.getDynamicValueOrNull
import com.projectronin.interop.fhir.r4.resource.MedicationStatement

class MedicationStatementDeserializer : StdDeserializer<MedicationStatement>(MedicationStatement::class.java) {
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): MedicationStatement {
        val node = p.codec.readTree<JsonNode>(p) ?: throw JsonParseException(p, "Unable to parse node")

        return MedicationStatement(
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
            status = node.getAsOrNull("status", p),
            statusReason = node.getAsList("statusReason", p),
            category = node.getAsOrNull("category", p),
            medication = node.getDynamicValueOrNull("medication", p),
            subject = node.getAsOrNull("subject", p),
            context = node.getAsOrNull("context", p),
            effective = node.getDynamicValueOrNull("effective", p),
            dateAsserted = node.getAsOrNull("dateAsserted", p),
            informationSource = node.getAsOrNull("informationSource", p),
            derivedFrom = node.getAsList("derivedFrom", p),
            reasonCode = node.getAsList("reasonCode", p),
            reasonReference = node.getAsList("reasonReference", p),
            note = node.getAsList("note", p),
            dosage = node.getAsList("dosage", p),
        )
    }
}
