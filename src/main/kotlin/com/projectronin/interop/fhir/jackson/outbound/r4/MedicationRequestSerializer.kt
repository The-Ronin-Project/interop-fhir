package com.projectronin.interop.fhir.jackson.outbound.r4

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.projectronin.interop.common.jackson.writeListField
import com.projectronin.interop.common.jackson.writeNullableField
import com.projectronin.interop.fhir.jackson.writeDynamicValueField
import com.projectronin.interop.fhir.r4.resource.MedicationRequest

class MedicationRequestSerializer : BaseDomainResourceSerializer<MedicationRequest>(MedicationRequest::class.java) {
    override fun serializeSpecificDomainElement(
        value: MedicationRequest,
        gen: JsonGenerator,
        provider: SerializerProvider
    ) {
        gen.writeListField("identifier", value.identifier)
        gen.writeNullableField("status", value.status)
        gen.writeNullableField("statusReason", value.statusReason)
        gen.writeNullableField("intent", value.intent)
        gen.writeListField("category", value.category)
        gen.writeNullableField("priority", value.priority)
        gen.writeNullableField("doNotPerform", value.doNotPerform)
        gen.writeDynamicValueField("reported", value.reported)
        gen.writeDynamicValueField("medication", value.medication)
        gen.writeNullableField("subject", value.subject)
        gen.writeNullableField("encounter", value.encounter)
        gen.writeListField("supportingInformation", value.supportingInformation)
        gen.writeNullableField("authoredOn", value.authoredOn)
        gen.writeNullableField("requester", value.requester)
        gen.writeNullableField("performer", value.performer)
        gen.writeNullableField("performerType", value.performerType)
        gen.writeNullableField("recorder", value.recorder)
        gen.writeListField("reasonCode", value.reasonCode)
        gen.writeListField("reasonReference", value.reasonReference)
        gen.writeListField("instantiatesCanonical", value.instantiatesCanonical)
        gen.writeListField("instantiatesUri", value.instantiatesUri)
        gen.writeListField("basedOn", value.basedOn)
        gen.writeNullableField("groupIdentifier", value.groupIdentifier)
        gen.writeNullableField("courseOfTherapyType", value.courseOfTherapyType)
        gen.writeListField("insurance", value.insurance)
        gen.writeListField("note", value.note)
        gen.writeListField("dosageInformation", value.dosageInformation)
        gen.writeNullableField("dispenseRequest", value.dispenseRequest)
        gen.writeNullableField("substitution", value.substitution)
        gen.writeNullableField("priorPrescription", value.priorPrescription)
        gen.writeListField("detectedIssue", value.detectedIssue)
        gen.writeListField("eventHistory", value.eventHistory)
    }
}
