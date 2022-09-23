package com.projectronin.interop.fhir.jackson.outbound.stu3

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.projectronin.interop.common.jackson.writeListField
import com.projectronin.interop.common.jackson.writeNullableField
import com.projectronin.interop.fhir.jackson.writeDynamicValueField
import com.projectronin.interop.fhir.stu3.datatype.STU3Dosage

/**
 * Jackson serializer for [STU3Dosage]s
 */
class DosageSerializer : BaseElementSerializer<STU3Dosage>(STU3Dosage::class.java) {
    override fun serializeSpecificElement(value: STU3Dosage, gen: JsonGenerator, provider: SerializerProvider) {
        gen.writeNullableField("sequence", value.sequence)
        gen.writeNullableField("text", value.text)
        gen.writeListField("additionalInstruction", value.additionalInstruction)
        gen.writeNullableField("patientInstruction", value.patientInstruction)
        gen.writeNullableField("timing", value.timing)
        gen.writeDynamicValueField("asNeeded", value.asNeeded)
        gen.writeNullableField("site", value.site)
        gen.writeNullableField("route", value.route)
        gen.writeNullableField("method", value.method)
        gen.writeDynamicValueField("dose", value.dose)
        gen.writeDynamicValueField("rate", value.rate)
        gen.writeNullableField("maxDosePerPeriod", value.maxDosePerPeriod)
        gen.writeNullableField("maxDosePerAdministration", value.maxDosePerAdministration)
        gen.writeNullableField("maxDosePerLifetime", value.maxDosePerLifetime)
    }
}
