package com.projectronin.interop.fhir.jackson.outbound.stu3

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.projectronin.interop.fhir.stu3.resource.STU3UnknownResource

/**
 * Jackson serializer for [STU3UnknownResource]s
 */
class STU3UnknownResourceSerializer : BaseSTU3ResourceSerializer<STU3UnknownResource>(STU3UnknownResource::class.java) {
    override fun serializeSpecificElement(
        value: STU3UnknownResource,
        gen: JsonGenerator,
        provider: SerializerProvider
    ) {
        value.otherData.entries.forEach { (key, value) ->
            value?.let {
                gen.writeObjectField(key, it)
            }
        }
    }
}
