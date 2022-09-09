package com.projectronin.interop.fhir.jackson.outbound.r4

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.projectronin.interop.common.jackson.writeNullableField
import com.projectronin.interop.fhir.jackson.writeDynamicValueField
import com.projectronin.interop.fhir.r4.datatype.Ingredient

class IngredientSerializer : BaseBackboneElementSerializer<Ingredient>(Ingredient::class.java) {
    override fun serializeSpecificBackBoneElement(value: Ingredient, gen: JsonGenerator, provider: SerializerProvider) {
        gen.writeDynamicValueField("item", value.item)
        gen.writeNullableField("isActive", value.isActive)
        gen.writeNullableField("strength", value.strength)
    }
}
