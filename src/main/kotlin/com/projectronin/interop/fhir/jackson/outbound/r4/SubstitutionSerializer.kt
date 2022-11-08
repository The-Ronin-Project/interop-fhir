package com.projectronin.interop.fhir.jackson.outbound.r4

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.projectronin.interop.common.jackson.writeNullableField
import com.projectronin.interop.fhir.jackson.writeDynamicValueField
import com.projectronin.interop.fhir.r4.datatype.medication.Substitution

class SubstitutionSerializer : BaseElementSerializer<Substitution>(Substitution::class.java) {
    override fun serializeSpecificElement(value: Substitution, gen: JsonGenerator, provider: SerializerProvider) {
        gen.writeDynamicValueField("allowed", value.allowed)
        gen.writeNullableField("reason", value.reason)
    }
}
