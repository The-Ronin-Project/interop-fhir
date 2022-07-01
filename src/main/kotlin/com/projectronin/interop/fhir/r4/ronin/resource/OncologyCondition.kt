package com.projectronin.interop.fhir.r4.ronin.resource

import com.fasterxml.jackson.annotation.JsonTypeName
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.interop.fhir.jackson.inbound.r4.ronin.OncologyConditionDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.ronin.OncologyConditionSerializer
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
import com.projectronin.interop.fhir.r4.resource.ContainedResource
import com.projectronin.interop.fhir.r4.resource.base.BaseCondition

/**
 * Project Ronin definition of an Oncology Condition.
 *
 * See [Project Ronin Profile Spec](https://crispy-carnival-61996e6e.pages.github.io/StructureDefinition-oncology-condition.html)
 */

@JsonDeserialize(using = OncologyConditionDeserializer::class)
@JsonSerialize(using = OncologyConditionSerializer::class)
@JsonTypeName("Condition")
data class OncologyCondition(
    override val id: Id? = null,
    override val meta: Meta? = null,
    override val implicitRules: Uri? = null,
    override val language: Code? = null,
    override val text: Narrative? = null,
    override val contained: List<ContainedResource> = listOf(),
    override val extension: List<Extension> = listOf(),
    override val modifierExtension: List<Extension> = listOf(),
    override val identifier: List<Identifier>,
    override val clinicalStatus: CodeableConcept? = null,
    override val verificationStatus: CodeableConcept? = null,
    override val category: List<CodeableConcept>,
    override val severity: CodeableConcept? = null,
    override val code: CodeableConcept,
    override val bodySite: List<CodeableConcept> = listOf(),
    override val subject: Reference,
    override val encounter: Reference? = null,
    override val onset: DynamicValue<Any>? = null,
    override val abatement: DynamicValue<Any>? = null,
    override val recordedDate: DateTime? = null,
    override val recorder: Reference? = null,
    override val asserter: Reference? = null,
    override val stage: List<ConditionStage> = listOf(),
    override val evidence: List<ConditionEvidence> = listOf(),
    override val note: List<Annotation> = listOf()
) : RoninDomainResource, BaseCondition() {
    init {
        validate()

        requireTenantIdentifier(identifier)

        require(category.isNotEmpty()) { "At least one category must be provided" }
    }
}
