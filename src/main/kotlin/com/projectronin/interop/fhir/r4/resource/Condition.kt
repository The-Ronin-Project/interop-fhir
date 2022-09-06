package com.projectronin.interop.fhir.r4.resource

import com.fasterxml.jackson.annotation.JsonTypeName
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.interop.fhir.jackson.inbound.r4.ConditionDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.ConditionSerializer
import com.projectronin.interop.fhir.r4.datatype.Annotation
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.ConditionEvidence
import com.projectronin.interop.fhir.r4.datatype.ConditionStage
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.Extension
import com.projectronin.interop.fhir.r4.datatype.Identifier
import com.projectronin.interop.fhir.r4.datatype.Meta
import com.projectronin.interop.fhir.r4.datatype.Narrative
import com.projectronin.interop.fhir.r4.datatype.Reference
import com.projectronin.interop.fhir.r4.datatype.primitive.Code
import com.projectronin.interop.fhir.r4.datatype.primitive.DateTime
import com.projectronin.interop.fhir.r4.datatype.primitive.Id
import com.projectronin.interop.fhir.r4.datatype.primitive.Uri

/**
 * A clinical condition, problem, diagnosis, or other event, situation, issue, or clinical concept that has risen to a level of concern.
 */
@JsonDeserialize(using = ConditionDeserializer::class)
@JsonSerialize(using = ConditionSerializer::class)
@JsonTypeName("Condition")
data class Condition(
    override val id: Id? = null,
    override val meta: Meta? = null,
    override val implicitRules: Uri? = null,
    override val language: Code? = null,
    override val text: Narrative? = null,
    override val contained: List<ContainedResource> = listOf(),
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    val identifier: List<Identifier> = listOf(),
    val clinicalStatus: CodeableConcept? = null,
    val verificationStatus: CodeableConcept? = null,
    val category: List<CodeableConcept> = listOf(),
    val severity: CodeableConcept? = null,
    val code: CodeableConcept? = null,
    val bodySite: List<CodeableConcept> = listOf(),
    val subject: Reference?,
    val encounter: Reference? = null,
    val onset: DynamicValue<Any>? = null,
    val abatement: DynamicValue<Any>? = null,
    val recordedDate: DateTime? = null,
    val recorder: Reference? = null,
    val asserter: Reference? = null,
    val stage: List<ConditionStage> = listOf(),
    val evidence: List<ConditionEvidence> = listOf(),
    val note: List<Annotation> = listOf()
) : DomainResource<Condition> {
    override val resourceType: String = "Condition"
}
