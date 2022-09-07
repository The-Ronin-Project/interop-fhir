package com.projectronin.interop.fhir.jackson.outbound.r4

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.projectronin.interop.common.jackson.writeListField
import com.projectronin.interop.common.jackson.writeNullableField
import com.projectronin.interop.fhir.jackson.writeDynamicValueField
import com.projectronin.interop.fhir.r4.resource.MedicationStatement

class MedicationStatementSerializer : BaseDomainResourceSerializer<MedicationStatement>(MedicationStatement::class.java) {
    override fun serializeSpecificDomainElement(value: MedicationStatement, gen: JsonGenerator, provider: SerializerProvider) {
        gen.writeListField("identifier", value.identifier)
        gen.writeListField("basedOn", value.basedOn)
        gen.writeListField("partOf", value.partOf)
        gen.writeObjectField("status", value.status)
        gen.writeListField("statusReason", value.statusReason)
        gen.writeNullableField("category", value.category)
        gen.writeDynamicValueField("medication", value.medication)
        gen.writeObjectField("subject", value.subject)
        gen.writeNullableField("context", value.context)
        gen.writeDynamicValueField("effective", value.effective)
        gen.writeNullableField("dateAsserted", value.dateAsserted)
        gen.writeNullableField("informationSource", value.informationSource)
        gen.writeListField("derivedFrom", value.derivedFrom)
        gen.writeListField("reasonCode", value.reasonCode)
        gen.writeListField("reasonReference", value.reasonReference)
        gen.writeListField("note", value.note)
        gen.writeListField("dosage", value.dosage)
    }
}
