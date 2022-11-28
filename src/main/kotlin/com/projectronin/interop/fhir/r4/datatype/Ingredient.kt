package com.projectronin.interop.fhir.r4.datatype

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.interop.fhir.jackson.inbound.r4.BaseFHIRDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.BaseFHIRSerializer
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRBoolean
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString

/**
 * Identifies a particular constituent of interest in the product.
 *
 * See [FHIR Spec](https://www.hl7.org/fhir/R4/medication-definitions.html#Medication.ingredient)
 */
@JsonDeserialize(using = IngredientDeserializer::class)
@JsonSerialize(using = IngredientSerializer::class)
data class Ingredient(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val item: DynamicValue<Any>? = null,
    val isActive: FHIRBoolean? = null,
    val strength: Ratio? = null
) : BackboneElement<Ingredient>

class IngredientDeserializer : BaseFHIRDeserializer<Ingredient>(Ingredient::class.java)
class IngredientSerializer : BaseFHIRSerializer<Ingredient>(Ingredient::class.java)
