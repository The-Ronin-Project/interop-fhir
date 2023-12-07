package com.projectronin.interop.fhir.r4.resource

import com.fasterxml.jackson.annotation.JsonTypeName
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.event.interop.internal.v1.ResourceType
import com.projectronin.interop.fhir.jackson.inbound.r4.BaseFHIRDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.BaseFHIRSerializer
import com.projectronin.interop.fhir.r4.datatype.Annotation
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
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
import com.projectronin.interop.fhir.r4.valueset.MedicationAdministrationStatus
import com.projectronin.interop.fhir.validate.annotation.RequiredField
import com.projectronin.interop.fhir.validate.annotation.RequiredValueSet
import com.projectronin.interop.fhir.validate.annotation.SupportedDynamicValueTypes
import com.projectronin.interop.fhir.validate.annotation.SupportedReferenceTypes

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
    @SupportedReferenceTypes(ResourceType.MedicationAdministration, ResourceType.Procedure)
    val partOf: List<Reference> = listOf(),
    @RequiredField
    @RequiredValueSet(MedicationAdministrationStatus::class)
    val status: Code? = null,
    val statusReason: List<CodeableConcept> = listOf(),
    val category: CodeableConcept? = null,
    @RequiredField
    @SupportedDynamicValueTypes(DynamicValueType.CODEABLE_CONCEPT, DynamicValueType.REFERENCE)
    @SupportedReferenceTypes(ResourceType.Medication)
    val medication: DynamicValue<Any>? = null,
    @SupportedReferenceTypes(ResourceType.Patient, ResourceType.Group)
    @RequiredField
    val subject: Reference? = null,
    @SupportedReferenceTypes(ResourceType.Encounter, ResourceType.EpisodeOfCare)
    val context: Reference? = null,
    val supportingInformation: List<Reference> = listOf(),
    @RequiredField
    @SupportedDynamicValueTypes(DynamicValueType.DATE_TIME, DynamicValueType.PERIOD)
    val effective: DynamicValue<Any>? = null,
    val performer: List<MedicationAdministrationPerformer> = listOf(),
    val reasonCode: List<CodeableConcept> = listOf(),
    @SupportedReferenceTypes(ResourceType.Condition, ResourceType.Observation, ResourceType.DiagnosticReport)
    val reasonReference: List<Reference> = listOf(),
    @SupportedReferenceTypes(ResourceType.MedicationRequest)
    val request: Reference? = null,
    @SupportedReferenceTypes(ResourceType.Device)
    val device: List<Reference> = listOf(),
    val note: List<Annotation> = listOf(),
    val dosage: MedicationAdministrationDosage? = null,
    @SupportedReferenceTypes(ResourceType.Provenance)
    val eventHistory: List<Reference> = listOf(),
) : DomainResource<MedicationAdministration> {
    override val resourceType: String = "MedicationAdministration"
}

class MedicationAdministrationDeserializer :
    BaseFHIRDeserializer<MedicationAdministration>(MedicationAdministration::class.java)

class MedicationAdministrationSerializer :
    BaseFHIRSerializer<MedicationAdministration>(MedicationAdministration::class.java)

@JsonDeserialize(using = MedicationAdministrationPerformerDeserializer::class)
@JsonSerialize(using = MedicationAdministrationPerformerSerializer::class)
data class MedicationAdministrationPerformer(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val function: CodeableConcept? = null,
    @RequiredField
    @SupportedReferenceTypes(
        ResourceType.Practitioner,
        ResourceType.PractitionerRole,
        ResourceType.Patient,
        ResourceType.RelatedPerson,
        ResourceType.Device,
    )
    val actor: Reference? = null,
) : BackboneElement<MedicationAdministrationPerformer>

class MedicationAdministrationPerformerDeserializer :
    BaseFHIRDeserializer<MedicationAdministrationPerformer>(MedicationAdministrationPerformer::class.java)

class MedicationAdministrationPerformerSerializer :
    BaseFHIRSerializer<MedicationAdministrationPerformer>(MedicationAdministrationPerformer::class.java)

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
    @SupportedDynamicValueTypes(DynamicValueType.RATIO, DynamicValueType.QUANTITY)
    val rate: DynamicValue<Any>? = null,
) : BackboneElement<MedicationAdministrationDosage>

class MedicationAdministrationDosageDeserializer :
    BaseFHIRDeserializer<MedicationAdministrationDosage>(MedicationAdministrationDosage::class.java)

class MedicationAdministrationDosageSerializer :
    BaseFHIRSerializer<MedicationAdministrationDosage>(MedicationAdministrationDosage::class.java)
