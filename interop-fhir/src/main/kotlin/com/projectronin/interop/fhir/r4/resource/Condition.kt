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
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.interop.fhir.r4.datatype.primitive.FHIRString
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri
import com.projectronin.interop.fhir.r4.element.BackboneElement
import com.projectronin.interop.fhir.validate.annotation.RequiredField
import com.projectronin.interop.fhir.validate.annotation.SupportedDynamicValueTypes
import com.projectronin.interop.fhir.validate.annotation.SupportedReferenceTypes

/**
 * A clinical condition, problem, diagnosis, or other event, situation, issue, or clinical concept that has risen to a level of concern.
 */
@JsonDeserialize(using = ConditionDeserializer::class)
@JsonSerialize(using = ConditionSerializer::class)
@JsonTypeName("Condition")
data class Condition(
    override val id: Id? = null,
    override var meta: Meta? = null,
    override val implicitRules: Uri? = null,
    override val language: Code? = null,
    override val text: Narrative? = null,
    override val contained: List<Resource<*>> = listOf(),
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val identifier: List<Identifier> = listOf(),
    val clinicalStatus: CodeableConcept? = null,
    val verificationStatus: CodeableConcept? = null,
    val category: List<CodeableConcept> = listOf(),
    val severity: CodeableConcept? = null,
    val code: CodeableConcept? = null,
    val bodySite: List<CodeableConcept> = listOf(),
    @RequiredField
    @SupportedReferenceTypes(ResourceType.Patient, ResourceType.Group)
    val subject: Reference?,
    @SupportedReferenceTypes(ResourceType.Encounter)
    val encounter: Reference? = null,
    @SupportedDynamicValueTypes(
        DynamicValueType.DATE_TIME,
        DynamicValueType.AGE,
        DynamicValueType.PERIOD,
        DynamicValueType.RANGE,
        DynamicValueType.STRING
    )
    val onset: DynamicValue<Any>? = null,
    @SupportedDynamicValueTypes(
        DynamicValueType.DATE_TIME,
        DynamicValueType.AGE,
        DynamicValueType.PERIOD,
        DynamicValueType.RANGE,
        DynamicValueType.STRING
    )
    val abatement: DynamicValue<Any>? = null,
    val recordedDate: DateTime? = null,
    @SupportedReferenceTypes(
        ResourceType.Practitioner,
        ResourceType.PractitionerRole,
        ResourceType.Patient,
        ResourceType.RelatedPerson
    )
    val recorder: Reference? = null,
    @SupportedReferenceTypes(
        ResourceType.Practitioner,
        ResourceType.PractitionerRole,
        ResourceType.Patient,
        ResourceType.RelatedPerson
    )
    val asserter: Reference? = null,
    val stage: List<ConditionStage> = listOf(),
    val evidence: List<ConditionEvidence> = listOf(),
    val note: List<Annotation> = listOf()
) : DomainResource<Condition> {
    override val resourceType: String = "Condition"
}

class ConditionDeserializer : BaseFHIRDeserializer<Condition>(Condition::class.java)
class ConditionSerializer : BaseFHIRSerializer<Condition>(Condition::class.java)

/**
 * Supporting evidence
 */
@JsonSerialize(using = ConditionEvidenceSerializer::class)
@JsonDeserialize(using = ConditionEvidenceDeserializer::class)
data class ConditionEvidence(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val code: List<CodeableConcept> = listOf(),
    val detail: List<Reference> = listOf()
) : BackboneElement<ConditionEvidence>

class ConditionEvidenceSerializer : BaseFHIRSerializer<ConditionEvidence>(ConditionEvidence::class.java)
class ConditionEvidenceDeserializer : BaseFHIRDeserializer<ConditionEvidence>(ConditionEvidence::class.java)

/**
 * Stage/grade, usually assessed formally
 */
@JsonSerialize(using = ConditionStageSerializer::class)
@JsonDeserialize(using = ConditionStageDeserializer::class)
data class ConditionStage(
    override val id: FHIRString? = null,
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val summary: CodeableConcept? = null,
    @SupportedReferenceTypes(ResourceType.ClinicalImpression, ResourceType.DiagnosticReport, ResourceType.Observation)
    val assessment: List<Reference> = listOf(),
    val type: CodeableConcept? = null
) : BackboneElement<ConditionStage>

class ConditionStageSerializer : BaseFHIRSerializer<ConditionStage>(ConditionStage::class.java)
class ConditionStageDeserializer : BaseFHIRDeserializer<ConditionStage>(ConditionStage::class.java)
