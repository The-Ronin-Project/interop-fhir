package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.interop.fhir.jackson.inbound.r4.IngredientDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.IngredientSerializer

/**
 * Identifies a particular constituent of interest in the product.
 *
 * See [FHIR Spec](https://www.hl7.org/fhir/R4/medication-definitions.html#Medication.ingredient)
 */
@JsonDeserialize(using = IngredientDeserializer::class)
@JsonSerialize(using = IngredientSerializer::class)
data class Ingredient(
    override val id: String? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val item: DynamicValue<Any>? = null,
    val isActive: Boolean? = null,
    val strength: Ratio? = null
) : BackboneElement<Ingredient>