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
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.Meta
import com.projectronin.interop.fhir.r4.datatype.Narrative
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.SimpleQuantity
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.Date
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRBoolean
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.element.BackboneElement
import com.projectronin.interop.fhir.validate.annotation.SupportedReferenceTypes

/**
 * Describes the event of a patient being administered a vaccine or a record of an immunization as reported by a patient, a clinician or another party.
 */
@JsonDeserialize(using = ImmunizationDeserializer::class)
@JsonSerialize(using = ImmunizationSerializer::class)
@JsonTypeName("Immunization")
data class Immunization(
    override val id: Id? = null,
    override var meta: Meta? = null,
    override val implicitRules: Uri? = null,
    override val language: Code? = null,
    override val text: Narrative? = null,
    override val contained: List<Resource<*>> = listOf(),
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val identifier: List<Identifier> = listOf(),
    val status: Code?,
    val statusReason: CodeableConcept? = null,
    val vaccineCode: CodeableConcept?,
    @SupportedReferenceTypes(ResourceType.Patient)
    val patient: Reference?,
    @SupportedReferenceTypes(ResourceType.Encounter)
    val encounter: Reference? = null,
    val occurrence: DynamicValue<Any>?,
    val recorded: DateTime? = null,
    val primarySource: FHIRBoolean? = null,
    val reportOrigin: CodeableConcept? = null,
    @SupportedReferenceTypes(ResourceType.Location)
    val location: Reference? = null,
    @SupportedReferenceTypes(ResourceType.Organization)
    val manufacturer: Reference? = null,
    val lotNumber: FHIRString? = null,
    val expirationDate: Date? = null,
    val site: CodeableConcept? = null,
    val route: CodeableConcept? = null,
    val doseQuantity: SimpleQuantity? = null,
    val performer: List<ImmunizationPerformer> = listOf(),
    val note: List<Annotation> = listOf(),
    val reasonCode: List<CodeableConcept> = listOf(),
    @SupportedReferenceTypes(ResourceType.Condition, ResourceType.Observation, ResourceType.DiagnosticReport)
    val reasonReference: List<Reference> = listOf(),
    val isSubpotent: FHIRBoolean? = null,
    val subpotentReason: List<CodeableConcept> = listOf(),
    val education: List<ImmunizationEducation> = listOf(),
    val programEligibility: List<CodeableConcept> = listOf(),
    val fundingSource: CodeableConcept? = null,
    val reaction: List<ImmunizationReaction> = listOf(),
    val protocolApplied: List<ImmunizationProtocolApplied> = listOf()
) : DomainResource<Immunization> {
    override val resourceType: String = "Immunization"
}

class ImmunizationDeserializer : BaseFHIRDeserializer<Immunization>(Immunization::class.java)
class ImmunizationSerializer : BaseFHIRSerializer<Immunization>(Immunization::class.java)

@JsonSerialize(using = ImmunizationPerformerSerializer::class)
@JsonDeserialize(using = ImmunizationPerformerDeserializer::class)
data class ImmunizationPerformer(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val function: CodeableConcept? = null,
    @SupportedReferenceTypes(ResourceType.Practitioner, ResourceType.PractitionerRole, ResourceType.Organization)
    val actor: Reference?
) : BackboneElement<ImmunizationPerformer>

class ImmunizationPerformerSerializer : BaseFHIRSerializer<ImmunizationPerformer>(ImmunizationPerformer::class.java)
class ImmunizationPerformerDeserializer : BaseFHIRDeserializer<ImmunizationPerformer>(ImmunizationPerformer::class.java)

@JsonSerialize(using = ImmunizationEducationSerializer::class)
@JsonDeserialize(using = ImmunizationEducationDeserializer::class)
data class ImmunizationEducation(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val documentType: FHIRString? = null,
    val reference: Uri? = null,
    val publicationDate: DateTime? = null,
    val presentationDate: DateTime? = null
) : BackboneElement<ImmunizationEducation>

class ImmunizationEducationSerializer : BaseFHIRSerializer<ImmunizationEducation>(ImmunizationEducation::class.java)
class ImmunizationEducationDeserializer : BaseFHIRDeserializer<ImmunizationEducation>(ImmunizationEducation::class.java)

@JsonSerialize(using = ImmunizationReactionSerializer::class)
@JsonDeserialize(using = ImmunizationReactionDeserializer::class)
data class ImmunizationReaction(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val date: DateTime? = null,
    @SupportedReferenceTypes(ResourceType.Observation)
    val detail: Reference? = null,
    val reported: FHIRBoolean? = null
) : BackboneElement<ImmunizationReaction>

class ImmunizationReactionSerializer : BaseFHIRSerializer<ImmunizationReaction>(ImmunizationReaction::class.java)
class ImmunizationReactionDeserializer : BaseFHIRDeserializer<ImmunizationReaction>(ImmunizationReaction::class.java)

@JsonSerialize(using = ImmunizationProtocolAppliedSerializer::class)
@JsonDeserialize(using = ImmunizationProtocolAppliedDeserializer::class)
data class ImmunizationProtocolApplied(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val series: FHIRString? = null,
    @SupportedReferenceTypes(ResourceType.Organization)
    val authority: Reference? = null,
    val targetDisease: List<CodeableConcept> = listOf(),
    val doseNumber: DynamicValue<Any>?,
    val seriesDoses: DynamicValue<Any>? = null
) : BackboneElement<ImmunizationProtocolApplied>

class ImmunizationProtocolAppliedSerializer :
    BaseFHIRSerializer<ImmunizationProtocolApplied>(ImmunizationProtocolApplied::class.java)

class ImmunizationProtocolAppliedDeserializer :
    BaseFHIRDeserializer<ImmunizationProtocolApplied>(ImmunizationProtocolApplied::class.java)
