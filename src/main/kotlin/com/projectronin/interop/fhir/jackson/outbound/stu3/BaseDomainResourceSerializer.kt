package com.projectronin.interop.fhir.jackson.outbound.stu3

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.projectronin.interop.common.jackson.writeListField
import com.projectronin.interop.common.jackson.writeNullableField
import com.projectronin.interop.fhir.stu3.resource.STU3DomainResource

/**
 * Base serializer for helping serialize [DomainResource]s
 */
abstract class BaseDomainResourceSerializer<T : STU3DomainResource<T>>(clazz: Class<T>) :
    BaseResourceSerializer<T>(clazz) {
    /**
     * Writes the specific domain element attributes of [value] to the [gen]. Common element items such as id and extension do not need to be written.
     */
    abstract fun serializeSpecificDomainElement(value: T, gen: JsonGenerator, provider: SerializerProvider)

    override fun serializeSpecificElement(value: T, gen: JsonGenerator, provider: SerializerProvider) {
        gen.writeNullableField("text", value.text)
        gen.writeListField("contained", value.contained)
        gen.writeListField("extension", value.extension)
        gen.writeListField("modifierExtension", value.modifierExtension)

        serializeSpecificDomainElement(value, gen, provider)
    }
}
