package com.projectronin.interop.fhir.r4.resource

import com.fasterxml.jackson.annotation.JsonTypeName
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.interop.fhir.jackson.inbound.r4.BaseFHIRDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.BaseFHIRSerializer
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.Meta
import com.projectronin.interop.fhir.r4.datatype.Narrative
import com.projectronin.interop.fhir.r4.datatype.Ratio
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRBoolean
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.element.BackboneElement

/**
 * This resource is primarily used for the identification and definition of a medication for the purposes of
 * prescribing, dispensing, and administering a medication as well as for making statements about medication use.
 *
 * See [FHIR Spec](https://www.hl7.org/fhir/R4/medication.html)
 */
@JsonSerialize(using = MedicationSerializer::class)
@JsonDeserialize(using = MedicationDeserializer::class)
@JsonTypeName("Medication")
data class Medication(
    override val id: Id? = null,
    override var meta: Meta? = null,
    override val implicitRules: Uri? = null,
    override val language: Code? = null,
    override val text: Narrative? = null,
    override val contained: List<ContainedResource> = listOf(),
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val identifier: List<Identifier> = listOf(),
    val code: CodeableConcept? = null,
    val status: Code? = null,
    val manufacturer: Reference? = null,
    val form: CodeableConcept? = null,
    val amount: Ratio? = null,
    val ingredient: List<Ingredient> = listOf(),
    val batch: Batch? = null
) : DomainResource<Medication> {
    override val resourceType: String = "Medication"
}

class MedicationSerializer : BaseFHIRSerializer<Medication>(Medication::class.java)
class MedicationDeserializer : BaseFHIRDeserializer<Medication>(Medication::class.java)

/**
 * Information that only applies to packages (not products).
 *
 * See [FHIR Spec](https://www.hl7.org/fhir/R4/medication-definitions.html#Medication.batch)
 */
@JsonSerialize(using = BatchSerializer::class)
@JsonDeserialize(using = BatchDeserializer::class)
data class Batch(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val lotNumber: FHIRString? = null,
    val expirationDate: DateTime? = null
) : BackboneElement<Batch>

class BatchSerializer : BaseFHIRSerializer<Batch>(Batch::class.java)
class BatchDeserializer : BaseFHIRDeserializer<Batch>(Batch::class.java)

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
