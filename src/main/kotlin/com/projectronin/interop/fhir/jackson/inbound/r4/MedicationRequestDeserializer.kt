package com.projectronin.interop.fhir.jackson.inbound.r4

import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.projectronin.interop.common.jackson.getAsList
import com.projectronin.interop.common.jackson.getAsOrNull
import com.projectronin.interop.fhir.jackson.getDynamicValueOrNull
import com.projectronin.interop.fhir.r4.resource.MedicationRequest

class MedicationRequestDeserializer : StdDeserializer<MedicationRequest>(MedicationRequest::class.java) {
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): MedicationRequest {
        val node = p.codec.readTree<JsonNode>(p) ?: throw JsonParseException(p, "Unable to parse node")

        return MedicationRequest(
            id = node.getAsOrNull("id", p),
            meta = node.getAsOrNull("meta", p),
            implicitRules = node.getAsOrNull("implicitRules", p),
            language = node.getAsOrNull("language", p),
            text = node.getAsOrNull("text", p),
            contained = node.getAsList("contained", p),
            extension = node.getAsList("extension", p),
            modifierExtension = node.getAsList("modifierExtension", p),
            identifier = node.getAsList("identifier", p),
            status = node.getAsOrNull("status", p),
            statusReason = node.getAsOrNull("statusReason", p),
            intent = node.getAsOrNull("intent", p),
            category = node.getAsList("category", p),
            priority = node.getAsOrNull("priority", p),
            doNotPerform = node.getAsOrNull("doNotPerform", p),
            reported = node.getDynamicValueOrNull("reported", p),
            medication = node.getDynamicValueOrNull("medication", p),
            subject = node.getAsOrNull("subject", p),
            encounter = node.getAsOrNull("encounter", p),
            supportingInformation = node.getAsList("supportingInformation", p),
            authoredOn = node.getAsOrNull("authoredOn", p),
            requester = node.getAsOrNull("requester", p),
            performer = node.getAsOrNull("performer", p),
            performerType = node.getAsOrNull("performerType", p),
            recorder = node.getAsOrNull("recorder", p),
            reasonCode = node.getAsList("reasonCode", p),
            reasonReference = node.getAsList("reasonReference", p),
            instantiatesCanonical = node.getAsList("instantiatesCanonical", p),
            instantiatesUri = node.getAsList("instantiatesUri", p),
            basedOn = node.getAsList("basedOn", p),
            groupIdentifier = node.getAsOrNull("groupIdentifier", p),
            courseOfTherapyType = node.getAsOrNull("courseOfTherapyType", p),
            insurance = node.getAsList("insurance", p),
            note = node.getAsList("note", p),
            dosageInformation = node.getAsList("dosageInformation", p),
            dispenseRequest = node.getAsOrNull("dispenseRequest", p),
            substitution = node.getAsOrNull("substitution", p),
            priorPrescription = node.getAsOrNull("priorPrescription", p),
            detectedIssue = node.getAsList("detectedIssue", p),
            eventHistory = node.getAsList("eventHistory", p)
        )
    }
}
