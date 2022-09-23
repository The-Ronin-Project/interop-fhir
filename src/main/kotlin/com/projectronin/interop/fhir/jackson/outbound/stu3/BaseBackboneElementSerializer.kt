package com.projectronin.interop.fhir.jackson.outbound.stu3

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.projectronin.interop.common.jackson.writeListField
import com.projectronin.interop.fhir.stu3.datatype.STU3BackboneElement

/**
 * Base serializer for helping serialize [STU3BackboneElement]s
 */
abstract class BaseBackboneElementSerializer<T : STU3BackboneElement<T>>(clazz: Class<T>) : BaseElementSerializer<T>(clazz) {
    /**
     * Writes the specific backbone element attributes of [value] to the [gen]. Common element items such as id and
     * extension do not need to be written.
     */
    abstract fun serializeSpecificBackBoneElement(value: T, gen: JsonGenerator, provider: SerializerProvider)

    override fun serializeSpecificElement(value: T, gen: JsonGenerator, provider: SerializerProvider) {
        gen.writeListField("modifierExtension", value.modifierExtension)
        serializeSpecificBackBoneElement(value, gen, provider)
    }
}
