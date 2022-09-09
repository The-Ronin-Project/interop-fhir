package com.projectronin.interop.fhir.r4.resource

import com.fasterxml.jackson.annotation.JsonTypeName
import com.projectronin.interop.fhir.r4.datatype.Batch
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.Ingredient
import com.projectronin.interop.fhir.r4.datatype.Meta
import com.projectronin.interop.fhir.r4.datatype.Narrative
import com.projectronin.interop.fhir.r4.datatype.Ratio
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri

/**
 * This resource is primarily used for the identification and definition of a medication for the purposes of
 * prescribing, dispensing, and administering a medication as well as for making statements about medication use.
 *
 * See [FHIR Spec](https://www.hl7.org/fhir/R4/medication.html)
 */
@JsonTypeName("Medication")
data class Medication(
    override val id: Id? = null,
    override val meta: Meta? = null,
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
