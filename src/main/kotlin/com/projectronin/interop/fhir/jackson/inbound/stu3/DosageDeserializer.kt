package com.projectronin.interop.fhir.jackson.inbound.stu3

import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.projectronin.interop.common.jackson.getAsIntOrNull
import com.projectronin.interop.common.jackson.getAsList
import com.projectronin.interop.common.jackson.getAsOrNull
import com.projectronin.interop.common.jackson.getAsTextOrNull
import com.projectronin.interop.fhir.jackson.getDynamicValueOrNull
import com.projectronin.interop.fhir.r4.datatype.Dosage
import com.projectronin.interop.fhir.stu3.datatype.STU3Dosage

/**
 * Jackson deserializer for [Dosage]s
 */
class DosageDeserializer : StdDeserializer<STU3Dosage>(STU3Dosage::class.java) {
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): STU3Dosage {
        val node = p.codec.readTree<JsonNode>(p) ?: throw JsonParseException(p, "Unable to parse node")

        return STU3Dosage(
            id = node.getAsTextOrNull("id"),
            extension = node.getAsList("extension", p),
            sequence = node.getAsIntOrNull("sequence"),
            text = node.getAsTextOrNull("text"),
            additionalInstruction = node.getAsList("additionalInstruction", p),
            patientInstruction = node.getAsTextOrNull("patientInstruction"),
            timing = node.getAsOrNull("timing", p),
            asNeeded = node.getDynamicValueOrNull("asNeeded", p),
            site = node.getAsOrNull("site", p),
            route = node.getAsOrNull("route", p),
            method = node.getAsOrNull("method", p),
            dose = node.getDynamicValueOrNull("dose", p),
            rate = node.getDynamicValueOrNull("rate", p),
            maxDosePerPeriod = node.getAsOrNull("maxDosePerPeriod", p),
            maxDosePerAdministration = node.getAsOrNull("maxDosePerAdministration", p),
            maxDosePerLifetime = node.getAsOrNull("maxDosePerLifetime", p)
        )
    }
}
