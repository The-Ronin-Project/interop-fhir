package com.projectronin.interop.fhir.r4.resource

import com.fasterxml.jackson.annotation.JsonTypeName
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.interop.fhir.jackson.inbound.r4.BaseFHIRDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.BaseFHIRSerializer
import com.projectronin.interop.fhir.r4.datatype.Annotation
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.Meta
import com.projectronin.interop.fhir.r4.datatype.Narrative
import com.projectronin.interop.fhir.r4.datatype.Quantity
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.element.BackboneElement

@JsonDeserialize(using = MedicationAdministrationDeserializer::class)
@JsonSerialize(using = MedicationAdministrationSerializer::class)
@JsonTypeName("MedicationAdministration")
data class MedicationAdministration(
    override val id: Id? = null,
    override var meta: Meta? = null,
    override val implicitRules: Uri? = null,
    override val language: Code? = null,
    override val text: Narrative? = null,
    override val contained: List<Resource<*>> = listOf(),
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val identifier: List<Identifier> = listOf(),
    val instantiates: List<Uri> = listOf(),
    val partOf: List<Reference> = listOf(),
    val status: Code? = null,
    val statusReason: List<CodeableConcept> = listOf(),
    val category: CodeableConcept? = null,
    val medication: DynamicValue<Any>? = null,
    val subject: Reference? = null,
    val context: Reference? = null,
    val supportingInformation: List<Reference> = listOf(),
    val effective: DynamicValue<Any>? = null,
    val performer: List<MedicationAdministrationPerformer> = listOf(),
    val reasonCode: List<CodeableConcept> = listOf(),
    val reasonReference: List<Reference> = listOf(),
    val request: Reference? = null,
    val device: List<Reference> = listOf(),
    val note: List<Annotation> = listOf(),
    val dosage: MedicationAdministrationDosage? = null,
    val eventHistory: List<Reference> = listOf()
) : DomainResource<MedicationAdministration> {
    override val resourceType: String = "MedicationAdministration"
}

class MedicationAdministrationDeserializer : BaseFHIRDeserializer<MedicationAdministration>(MedicationAdministration::class.java)
class MedicationAdministrationSerializer : BaseFHIRSerializer<MedicationAdministration>(MedicationAdministration::class.java)

@JsonDeserialize(using = MedicationAdministrationPerformerDeserializer::class)
@JsonSerialize(using = MedicationAdministrationPerformerSerializer::class)
data class MedicationAdministrationPerformer(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val function: CodeableConcept? = null,
    val actor: Reference? = null
) : BackboneElement<MedicationAdministrationPerformer>

class MedicationAdministrationPerformerDeserializer : BaseFHIRDeserializer<MedicationAdministrationPerformer>(MedicationAdministrationPerformer::class.java)
class MedicationAdministrationPerformerSerializer : BaseFHIRSerializer<MedicationAdministrationPerformer>(MedicationAdministrationPerformer::class.java)

@JsonDeserialize(using = MedicationAdministrationDosageDeserializer::class)
@JsonSerialize(using = MedicationAdministrationDosageSerializer::class)
data class MedicationAdministrationDosage(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val text: FHIRString? = null,
    val site: CodeableConcept? = null,
    val route: CodeableConcept? = null,
    val method: CodeableConcept? = null,
    val dose: Quantity? = null,
    val rate: DynamicValue<Any>? = null
) : BackboneElement<MedicationAdministrationDosage>
class MedicationAdministrationDosageDeserializer : BaseFHIRDeserializer<MedicationAdministrationDosage>(MedicationAdministrationDosage::class.java)
class MedicationAdministrationDosageSerializer : BaseFHIRSerializer<MedicationAdministrationDosage>(MedicationAdministrationDosage::class.java)
