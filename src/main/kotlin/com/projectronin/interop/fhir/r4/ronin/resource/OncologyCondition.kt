package com.projectronin.interop.fhir.r4.ronin.resource

import com.fasterxml.jackson.annotation.JsonTypeName
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.projectronin.interop.common.enums.CodedEnum
import com.projectronin.interop.fhir.jackson.inbound.r4.ronin.OncologyConditionDeserializer
import com.projectronin.interop.fhir.jackson.outbound.r4.ronin.OncologyConditionSerializer
import com.projectronin.interop.fhir.r4.datatype.Annotation
import com.projectronin.interop.fhir.r4.datatype.CodeableConcept
import com.projectronin.interop.fhir.r4.datatype.ConditionEvidence
import com.projectronin.interop.fhir.r4.datatype.ConditionStage
import com.projectronin.interop.fhir.r4.datatype.DynamicValue
import com.projectronin.interop.fhir.r4.datatype.DynamicValueType
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
import com.projectronin.interop.fhir.r4.valueset.ConditionCategoryCodes
import com.projectronin.interop.fhir.r4.valueset.ConditionClinicalStatusCodes
import com.projectronin.interop.fhir.r4.valueset.ConditionVerificationStatus

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
    companion object {
        val acceptedOnsets = listOf(DynamicValueType.DATE_TIME, DynamicValueType.PERIOD)
        val acceptedAbatement = listOf(DynamicValueType.PERIOD)
    }

    init {
        validate()

        // Category Required
        require(category.isNotEmpty()) { "At least one category must be provided" }

        onset?.let {
            require(acceptedOnsets.contains(onset.type)) { "Invalid dynamic type for condition onset" }
        }
        abatement?.let {
            require(acceptedAbatement.contains(abatement.type)) { "Invalid dynamic type for condition abatement" }
        }

        requireTenantIdentifier(identifier)

        clinicalStatus?.let {
            val clinicalStatusCode = clinicalStatus.coding.getOrNull(0)?.code?.let { code ->
                CodedEnum.byCode<ConditionClinicalStatusCodes>(code.value)
            }
            requireNotNull(clinicalStatusCode) { "Invalid Clinical Status Code" }

            // con-4
            abatement?.let {
                require(
                    listOf(
                        ConditionClinicalStatusCodes.INACTIVE,
                        ConditionClinicalStatusCodes.RESOLVED,
                        ConditionClinicalStatusCodes.REMISSION
                    ).contains(clinicalStatusCode)
                ) { "If condition is abated, then clinicalStatus must be either inactive, resolved, or remission" }
            }
        }

        verificationStatus?.let {
            val verificationStatusCode = verificationStatus.coding.getOrNull(0)?.code?.let { code ->
                CodedEnum.byCode<ConditionVerificationStatus>(code.value)
            }
            requireNotNull(verificationStatusCode) { "Invalid Verification Status Code" }

            // con-3
            category[0].coding[0].code?.let { code ->
                val conditionCategoryCode = CodedEnum.byCode<ConditionCategoryCodes>(code.value)
                if (verificationStatus.coding[0].system == Uri("http://terminology.hl7.org/CodeSystem/condition-ver-status") && verificationStatusCode != ConditionVerificationStatus.ENTERED_IN_ERROR && conditionCategoryCode == ConditionCategoryCodes.PROBLEM_LIST_ITEM) {
                    requireNotNull(clinicalStatus) { "Condition.clinicalStatus SHALL be present if verificationStatus is not entered-in-error and category is problem-list-item" }
                }
            }

            // con-5
            clinicalStatus?.let {
                require(verificationStatusCode != ConditionVerificationStatus.ENTERED_IN_ERROR) { "Condition.clinicalStatus SHALL NOT be present if verification Status is entered-in-error" }
            }
        }
    }
}
