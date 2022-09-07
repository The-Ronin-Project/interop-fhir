package com.projectronin.interop.fhir.jackson.outbound.r4

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.projectronin.interop.common.jackson.writeListField
import com.projectronin.interop.common.jackson.writeNullableField
import com.projectronin.interop.fhir.jackson.writeDynamicValueField
import com.projectronin.interop.fhir.r4.resource.ConceptMap

class ConceptMapSerializer : BaseDomainResourceSerializer<ConceptMap>(ConceptMap::class.java) {
    override fun serializeSpecificDomainElement(value: ConceptMap, gen: JsonGenerator, provider: SerializerProvider) {
        gen.writeNullableField("url", value.url)
        gen.writeNullableField("identifier", value.identifier)
        gen.writeNullableField("version", value.version)
        gen.writeNullableField("name", value.name)
        gen.writeObjectField("status", value.status)
        gen.writeNullableField("experimental", value.experimental)
        gen.writeNullableField("date", value.date)
        gen.writeNullableField("publisher", value.publisher)
        gen.writeListField("contact", value.contact)
        gen.writeNullableField("description", value.description)
        gen.writeListField("contact", value.contact)
        gen.writeListField("useContext", value.useContext)
        gen.writeListField("jurisdiction", value.jurisdiction)
        gen.writeNullableField("purpose", value.purpose)
        gen.writeNullableField("copyright", value.copyright)
        gen.writeDynamicValueField("source", value.source)
        gen.writeDynamicValueField("target", value.target)
        gen.writeListField("group", value.group)
    }
}
