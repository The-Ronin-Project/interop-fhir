package com.projectronin.interop.fhir.jackson.outbound.r4.ronin

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.projectronin.interop.common.jackson.writeListField
import com.projectronin.interop.common.jackson.writeNullableField
import com.projectronin.interop.fhir.jackson.writeDynamicValueField
import com.projectronin.interop.fhir.r4.ronin.resource.OncologyObservation

/**
 * Jackson serializer for [OncologyObservation]s
 */
class OncologyObservationSerializer : BaseRoninDomainResourceSerializer<OncologyObservation>(OncologyObservation::class.java) {
    override fun serializeSpecificDomainElement(
        value: OncologyObservation,
        gen: JsonGenerator,
        provider: SerializerProvider,
    ) {
        gen.writeListField("identifier", value.identifier)
        gen.writeListField("basedOn", value.basedOn)
        gen.writeListField("partOf", value.partOf)
        gen.writeObjectField("status", value.status)
        gen.writeListField("category", value.category)
        gen.writeObjectField("code", value.code)
        gen.writeNullableField("subject", value.subject)
        gen.writeListField("focus", value.focus)
        gen.writeNullableField("encounter", value.encounter)
        gen.writeDynamicValueField("effective", value.effective)
        gen.writeNullableField("issued", value.issued)
        gen.writeListField("performer", value.performer)
        gen.writeDynamicValueField("value", value.value)
        gen.writeNullableField("dataAbsentReason", value.dataAbsentReason)
        gen.writeListField("interpretation", value.interpretation)
        gen.writeListField("note", value.note)
        gen.writeNullableField("bodySite", value.bodySite)
        gen.writeNullableField("method", value.method)
        gen.writeNullableField("specimen", value.specimen)
        gen.writeNullableField("device", value.device)
        gen.writeListField("referenceRange", value.referenceRange)
        gen.writeListField("hasMember", value.hasMember)
        gen.writeListField("derivedFrom", value.derivedFrom)
        gen.writeListField("component", value.component)
    }
}
