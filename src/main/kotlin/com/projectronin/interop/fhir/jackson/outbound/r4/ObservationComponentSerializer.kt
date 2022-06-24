package com.projectronin.interop.fhir.jackson.outbound.r4

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.projectronin.interop.common.jackson.writeListField
import com.projectronin.interop.common.jackson.writeNullableField
import com.projectronin.interop.fhir.jackson.writeDynamicValueField
import com.projectronin.interop.fhir.r4.datatype.ObservationComponent

/**
 * Jackson serializer for [ObservationComponent]s
 */
class ObservationComponentSerializer : BaseElementSerializer<ObservationComponent>(ObservationComponent::class.java) {
    override fun serializeSpecificElement(value: ObservationComponent, gen: JsonGenerator, provider: SerializerProvider) {
        gen.writeObjectField("code", value.code)
        gen.writeDynamicValueField("value", value.value)
        gen.writeNullableField("dataAbsentReason", value.dataAbsentReason)
        gen.writeListField("interpretation", value.interpretation)
        gen.writeListField("referenceRange", value.referenceRange)
    }
}
